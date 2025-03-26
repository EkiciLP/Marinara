package net.tomatentum.marinara.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;

import net.tomatentum.marinara.parser.AnnotationParser;

public abstract class ReflectedMethod {

    private Logger logger = LoggerUtil.getLogger(getClass());

    private Method method;
    private Object containingObject;
    protected List<AnnotationParser> parsers;

    public ReflectedMethod(Method method, Object containingObject) {
        if (!Arrays.asList(containingObject.getClass().getMethods()).contains(method))
            throw new InvalidParameterException("Method does not apply to specified handler");
        this.method = method;
        this.containingObject = containingObject;
        this.parsers = new ArrayList<>(Arrays.asList(provideParsers()));

        this.parsers.stream().forEach(AnnotationParser::parse);
    }

    public abstract Object getParameter(Object context, int index);

    public abstract AnnotationParser[] provideParsers();

    public Object run(Object context) {
        method.setAccessible(true);
        try {
            return method.invoke(containingObject, getParameters(context));
        }catch (IllegalAccessException | InvocationTargetException ex) {
            logger.error("ReflectedMethod failed to run", ex);
            return null;
        }
    }

    public Method method() {
        return this.method;
    }

    public Object containingObject() {
        return this.containingObject;
    }

    public List<AnnotationParser> parsers() {
        return this.parsers;
    }

    private Object[] getParameters(Object context) {
        int parameterCount = method.getParameterCount();
        List<Object> parameters = new ArrayList<>();
        
        for (int i = 0; i < parameterCount; i++) {
            Object parameter;
            if (i == 0) {
                parameter = context;
            }else
                parameter = getParameter(context, i-1);

            logger.trace("Found parameter {}={} for method {}", parameter != null ? parameter.getClass().toString() : " ", parameter, ReflectionUtil.getFullMethodName(method));
            parameters.add(parameter);   
        }
        return parameters.toArray();
    }
    
}
