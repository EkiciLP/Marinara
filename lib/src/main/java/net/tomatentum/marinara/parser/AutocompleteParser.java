package net.tomatentum.marinara.parser;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Consumer;

import net.tomatentum.marinara.interaction.annotation.AutoComplete;

public class AutocompleteParser implements AnnotationParser {

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
            this.consumer.accept(autocompletes);

    }

    @Override
    public Method getMethod() {
        return this.method;
    }
    
}
