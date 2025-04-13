package net.tomatentum.marinara.parser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Consumer;

import org.slf4j.Logger;

import net.tomatentum.cutin.MethodParser;
import net.tomatentum.cutin.container.MethodContainer;
import net.tomatentum.cutin.util.ReflectionUtil;
import net.tomatentum.marinara.checks.AppliedCheck;
import net.tomatentum.marinara.checks.CheckExecutionContext;
import net.tomatentum.marinara.checks.CheckMethodIdentifier;
import net.tomatentum.marinara.checks.CheckMethodIdentifier.CheckMethodType;
import net.tomatentum.marinara.util.LoggerUtil;

public class InteractionCheckParser implements MethodParser {

    private MethodContainer<CheckMethodIdentifier, CheckExecutionContext> checkContainer;
    private Method method;
    private Consumer<AppliedCheck> consumer;

    private Logger logger = LoggerUtil.getLogger(getClass());

    public InteractionCheckParser(Method method, Consumer<AppliedCheck> consumer, MethodContainer<CheckMethodIdentifier, CheckExecutionContext> checkContainer) {
        this.checkContainer = checkContainer;
        this.method = method;
        this.consumer = consumer;
    }

    @Override
    public void parse() {
        Annotation[] annotations = method.getAnnotations();
        Arrays.stream(annotations).forEach(this::convertAnnotation);
    }

    private void convertAnnotation(Annotation annotation) {
            var preExec = this.checkContainer.findFirstFor(new CheckMethodIdentifier(annotation.annotationType(), CheckMethodType.PRE));
            var postExec = this.checkContainer.findFirstFor(new CheckMethodIdentifier(annotation.annotationType(), CheckMethodType.POST));
            if (preExec.isPresent() && postExec.isPresent())  {
                AppliedCheck appliedCheck = new AppliedCheck(annotation, preExec.get(), postExec.get());
                logger.trace("Parsed InteractionCheck {} for annotation {} for method {}", preExec.get().containingObject(), annotation, ReflectionUtil.getFullMethodName(method));
                consumer.accept(appliedCheck);
            }
    }
    
}
