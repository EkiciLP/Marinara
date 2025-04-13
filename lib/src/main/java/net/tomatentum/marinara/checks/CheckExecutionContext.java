package net.tomatentum.marinara.checks;

import java.lang.annotation.Annotation;

public record CheckExecutionContext(Annotation annotation, Object originalContext) {
    
}
