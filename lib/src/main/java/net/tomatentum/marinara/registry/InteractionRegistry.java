package net.tomatentum.marinara.registry;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Logger;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.commands.SlashCommandDefinition;
import net.tomatentum.marinara.interaction.commands.ExecutableSlashCommandDefinition;
import net.tomatentum.marinara.interaction.methods.SlashCommandInteractionMethod;
import net.tomatentum.marinara.util.LoggerUtil;
import net.tomatentum.marinara.interaction.methods.InteractionMethod;

public class InteractionRegistry {
    private Logger logger = LoggerUtil.getLogger(getClass());
    private List<InteractionMethod> interactionMethods;
    private Marinara marinara;

    public InteractionRegistry(Marinara marinara) {
        this.interactionMethods = new ArrayList<>();
        this.marinara = marinara;
        marinara.getWrapper().subscribeInteractions(this::handle);
    }

    public void addInteractions(InteractionHandler interactionHandler) {
        for (Method method : interactionHandler.getClass().getMethods()) {
            InteractionMethod iMethod = InteractionMethod.create(method, interactionHandler, marinara);
            if (iMethod != null) {
                this.interactionMethods.add(iMethod);
                logger.debug("Added {} method from {}", iMethod.getMethod().getName(), interactionHandler.getClass().getSimpleName());
            }
        }
        logger.info("Added all Interactions from {}", interactionHandler.getClass().getSimpleName());
    }

    public void registerCommands() {
        List<SlashCommandDefinition> defs = new ArrayList<>();
        List<ExecutableSlashCommandDefinition> execDefs = interactionMethods.stream()
            .filter((x) -> x.getClass().isAssignableFrom(SlashCommandInteractionMethod.class))
            .map((x) -> ((SlashCommandInteractionMethod)x).getCommandDefinition())
            .toList();

        execDefs.forEach((def) -> {
            Optional<SlashCommandDefinition> appDef = defs.stream()
                .filter((x) -> x.getSlashCommand().equals(def.applicationCommand()))
                .findFirst();
            if (appDef.isPresent())
                appDef.get().addExecutableCommand(def);
            else
                defs.add(new SlashCommandDefinition(def.applicationCommand()).addExecutableCommand(def));

            logger.debug("Added Executable Command {}{}{} for registration", 
                def.applicationCommand().name(), 
                def.subCommandGroup() == null ? "" : "." + def.subCommandGroup().name(),
                def.subCommand() == null ? "" : "." + def.subCommand().name()
                );
        });

        marinara.getWrapper().registerSlashCommands(defs.toArray(SlashCommandDefinition[]::new));
        logger.info("Registered all SlashCommands");
    }

    public void handle(Object context) {
        InteractionType type = marinara.getWrapper().getInteractionType(context.getClass());
        logger.debug("Received {} interaction ", context);
        interactionMethods.forEach((m) -> {
            if (m.getType().equals(type) && m.canRun(context)) {
                logger.info("Running {} interaction using {}\ncontext: {}", type, m.getMethod().toString(), context.toString());
                m.run(context);
            }
        });
    }
}
