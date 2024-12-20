package net.tomatentum.marinara;

import org.apache.logging.log4j.Logger;

import net.tomatentum.marinara.registry.InteractionCheckRegistry;
import net.tomatentum.marinara.registry.InteractionRegistry;
import net.tomatentum.marinara.util.LoggerUtil;
import net.tomatentum.marinara.wrapper.LibraryWrapper;

public class Marinara {

    private Logger logger = LoggerUtil.getLogger(getClass());
    
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
        logger.info("Marinara loaded successfully!");
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
