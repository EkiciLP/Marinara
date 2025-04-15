package net.tomatentum.marinara.parser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.tomatentum.cutin.MethodParser;
import net.tomatentum.cutin.container.MethodContainer;
import net.tomatentum.cutin.util.ReflectionUtil;
import net.tomatentum.marinara.checks.AppliedCheck;
import net.tomatentum.marinara.checks.CheckExecutionContext;
import net.tomatentum.marinara.checks.CheckMethodIdentifier;
import net.tomatentum.marinara.checks.CheckMethodIdentifier.CheckMethodType;

public class InteractionCheckParser implements MethodParser {

    private MethodContainer<CheckMethodIdentifier, CheckExecutionContext> checkContainer;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public InteractionCheckParser(MethodContainer<CheckMethodIdentifier, CheckExecutionContext> checkContainer) {
        this.checkContainer = checkContainer;
    }

    @Override
    public Object parse(Method method, Object containingObject) {
        Annotation[] annotations = method.getAnnotations();
        return Arrays.stream(annotations)
            .map(a -> convertAnnotation(a, method))
            .filter(Objects::nonNull)
            .toList();
    }

    private AppliedCheck convertAnnotation(Annotation annotation, Method method) {
            var preExec = this.checkContainer.findFirstFor(new CheckMethodIdentifier(annotation.annotationType(), CheckMethodType.PRE));
            var postExec = this.checkContainer.findFirstFor(new CheckMethodIdentifier(annotation.annotationType(), CheckMethodType.POST));
            if (preExec.isPresent() && postExec.isPresent())  {
                AppliedCheck appliedCheck = new AppliedCheck(annotation, preExec.get(), postExec.get());
                logger.trace("Parsed InteractionCheck {} for annotation {} for method {}", preExec.get().containingObject(), annotation, ReflectionUtil.getFullMethodName(method));
                return appliedCheck;
            }
            return null;
    }
    
}
