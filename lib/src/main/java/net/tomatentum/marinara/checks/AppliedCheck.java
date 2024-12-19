package net.tomatentum.marinara.checks;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.logging.log4j.Logger;

import net.tomatentum.marinara.util.LoggerUtil;
import net.tomatentum.marinara.util.ReflectionUtil;

public record AppliedCheck(InteractionCheck<?> check, Annotation annotation) {

    private static Logger logger = LoggerUtil.getLogger(AppliedCheck.class);
    
    public boolean pre(Object context) {
        Method[] methods = Arrays.stream(check.getClass().getMethods())
            .filter(x -> x.getName().equals("preExec"))
            .filter(x -> !x.isBridge())
            .toArray(s -> new Method[s]);
        Method method = ReflectionUtil.getMostSpecificMethod(methods, context.getClass(), annotation.annotationType());
        method.setAccessible(true);
        try {
            logger.debug("Executing pre check {} with context {}", check.getClass().getName(), context.toString());
            return (boolean) method.invoke(check, context, annotation);
        } catch (IllegalAccessException | InvocationTargetException | SecurityException e) {
            logger.fatal(e);
            return false;
        }
    }

    public void post(Object context) {
        Method[] methods = Arrays.stream(check.getClass().getMethods())
            .filter(x -> x.getName().equals("postExec"))
            .filter(x -> !x.isBridge())
            .toArray(s -> new Method[s]);
        Method method = ReflectionUtil.getMostSpecificMethod(methods, context.getClass(), annotation.annotationType());
        method.setAccessible(true);
        try {
            logger.debug("Executing post check {} with context {}", check.getClass().getName(), context.toString());
            method.invoke(check, context, annotation);
        } catch (IllegalAccessException | InvocationTargetException | SecurityException e) {
            logger.fatal(e);
        }
    }

}
