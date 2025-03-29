package net.tomatentum.marinara.reflection;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    public ReflectedMethod produce(Method method, Object containingClass) {
        Optional<ReflectedMethod> imethod = this.factories.stream()
            .map(f -> factoryProduce(f, method, containingClass))
            .filter(Objects::nonNull)
            .findFirst();

        if (imethod.isEmpty()) {
            logger.debug("Could not produce a ReflectedMethod for Method {}", ReflectionUtil.getFullMethodName(method));
            return null;
        }

        return imethod.get();
    }

    @Override
    public ReflectedMethodFactory addFactory(Factory factory) {
        this.factories.add(factory);
        return this;
    }

    private ReflectedMethod factoryProduce(Factory factory, Method method, Object containingClass) {
        List<AnnotationParser> parser = new ArrayList<>();
        ReflectedMethod m = factory.produce(this.marinara, method, containingClass);
        if (m != null) {
            factory.addParser(m, parser); 
            parser.forEach(AnnotationParser::parse);
        }
        return m;
    }
    
}
