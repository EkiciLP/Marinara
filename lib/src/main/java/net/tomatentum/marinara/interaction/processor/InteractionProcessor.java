package net.tomatentum.marinara.interaction.processor;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;

public interface InteractionProcessor {

    void process(Object context, InteractionIdentifier identifier, Marinara marinara);
    
}
