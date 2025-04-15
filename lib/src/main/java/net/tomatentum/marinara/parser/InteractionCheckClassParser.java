package net.tomatentum.marinara.parser;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.leangen.geantyref.GenericTypeReflector;
import net.tomatentum.cutin.MethodParser;
import net.tomatentum.marinara.checks.InteractionCheck;

public class InteractionCheckClassParser implements MethodParser {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Object parse(Method method, Object containingObject) {
        ParameterizedType type = (ParameterizedType) GenericTypeReflector.getExactSuperType(containingObject.getClass(), InteractionCheck.class);
        if (type == null) return null;
        Type typeParam = type.getActualTypeArguments().length == 1 ? type.getActualTypeArguments()[0] : null;
        if (typeParam != null)
            logger.trace("Parsed InteractionCheck Annotation {}", typeParam);
        return typeParam;
    }
    
}
