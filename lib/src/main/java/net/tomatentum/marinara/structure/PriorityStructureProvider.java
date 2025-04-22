package net.tomatentum.marinara.structure;

import java.util.Map;
import java.util.TreeMap;

import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;

public class PriorityStructureProvider<B extends Object> implements StructureProvider<B> {

    private Map<Short, StructureProvider<B>> providerMap;

    public PriorityStructureProvider() {
        this.providerMap = new TreeMap<>();
    }

    public PriorityStructureProvider<B> addProvider(StructureProvider<B> provider, short priority) {
        this.providerMap.put(priority, provider);
        return this;
    }

    @Override
    public B button(InteractionIdentifier identifier) {
        for (short priority : this.providerMap.keySet()) {
            B result = this.providerMap.get(priority).button(identifier);
            if (result != null)
                return result;
        }
        return null;
    }

}
