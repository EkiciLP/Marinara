package net.tomatentum.marinara.reflection;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.parser.AnnotationParser;
import net.tomatentum.marinara.util.LoggerUtil;
import net.tomatentum.marinara.util.ReflectionUtil;

public class ReflectedMethodFactoryImpl implements ReflectedMethodFactory {

    private Logger logger = LoggerUtil.getLogger(getClass());

    private Marinara marinara;
    private List<Factory> factories;

    public ReflectedMethodFactoryImpl(Marinara marinara) {
        this(marinara, new ArrayList<>());
    }

    public ReflectedMethodFactoryImpl(Marinara marinara, List<Factory> factories) {
        this.marinara = marinara;
        this.factories = factories;
    }

    @Override
    public Optional<ReflectedMethod> produce(Method method, Object containingClass) {
        Optional<ReflectedMethod> imethod = this.factories.stream()
            .map(f -> factoryProduce(f, method, containingClass))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .findFirst();

        if (imethod.isEmpty()) {
            logger.debug("Could not produce a ReflectedMethod for Method {}", ReflectionUtil.getFullMethodName(method));
        }

        return imethod;
    }

    @Override
    public ReflectedMethodFactory addFactory(Factory factory) {
        this.factories.add(factory);
        return this;
    }

    private Optional<ReflectedMethod> factoryProduce(Factory factory, Method method, Object containingClass) {
        List<AnnotationParser> parser = new ArrayList<>();
        Optional<ReflectedMethod> m = factory.produce(this.marinara, method, containingClass);
        m.ifPresent(x -> {
            factory.addParser(x, parser); 
            parser.forEach(AnnotationParser::parse);
        });
        return m;
    }
    
}
