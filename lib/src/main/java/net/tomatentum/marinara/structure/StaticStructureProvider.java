package net.tomatentum.marinara.structure;

import java.util.HashMap;
import java.util.Map;

import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.structure.data.ButtonStructureData;
import net.tomatentum.marinara.wrapper.ComponentStructureConverter;

public class StaticStructureProvider<B extends Object> implements StructureProvider<B> {

    private ComponentStructureConverter<B> converter;

    private Map<InteractionIdentifier, B> buttonMap;

    public StaticStructureProvider(ComponentStructureConverter<B> converter) {
        this.converter = converter;
        this.buttonMap = new HashMap<>();
    }

    public StaticStructureProvider<B> addButton(InteractionIdentifier identifier, ButtonStructureData data) {
        this.buttonMap.put(identifier, this.converter.convertButton(data));
        return this;
    }
    
    @Override
    public B button(InteractionIdentifier identifier) {
        return this.buttonMap.get(identifier);
    }
    
}
