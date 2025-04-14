package net.tomatentum.marinara.parser;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.leangen.geantyref.GenericTypeReflector;
import net.tomatentum.cutin.MethodParser;
import net.tomatentum.marinara.checks.InteractionCheck;

public class InteractionCheckClassParser implements MethodParser {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Class<? extends InteractionCheck<?>> interactionCheckType;
    private Consumer<Type> annotationTypeConsumer;

    public InteractionCheckClassParser(Class<? extends InteractionCheck<?>> interactionCheckType, Consumer<Type> annotationTypeConsumer) {
        this.interactionCheckType = interactionCheckType;
        this.annotationTypeConsumer = annotationTypeConsumer;
    }

    @Override
    public void parse() {
        ParameterizedType type = (ParameterizedType) GenericTypeReflector.getExactSuperType(interactionCheckType, InteractionCheck.class);
        Type typeParam = type.getActualTypeArguments()[0];
        logger.trace("Parsed InteractionCheck Annotation {}", typeParam);
        this.annotationTypeConsumer.accept(typeParam);
    }
    
}
