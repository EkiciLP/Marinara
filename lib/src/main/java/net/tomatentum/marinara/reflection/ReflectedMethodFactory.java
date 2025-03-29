package net.tomatentum.marinara.reflection;

import java.lang.reflect.Method;
import java.util.List;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.parser.AnnotationParser;

public interface ReflectedMethodFactory {
    ReflectedMethod produce(Method method, Object containingClass);
    ReflectedMethodFactory addFactory(Factory factory);

    public interface Factory {

        ReflectedMethod produce(Marinara marinara, Method method, Object containingObject);
        void addParser(ReflectedMethod method, List<AnnotationParser> parser);

    }
}