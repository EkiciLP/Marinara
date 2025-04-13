package net.tomatentum.marinara.interaction.processor;

import java.util.Set;

import net.tomatentum.cutin.container.MethodContainer;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.wrapper.IdentifierProvider;

public class DirectInteractionProcessor extends InteractionMethodProcessor {


    public DirectInteractionProcessor(IdentifierProvider provider, InteractionType... types) {
        super(provider, Set.of(types));
    }

    @Override
    protected void processInteraction(Object context, MethodContainer<InteractionIdentifier, Object> container, InteractionIdentifier identifier) {
        container.findFor(identifier).forEach(m -> m.run(context));
    }
    
}
