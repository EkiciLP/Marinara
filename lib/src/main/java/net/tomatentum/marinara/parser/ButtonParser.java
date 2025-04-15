package net.tomatentum.marinara.parser;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.tomatentum.cutin.MethodParser;
import net.tomatentum.cutin.util.ReflectionUtil;
import net.tomatentum.marinara.interaction.annotation.Button;

public class ButtonParser implements MethodParser {
    
    private Logger logger = LoggerFactory.getLogger(getClass());

    public ButtonParser() {
    }

    @Override
    public Object parse(Method method, Object containingObject) {
        Button button = method.getAnnotation(Button.class);
        if (button == null) return null;
        logger.trace("Parsed Button annotation {} for method {}", button, ReflectionUtil.getFullMethodName(method));
        return button.value();
    }
    
}
