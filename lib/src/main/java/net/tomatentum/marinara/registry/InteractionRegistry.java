package net.tomatentum.marinara.registry;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.Logger;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.commands.SlashCommandDefinition;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.interaction.ident.RootCommandIdentifier;
import net.tomatentum.marinara.util.LoggerUtil;
import net.tomatentum.marinara.util.ObjectAggregator;
import net.tomatentum.marinara.wrapper.IdentifierProvider;
import net.tomatentum.marinara.interaction.methods.InteractionMethod;

public class InteractionRegistry {
    private Logger logger = LoggerUtil.getLogger(getClass());
    private Set<InteractionEntry> interactions;
    private Marinara marinara;
    private IdentifierProvider identifierProvider;

    public InteractionRegistry(Marinara marinara) {
        this.interactions = new HashSet<>();
        this.marinara = marinara;
        this.identifierProvider = marinara.getWrapper().createIdentifierProvider();
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
        List<InteractionIdentifier> slashIdentifiers = interactions.stream()
            .filter((x) -> x.type().equals(InteractionType.COMMAND))
            .map((x) -> x.identifier())
            .toList();

        SlashCommandDefinition[] defs = new ObjectAggregator<InteractionIdentifier, RootCommandIdentifier, SlashCommandDefinition>(
            i -> Arrays.asList((RootCommandIdentifier)i.rootNode()),
            SlashCommandDefinition::addIdentifier,
            SlashCommandDefinition::new)
            .aggregate(slashIdentifiers)
            .toArray(SlashCommandDefinition[]::new);

        marinara.getWrapper().registerSlashCommands(defs);
        logger.info("Registered all SlashCommands");
    }

    public void handle(Object context) {
        logger.debug("Received {} interaction ", context);
        interactions.forEach((e) -> {
            if (this.identifierProvider.provide(context).equals(e.identifier())) {
                logger.info("Running {} interaction using {}\ncontext: {}", e.type(), e.toString(), context.toString());
                e.runAll(context);
            }
        });
    }
}
