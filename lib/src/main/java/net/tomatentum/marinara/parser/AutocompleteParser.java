package net.tomatentum.marinara.parser;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.tomatentum.cutin.MethodParser;
import net.tomatentum.cutin.util.ReflectionUtil;
import net.tomatentum.marinara.interaction.annotation.AutoComplete;

public class AutocompleteParser implements MethodParser {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Method method;
    private Consumer<String[]> consumer;

    public AutocompleteParser(Method method, Consumer<String[]> consumer) {
        this.method = method;
        this.consumer = consumer;
    }

    @Override
    public void parse() {
        String[] autocompletes = Arrays.stream(this.method.getAnnotationsByType(AutoComplete.class))
            .map(AutoComplete::value)
            .toArray(String[]::new);
        logger.trace("Parsed AutoComplete annotation {} for method {}", autocompletes, ReflectionUtil.getFullMethodName(method));
        this.consumer.accept(autocompletes);
    }
    
}
