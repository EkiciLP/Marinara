package net.tomatentum.marinara;

import org.slf4j.Logger;

import net.tomatentum.marinara.reflection.ReflectedMethodFactory;
import net.tomatentum.marinara.reflection.ReflectedMethodFactoryImpl;
import net.tomatentum.marinara.registry.InteractionCheckRegistry;
import net.tomatentum.marinara.registry.InteractionRegistry;
import net.tomatentum.marinara.util.LoggerUtil;
import net.tomatentum.marinara.wrapper.LibraryWrapper;

public class Marinara {

    private Logger logger = LoggerUtil.getLogger(getClass());
    
    public static <T extends LibraryWrapper> Marinara load(LibraryWrapper wrapper) {
        return new Marinara(wrapper);
    }

    private LibraryWrapper wrapper;
    private ReflectedMethodFactory reflectedMethodFactory;
    private InteractionRegistry registry;
    private InteractionCheckRegistry checkRegistry;

    private Marinara(LibraryWrapper wrapper) {
        this.wrapper = wrapper;
        this.reflectedMethodFactory = new ReflectedMethodFactoryImpl(this);
        this.registry = new InteractionRegistry(this);
        this.checkRegistry = new InteractionCheckRegistry();
        logger.info("Marinara loaded successfully!");
    }

    public LibraryWrapper getWrapper() {
        return this.wrapper;
    }

    public InteractionRegistry getRegistry() {
        return this.registry;
    }

    public InteractionCheckRegistry getCheckRegistry() {
        return this.checkRegistry;
    }

    public ReflectedMethodFactory getReflectedMethodFactory() {
        return this.reflectedMethodFactory;
    }
}
