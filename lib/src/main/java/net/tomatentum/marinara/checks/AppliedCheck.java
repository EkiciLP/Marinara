package net.tomatentum.marinara.checks;

import java.lang.annotation.Annotation;

import org.slf4j.Logger;

import net.tomatentum.cutin.method.ReflectedMethod;
import net.tomatentum.marinara.util.LoggerUtil;

public record AppliedCheck(
        Annotation annotation, 
        ReflectedMethod<CheckMethodIdentifier, CheckExecutionContext> preExec,
        ReflectedMethod<CheckMethodIdentifier, CheckExecutionContext> postExec
    ) {

    private static Logger logger = LoggerUtil.getLogger(AppliedCheck.class); 

    public boolean pre(Object context) {
        logger.debug("Running InteractionCheck preExec {} with annotation {}", preExec(), annotation());
        return (boolean) preExec().run(new CheckExecutionContext(annotation, context));
    }

    public void post(Object context) {
        logger.debug("Running InteractionCheck postExec {} with annotation {}", postExec(), annotation());
        postExec().run(new CheckExecutionContext(annotation, context));
    }

}
