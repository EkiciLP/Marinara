package net.tomatentum.marinara.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    public static int getCastDepth(Class<?> child, Class<?> parent) {
        if (!parent.isAssignableFrom(child)) {
            throw new IllegalArgumentException("The specified class is not a child class of the specified parent.");
        }

        int depth = 0;
        Class<?> curr = child;
        List<Class<?>> parents = new ArrayList<>();

        while (!curr.equals(parent)) {
            depth++;
            parents.add(curr.getSuperclass());
            parents.addAll(Arrays.asList(curr.getInterfaces()));

            for (Class<?> currParent : parents) {
                if (currParent != null && parent.isAssignableFrom(currParent)) {
                    curr = currParent;
                    break;
                }
            }
            parents.clear();
        }

        return depth;
    }
    
    public static Method getMostSpecificMethod(Method[] methods, Class<?>... parameters) {
        List<Method> compatibleMethods = Arrays.stream(methods)
            .filter(x -> isMethodCallable(x, parameters))
            .toList();

        if (compatibleMethods.size() == 0)
            throw new IllegalArgumentException("There are no compatible Methods provided");

        for (int i = 0; i < parameters.length; i++) {
            final int currI = i;
            Class<?>[] parameterTypes = compatibleMethods.stream()
                .map(x -> x.getParameterTypes()[currI])
                .toArray(x -> new Class[x]);

            Class<?> mostSpecific = getMostSpecificClass(parameterTypes, parameters[i]);

            compatibleMethods = compatibleMethods.stream()
                .filter(x -> Objects.equals(x.getParameterTypes()[currI], mostSpecific))
                .toList();
        }
        
        return compatibleMethods.getFirst();
    }

    public static Class<?> getMostSpecificClass(Class<?>[] classes, Class<?> base) {
        int min = Integer.MAX_VALUE;
        Class<?> currMostSpecific = null;
        for (Class<?> currClass : classes) {
            int currCastDepth = getCastDepth(base, currClass);
            if (currCastDepth < min) {
                min = currCastDepth;
                currMostSpecific = currClass;
            }
        }
        return currMostSpecific;
    }

    public static boolean isMethodCallable(Method method, Class<?>... parameters) {
        if (!Objects.equals(method.getParameterCount(), parameters.length))
            return false;

        Class<?>[] methodParams = method.getParameterTypes();
        for (int i = 0; i < parameters.length; i++) {
            if (!methodParams[i].isAssignableFrom(parameters[i]))
                return false;
        }
        return true;
    }
}
