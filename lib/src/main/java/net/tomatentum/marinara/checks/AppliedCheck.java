package net.tomatentum.marinara.checks;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import net.tomatentum.marinara.util.ReflectionUtil;

public record AppliedCheck(InteractionCheck<?> check, Annotation annotation) {
    
    public boolean pre(Object context) {
        Method[] methods = Arrays.stream(check.getClass().getMethods())
            .filter(x -> x.getName().equals("preExec"))
            .filter(x -> !x.isBridge())
            .toArray(s -> new Method[s]);
        Method method = ReflectionUtil.getMostSpecificMethod(methods, context.getClass(), annotation.getClass());
        method.setAccessible(true);
        try {
            return (boolean) method.invoke(check, context, annotation);
        } catch (IllegalAccessException | InvocationTargetException | SecurityException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void post(Object context) {
        Method[] methods = Arrays.stream(check.getClass().getMethods())
            .filter(x -> x.getName().equals("postExec"))
            .filter(x -> !x.isBridge())
            .toArray(s -> new Method[s]);
        Method method = ReflectionUtil.getMostSpecificMethod(methods, context.getClass(), annotation.getClass());
        method.setAccessible(true);
        try {
            method.invoke(check, context, annotation);
        } catch (IllegalAccessException | InvocationTargetException | SecurityException e) {
            e.printStackTrace();
        }
    }

}
