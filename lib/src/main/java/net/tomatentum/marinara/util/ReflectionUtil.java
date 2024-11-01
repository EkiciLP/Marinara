package net.tomatentum.marinara.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import net.tomatentum.marinara.interaction.commands.annotation.SlashCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommand;

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

    public static void checkValidCommandMethod(Method method) {
        if (method.isAnnotationPresent(SlashCommand.class) && 
            method.getDeclaringClass().isAnnotationPresent(SlashCommand.class)) {
            throw new RuntimeException(method.getName() + ": Can't have ApplicationCommand Annotation on Class and Method");
        }

        if (!isAnnotationPresent(method, SlashCommand.class))
            throw new RuntimeException(method.getName() + ": Missing ApplicationCommand Annotation on either Class or Method");

        if ((method.isAnnotationPresent(SubCommand.class) && 
            !isAnnotationPresent(method, SlashCommand.class))) {
            throw new RuntimeException(method.getName() + ": Missing ApplicationCommand Annotation on either Method or Class");
        }
    }
    
}
