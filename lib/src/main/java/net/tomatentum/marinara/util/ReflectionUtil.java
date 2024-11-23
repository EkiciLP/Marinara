package net.tomatentum.marinara.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public final class ReflectionUtil {

    public static <T extends Annotation> boolean isAnnotationPresent(Method method, Class<T> annotationClass) {
        if (method.isAnnotationPresent(annotationClass) || method.getDeclaringClass().isAnnotationPresent(annotationClass))
            return true;

        return false;
    }

    public static <T extends Annotation> T getAnnotation(Method method, Class<T> annotationClass) {
        return method.isAnnotationPresent(annotationClass) ? 
            method.getAnnotation(annotationClass) : 
            method.getDeclaringClass().getAnnotation(annotationClass);
    }
    
}
