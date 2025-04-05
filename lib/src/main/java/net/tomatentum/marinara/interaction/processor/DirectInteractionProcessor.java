package net.tomatentum.marinara.interaction.processor;

import java.util.Set;

import org.slf4j.Logger;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.util.LoggerUtil;

public class DirectInteractionProcessor implements InteractionProcessor {

    private Logger logger = LoggerUtil.getLogger(getClass());
    private Set<InteractionType> types;

    public DirectInteractionProcessor(InteractionType... types) {
        this.types = Set.of(types);
    }

    @Override
    public void process(Object context, InteractionIdentifier identifier, Marinara marinara) {
        if (!types.contains(identifier.type()))
            return;
        logger.debug("Processing {} : {} with context {}", identifier, identifier.type(), context);
        marinara.getRegistry().interactions().stream()
            .filter(e -> e.identifier().equals(identifier))
            .findFirst()
            .ifPresent(e -> e.runAll(context));
    }
    
}
