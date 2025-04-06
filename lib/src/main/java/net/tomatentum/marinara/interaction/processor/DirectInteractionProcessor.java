package net.tomatentum.marinara.interaction.processor;

import java.util.Set;

import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.registry.InteractionRegistry;
import net.tomatentum.marinara.wrapper.IdentifierProvider;

public class DirectInteractionProcessor extends InteractionMethodProcessor {

    private InteractionRegistry registry;

    public DirectInteractionProcessor(InteractionRegistry registry, IdentifierProvider provider, InteractionType... types) {
        super(provider, Set.of(types));
        this.registry = registry;
    }

    @Override
    protected void processInteraction(Object context, InteractionIdentifier identifier) {
        this.registry.interactions().stream()
            .filter(e -> e.identifier().equals(identifier))
            .findFirst()
            .ifPresent(e -> e.runAll(context));
    }
    
}
