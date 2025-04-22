package net.tomatentum.marinara.structure;

import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;

public interface StructureProvider<B extends Object> {
    B button(InteractionIdentifier identifier);
}
