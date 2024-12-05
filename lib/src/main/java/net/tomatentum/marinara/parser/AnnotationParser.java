package net.tomatentum.marinara.parser;

import java.lang.reflect.Method;

public interface AnnotationParser {
    void parse();
    Method getMethod();
}
