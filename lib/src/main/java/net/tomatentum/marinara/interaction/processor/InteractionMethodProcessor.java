package net.tomatentum.marinara.interaction.processor;

import java.util.Set;

import org.slf4j.Logger;

import net.tomatentum.cutin.MethodProcessor;
import net.tomatentum.cutin.container.MethodContainer;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.util.LoggerUtil;
import net.tomatentum.marinara.wrapper.IdentifierProvider;

public abstract class InteractionMethodProcessor implements MethodProcessor<InteractionIdentifier, Object> {

    private Logger logger = LoggerUtil.getLogger(getClass());

    private IdentifierProvider provider;
    private Set<InteractionType> types;

    protected InteractionMethodProcessor(IdentifierProvider provider, Set<InteractionType> types) {
        this.provider = provider;
        this.types = types;
    }

    @Override
    public void process(Object context, MethodContainer<InteractionIdentifier, Object> container) {
        InteractionIdentifier identifier = this.provider.provide(context);
        if (!this.types.contains(identifier.type())) return;
        logger.debug("Processing {} : {} with context {}", identifier, identifier.type(), context);
        this.processInteraction(context, container, identifier);
    }

    protected abstract void processInteraction(
        Object context, 
        MethodContainer<InteractionIdentifier, Object> container, 
        InteractionIdentifier identifier);
    
}
