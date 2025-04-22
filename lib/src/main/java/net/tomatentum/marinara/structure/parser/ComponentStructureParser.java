package net.tomatentum.marinara.structure.parser;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.tomatentum.cutin.MethodParser;
import net.tomatentum.cutin.util.ReflectionUtil;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.structure.annotation.ComponentStructure;

public class ComponentStructureParser implements MethodParser {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Class<? extends Object> buttonClass;

    public ComponentStructureParser(Class<? extends Object> buttonClass) {
        this.buttonClass = buttonClass;
    }

    @Override
    public Object parse(Method method, Object containingObject) {
        if (!method.isAnnotationPresent(ComponentStructure.class)) 
            return null;
        InteractionIdentifier.Builder builder = InteractionIdentifier.builder()
            .name(method.getAnnotation(ComponentStructure.class).customId());

        if (buttonClass.isAssignableFrom(method.getReturnType()))
            builder.type(InteractionType.BUTTON);

        if (builder.type() == null) {
            logger.error("Structure Method {} return type did not match any of the required {}", String.join(","), buttonClass);
            return null;
        }

        logger.trace("Parsed Structure Method of {} with result {}", ReflectionUtil.getFullMethodName(method), builder.build());
        return builder.build();

    }
    
}
