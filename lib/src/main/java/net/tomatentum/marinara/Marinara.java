package net.tomatentum.marinara;

import net.tomatentum.marinara.registry.InteractionRegistry;
import net.tomatentum.marinara.wrapper.LibraryWrapper;

public class Marinara {
    
    public static <T extends LibraryWrapper> Marinara load(LibraryWrapper wrapper) {
        InteractionRegistry registry = new InteractionRegistry(wrapper);
        return new Marinara(registry);
    }

    private InteractionRegistry registry;

    private Marinara(InteractionRegistry registry) {
        this.registry = registry;
    }

    public InteractionRegistry getRegistry() {
        return registry;
    }
}
