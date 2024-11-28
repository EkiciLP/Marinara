package net.tomatentum.marinara.checks;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

public record AppliedCheck(InteractionCheck<?> check, Annotation annotation) {
    
    public boolean pre() {
        try {
            return (boolean) check.getClass().getMethod("preExec", annotation.getClass()).invoke(check, annotation);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean post() {
        try {
            return (boolean) check.getClass().getMethod("postExec", annotation.getClass()).invoke(check, annotation);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            return false;
        }
    }

}
