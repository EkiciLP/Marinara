package net.tomatentum.marinara.registry;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.Logger;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.commands.SlashCommandDefinition;
import net.tomatentum.marinara.interaction.ident.RootCommandIdentifier;
import net.tomatentum.marinara.interaction.ident.SlashCommandIdentifier;
import net.tomatentum.marinara.util.LoggerUtil;
import net.tomatentum.marinara.wrapper.LibraryWrapper;
import net.tomatentum.marinara.interaction.methods.InteractionMethod;

public class InteractionRegistry {
    private Logger logger = LoggerUtil.getLogger(getClass());
    private Set<InteractionEntry> interactions;
    private Marinara marinara;

    public InteractionRegistry(Marinara marinara) {
        this.interactions = new HashSet<>();
        this.marinara = marinara;
        marinara.getWrapper().subscribeInteractions(this::handle);
    }

    /*
     * TODO: Maybe relocate InteractionEntry checking to another class with description merging.
     */
    public void addInteractions(InteractionHandler interactionHandler) {
        for (Method method : interactionHandler.getClass().getMethods()) {
            InteractionMethod iMethod = InteractionMethod.create(method, interactionHandler, marinara);
            if (iMethod != null) {
                Optional<InteractionEntry> entry = this.interactions.stream().filter(iMethod::equals).findFirst();
                if (entry.isEmpty()) {
                    interactions.add(new InteractionEntry(iMethod.identifier()).addMethod(iMethod));
                }else
                    entry.get().addMethod(iMethod);
                logger.debug("Added {} method from {}", iMethod.method().getName(), interactionHandler.getClass().getSimpleName());
            }
        }
        logger.info("Added all Interactions from {}", interactionHandler.getClass().getSimpleName());
    }

    public void registerCommands() {
        List<SlashCommandDefinition> defs = new ArrayList<>();
        List<SlashCommandIdentifier> slashIdentifiers = interactions.stream()
            .filter((x) -> x.type().equals(InteractionType.COMMAND))
            .map((x) -> (SlashCommandIdentifier)x.identifier())
            .toList();

        slashIdentifiers.forEach((ident) -> {
            Optional<SlashCommandDefinition> appDef = defs.stream()
                .filter((x) -> x.rootIdentifier().equals(ident.rootNode()))
                .findFirst();

            if (appDef.isPresent())
                appDef.get().addIdentifier(ident);
            else
                defs.add(
                    new SlashCommandDefinition((RootCommandIdentifier) ident.rootNode())
                        .addIdentifier(ident));
        });

        marinara.getWrapper().registerSlashCommands(defs.toArray(SlashCommandDefinition[]::new));
        logger.info("Registered all SlashCommands");
    }

    public void handle(Object context) {
        logger.debug("Received {} interaction ", context);
        LibraryWrapper wrapper = marinara.getWrapper();
        interactions.forEach((e) -> {
            if (wrapper.getInteractionIdentifier(context).equals(e.identifier())) {
                logger.info("Running {} interaction using {}\ncontext: {}", e.type(), e.toString(), context.toString());
                e.runAll(context);
            }
        });
    }
}
