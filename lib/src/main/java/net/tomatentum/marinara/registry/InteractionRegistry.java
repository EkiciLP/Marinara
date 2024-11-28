package net.tomatentum.marinara.registry;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.commands.SlashCommandDefinition;
import net.tomatentum.marinara.interaction.commands.ExecutableSlashCommandDefinition;
import net.tomatentum.marinara.interaction.methods.SlashCommandInteractionMethod;
import net.tomatentum.marinara.interaction.methods.InteractionMethod;

public class InteractionRegistry {
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
            if (iMethod != null)
                this.interactionMethods.add(iMethod);
        }
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
        });

        marinara.getWrapper().registerSlashCommands(defs.toArray(new SlashCommandDefinition[0]));
    }

    public void handle(Object context) {
        interactionMethods.forEach((m) -> {
            InteractionType type = marinara.getWrapper().getInteractionType(context.getClass());
            if (m.getType().equals(type))
                m.run(context);
        });
    }
}
