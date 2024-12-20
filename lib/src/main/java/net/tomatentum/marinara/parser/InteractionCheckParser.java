package net.tomatentum.marinara.parser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;

import org.apache.logging.log4j.Logger;

import net.tomatentum.marinara.checks.AppliedCheck;
import net.tomatentum.marinara.checks.InteractionCheck;
import net.tomatentum.marinara.registry.InteractionCheckRegistry;
import net.tomatentum.marinara.util.LoggerUtil;
import net.tomatentum.marinara.util.ReflectionUtil;

public class InteractionCheckParser implements AnnotationParser {

    private InteractionCheckRegistry checkRegistry;
    private Method method;
    private Consumer<AppliedCheck> consumer;

    private Logger logger = LoggerUtil.getLogger(getClass());

    public InteractionCheckParser(Method method, Consumer<AppliedCheck> consumer, InteractionCheckRegistry checkRegistry) {
        this.checkRegistry = checkRegistry;
        this.method = method;
        this.consumer = consumer;
    }

    @Override
    public void parse() {
        Annotation[] annotations = method.getAnnotations();
        Arrays.stream(annotations).forEach(this::convertAnnotation);
    }

    private void convertAnnotation(Annotation annotation) {
            Optional<InteractionCheck<?>> check = this.checkRegistry.getCheckFromAnnotation(annotation.annotationType());
            if (check.isPresent())  {
                AppliedCheck appliedCheck = new AppliedCheck(check.get(), annotation);
                logger.trace("Parsed InteractionCheck {} for annotation {} for method {}", check.getClass().getName(), annotation.toString(), ReflectionUtil.getFullMethodName(method));
                consumer.accept(appliedCheck);
            }
    }

    @Override
    public Method getMethod() {
        return this.method;
    }
    
}
