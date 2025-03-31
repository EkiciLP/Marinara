package net.tomatentum.marinara.registry;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.commands.SlashCommandDefinition;
import net.tomatentum.marinara.interaction.components.methods.ButtonInteractionMethod;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.interaction.ident.RootCommandIdentifier;
import net.tomatentum.marinara.util.LoggerUtil;
import net.tomatentum.marinara.util.ObjectAggregator;
import net.tomatentum.marinara.wrapper.IdentifierProvider;
import net.tomatentum.marinara.interaction.methods.AutoCompleteInteractionMethod;
import net.tomatentum.marinara.interaction.methods.InteractionMethod;
import net.tomatentum.marinara.interaction.methods.SlashCommandInteractionMethod;
import net.tomatentum.marinara.reflection.ReflectedMethod;

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
        marinara.getReflectedMethodFactory()
            .addFactory(new AutoCompleteInteractionMethod.Factory())
            .addFactory(new SlashCommandInteractionMethod.Factory())
            .addFactory(new ButtonInteractionMethod.Factory());

    }
    
    public void addInteractions(InteractionHandler interactionHandler) {
        for (Method method : interactionHandler.getClass().getDeclaredMethods()) {
            ReflectedMethod rMethod = this.marinara.getReflectedMethodFactory().produce(method, interactionHandler);
            if (rMethod != null && rMethod instanceof InteractionMethod) {
                InteractionMethod iMethod = (InteractionMethod) rMethod;
                InteractionEntry.findEntry(interactions, iMethod.identifier()).addMethod(iMethod);
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

        marinara.getWrapper().getRegisterer().register(defs);
    }

    public void handle(Object context) {
        logger.debug("Received {} interaction ", context);
        interactions.forEach((e) -> {
            if (e.identifier().equals(this.identifierProvider.provide(context, this))) {
                logger.info("Running {} interaction using {}\ncontext: {}", e.type(), e.toString(), context.toString());
                e.runAll(context);
            }
        });
    }

    public Optional<InteractionEntry> findFor(InteractionIdentifier identifier) {
        return this.interactions.stream()
            .filter(x -> x.identifier().equals(identifier))
            .findFirst();
    }
}
