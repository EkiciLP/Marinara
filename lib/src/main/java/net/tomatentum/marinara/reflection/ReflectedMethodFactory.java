package net.tomatentum.marinara.reflection;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.parser.AnnotationParser;

public interface ReflectedMethodFactory {
    Optional<ReflectedMethod> produce(Method method, Object containingClass);
    ReflectedMethodFactory addFactory(Factory factory);

    public interface Factory {

        Optional<ReflectedMethod> produce(Marinara marinara, Method method, Object containingObject);
        void addParser(ReflectedMethod method, List<AnnotationParser> parser);

    }
}