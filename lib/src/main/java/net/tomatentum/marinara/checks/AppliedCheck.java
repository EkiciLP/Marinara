package net.tomatentum.marinara.checks;

import java.lang.annotation.Annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.tomatentum.cutin.method.ReflectedMethod;

public record AppliedCheck(
        Annotation annotation, 
        ReflectedMethod<CheckMethodIdentifier, CheckExecutionContext> preExec,
        ReflectedMethod<CheckMethodIdentifier, CheckExecutionContext> postExec
    ) {

    private static Logger logger = LoggerFactory.getLogger(AppliedCheck.class); 

    public boolean pre(Object context) {
        logger.debug("Running InteractionCheck preExec {} with annotation {}", preExec(), annotation());
        return (boolean) preExec().run(new CheckExecutionContext(annotation, context));
    }

    public void post(Object context) {
        logger.debug("Running InteractionCheck postExec {} with annotation {}", postExec(), annotation());
        postExec().run(new CheckExecutionContext(annotation, context));
    }

}
