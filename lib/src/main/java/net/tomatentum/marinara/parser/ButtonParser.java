package net.tomatentum.marinara.parser;

import java.lang.reflect.Method;
import java.util.function.Consumer;

import org.apache.logging.log4j.Logger;

import net.tomatentum.marinara.interaction.annotation.Button;
import net.tomatentum.marinara.util.LoggerUtil;
import net.tomatentum.marinara.util.ReflectionUtil;

public class ButtonParser implements AnnotationParser {
    
    private Method method;
    private Consumer<String> consumer;

    private Logger logger = LoggerUtil.getLogger(getClass());

    public ButtonParser(Method method, Consumer<String> consumer) {
        this.method = method;
        this.consumer = consumer;
    }

    @Override
    public void parse() {
        Button button = getMethod().getAnnotation(Button.class);
        logger.trace("Parsed Button annotation {} for method {}", button.toString(), ReflectionUtil.getFullMethodName(method));
        this.consumer.accept(button.value());
    }

    @Override
    public Method getMethod() {
        return this.method;
    }
    
}
