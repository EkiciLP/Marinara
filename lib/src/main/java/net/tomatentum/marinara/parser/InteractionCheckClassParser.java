package net.tomatentum.marinara.parser;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.function.Consumer;

import io.leangen.geantyref.GenericTypeReflector;
import net.tomatentum.cutin.MethodParser;
import net.tomatentum.marinara.checks.InteractionCheck;

public class InteractionCheckClassParser implements MethodParser {

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
        this.annotationTypeConsumer.accept(typeParam);
    }
    
}
