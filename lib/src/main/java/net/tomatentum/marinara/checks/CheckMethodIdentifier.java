package net.tomatentum.marinara.checks;

import java.lang.reflect.Type;

public record CheckMethodIdentifier(Type annotationType, CheckMethodType type) {

    public enum CheckMethodType {
        PRE("preExec"),
        POST("postExec");

        private String methodName;

        private CheckMethodType(String methodName) {
            this.methodName = methodName;
        }

        public String methodName() {
            return this.methodName;
        }
    }

    @Override
    public final String toString() {
        return "InteractionCheck(%s, %s)".formatted(annotationType, type);
    }
    
}
