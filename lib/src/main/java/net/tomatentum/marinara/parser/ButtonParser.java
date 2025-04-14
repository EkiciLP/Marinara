package net.tomatentum.marinara.parser;

import java.lang.reflect.Method;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.tomatentum.cutin.MethodParser;
import net.tomatentum.cutin.util.ReflectionUtil;
import net.tomatentum.marinara.interaction.annotation.Button;

public class ButtonParser implements MethodParser {
    
    private Method method;
    private Consumer<String> consumer;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public ButtonParser(Method method, Consumer<String> consumer) {
        this.method = method;
        this.consumer = consumer;
    }

    @Override
    public void parse() {
        Button button = this.method.getAnnotation(Button.class);
        logger.trace("Parsed Button annotation {} for method {}", button, ReflectionUtil.getFullMethodName(method));
        this.consumer.accept(button.value());
    }
    
}
