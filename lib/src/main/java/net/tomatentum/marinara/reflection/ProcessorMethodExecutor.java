package net.tomatentum.marinara.reflection;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;

import net.tomatentum.marinara.util.LoggerUtil;

public class ProcessorMethodExecutor implements MethodExecutor, ProcessorContainer {

    private Logger logger = LoggerUtil.getLogger(getClass());

    private Set<MethodProcessor> processors;

    public ProcessorMethodExecutor() {
        this.processors = new HashSet<>();
    }

    @Override
    public ProcessorContainer addProcessor(MethodProcessor processor) {
        processors.add(processor);
        return this;
    }

    @Override
    public void handle(Object context) {
        logger.debug("Received {} interaction ", context);
        processors.forEach(x -> x.process(context));
    }
    
}
