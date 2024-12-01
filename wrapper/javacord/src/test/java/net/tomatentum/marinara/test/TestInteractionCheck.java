package net.tomatentum.marinara.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.tomatentum.marinara.checks.InteractionCheck;

public class TestInteractionCheck implements InteractionCheck<TestInteractionCheck.TestCheck> {

    public static boolean preExecuted = false;
    public static boolean postExecuted = false;

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface TestCheck {
    }

    @Override
    public boolean preExec(Object context, TestCheck annotation) {
        assertNotNull(annotation);
        assertNotNull(context);
        preExecuted = true;
        return true;
    }

    @Override
    public void postExec(Object context, TestCheck annotation) {
        assertNotNull(annotation);
        assertNotNull(context);
        postExecuted = true;
    }
    
}
