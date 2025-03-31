package net.tomatentum.marinara.wrapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import io.leangen.geantyref.GenericTypeReflector;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.registry.InteractionRegistry;
import net.tomatentum.marinara.util.LoggerUtil;
import net.tomatentum.marinara.util.ReflectionUtil;

public class IdentifierProvider {

    public static IdentifierProvider of(Converter<?>... converter) {
        return new IdentifierProvider(Arrays.asList(converter));
    }

    private Map<Class<?>, Converter<?>> converter;
    private Logger logger = LoggerUtil.getLogger(getClass());

    private IdentifierProvider(List<Converter<?>> converter) {
        this.converter = new HashMap<>();
        for (Converter<?> conv : converter) {
            if (conv.getClass().getName().contains("$$Lambda"))
                throw new IllegalArgumentException("Lambdas cannot be used for IdentifierConverter because of Type erasure.");
            Type type = GenericTypeReflector.getExactSuperType(conv.getClass(), Converter.class);
            Type parameterType = (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0];
            if (!(parameterType instanceof Class))
                throw new IllegalArgumentException("Only full Class types are supported by IdentiferConverters");
            this.converter.put((Class<?>) parameterType, conv);
        }
    }

    public InteractionIdentifier provide(Object context, InteractionRegistry registry) {
        Type type = ReflectionUtil.getMostSpecificClass(
            converter.keySet().stream().filter(x -> x.isAssignableFrom(context.getClass())).toArray(Class<?>[]::new), 
            context.getClass());

        if (type == null)
            logger.debug("No Identifier converter found for context {}", context.getClass().toString());

        @SuppressWarnings("unchecked")
        Converter<Object> conv = (Converter<Object>) converter.get(type);

        return conv.convert(context, registry);
    }

    @FunctionalInterface
    public interface Converter<T extends Object> {
        InteractionIdentifier convert(T context, InteractionRegistry registry);
    }

    public static class LambdaWrapper<T extends Object> implements Converter<T> {

        private Converter<T> converter;

        LambdaWrapper(Converter<T> converter) {
            this.converter = converter;
        }

        @Override
        public InteractionIdentifier convert(T context, InteractionRegistry registry) {
            return this.converter.convert(context, registry);
        }

    }
}
