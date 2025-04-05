package net.tomatentum.marinara.registry;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.interaction.processor.InteractionProcessor;
import net.tomatentum.marinara.util.LoggerUtil;
import net.tomatentum.marinara.wrapper.IdentifierProvider;

public class ProcessorInteractionExecutor implements InteractionExecutor {

    private Logger logger = LoggerUtil.getLogger(getClass());

    private Set<InteractionProcessor> processors;
    private IdentifierProvider identifierProvider;
    private Marinara marinara;

    public ProcessorInteractionExecutor(IdentifierProvider identifierProvider, Marinara marinara) {
        this.processors = new HashSet<>();
        this.identifierProvider = identifierProvider;
        this.marinara = marinara;
    }

    public ProcessorInteractionExecutor addProcessor(InteractionProcessor processor) {
        this.processors.add(processor);
        return this;
    }

    @Override
    public void handle(Object context) {
        logger.debug("Received {} interaction ", context);
        InteractionIdentifier identifier = this.identifierProvider.provide(context);
        logger.debug("Processing {} : {} interaction ", identifier, identifier.type());
        processors.forEach(x -> x.process(context, identifier, this.marinara));
    }
    
}
