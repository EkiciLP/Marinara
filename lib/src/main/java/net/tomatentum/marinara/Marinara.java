package net.tomatentum.marinara;

import net.tomatentum.marinara.registry.InteractionCheckRegistry;
import net.tomatentum.marinara.registry.InteractionRegistry;
import net.tomatentum.marinara.wrapper.LibraryWrapper;

public class Marinara {
    
    public static <T extends LibraryWrapper> Marinara load(LibraryWrapper wrapper) {
        return new Marinara(wrapper);
    }

    private InteractionRegistry registry;
    private InteractionCheckRegistry checkRegistry;
    private LibraryWrapper wrapper;

    private Marinara(LibraryWrapper wrapper) {
        this.wrapper = wrapper;
        this.registry = new InteractionRegistry(this);
        this.checkRegistry = new InteractionCheckRegistry();
    }

    public InteractionRegistry getRegistry() {
        return registry;
    }

    public InteractionCheckRegistry getCheckRegistry() {
        return checkRegistry;
    }

    public LibraryWrapper getWrapper() {
        return wrapper;
    }
}
