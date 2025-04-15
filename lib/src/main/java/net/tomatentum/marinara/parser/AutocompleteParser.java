package net.tomatentum.marinara.parser;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.tomatentum.cutin.MethodParser;
import net.tomatentum.cutin.util.ReflectionUtil;
import net.tomatentum.marinara.interaction.annotation.AutoComplete;

public class AutocompleteParser implements MethodParser {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Object parse(Method method, Object containingObject) {
        String[] autoCompletes = Arrays.stream(method.getAnnotationsByType(AutoComplete.class))
            .map(AutoComplete::value)
            .toArray(String[]::new);
        if (autoCompletes.length > 0)
            logger.trace("Parsed AutoComplete annotations {} for method {}", autoCompletes, ReflectionUtil.getFullMethodName(method));
        return autoCompletes;
    }
    
}
