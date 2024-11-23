package net.tomatentum.marinara.parser;

import java.lang.reflect.Method;
import java.util.function.Consumer;

import net.tomatentum.marinara.interaction.annotation.Button;

public class ButtonParser implements AnnotationParser {
    
    private Method method;
    private Consumer<String> consumer;

    public ButtonParser(Method method, Consumer<String> consumer) {
        this.method = method;
        this.consumer = consumer;
    }

    @Override
    public void parse() {
        Button button = getMethod().getAnnotation(Button.class);
        this.consumer.accept(button.value());
    }

    @Override
    public Method getMethod() {
        return this.method;
    }
    
}
