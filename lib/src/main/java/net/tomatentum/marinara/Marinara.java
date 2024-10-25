package net.tomatentum.marinara;

import java.lang.reflect.Constructor;

import net.tomatentum.marinara.registry.InteractionRegistry;
import net.tomatentum.marinara.wrapper.LibraryWrapper;

public class Marinara {
    
    public static <T extends LibraryWrapper> Marinara load(Class<T> clazz) {
        try {
            Constructor<T> ctor = clazz.getConstructor();
            ctor.setAccessible(true);
            T wrapper = ctor.newInstance();
            InteractionRegistry registry = new InteractionRegistry(wrapper);
            return new Marinara(registry);
        }catch (Exception ex) {
            System.err.println(ex);
            System.exit(100);
            return null;
        }
    }

    private InteractionRegistry registry;

    private Marinara(InteractionRegistry registry) {
        this.registry = registry;
    }

    public InteractionRegistry getRegistry() {
        return registry;
    }
}
