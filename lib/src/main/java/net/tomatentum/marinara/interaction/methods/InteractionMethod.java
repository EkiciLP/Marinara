package net.tomatentum.marinara.interaction.methods;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.Logger;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.checks.AppliedCheck;
import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.annotation.Button;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommand;
import net.tomatentum.marinara.parser.AnnotationParser;
import net.tomatentum.marinara.parser.InteractionCheckParser;
import net.tomatentum.marinara.util.LoggerUtil;
import net.tomatentum.marinara.util.ReflectionUtil;

public abstract class InteractionMethod {

    public static InteractionMethod create(Method method, InteractionHandler handler, Marinara marinara) {
        if (method.isAnnotationPresent(SlashCommand.class) || method.isAnnotationPresent(SubCommand.class))
            return new SlashCommandInteractionMethod(method, handler, marinara);
        if (method.isAnnotationPresent(Button.class))
            return new ButtonInteractionMethod(method, handler, marinara);
        return null;
    }

    protected Method method;
    protected InteractionHandler handler;
    protected Marinara marinara;
    protected List<AnnotationParser> parsers;
    protected List<AppliedCheck> appliedChecks;

    private Logger logger = LoggerUtil.getLogger(getClass());

    protected InteractionMethod(Method method, 
        InteractionHandler handler, 
        Marinara marinara
        ) {
        if (!Arrays.asList(handler.getClass().getMethods()).contains(method))
            throw new InvalidParameterException("Method does not apply to specified handler");

        this.method = method;
        this.handler = handler;
        this.marinara = marinara;
        this.parsers = new ArrayList<>(Arrays.asList(getParsers()));
        this.appliedChecks = new ArrayList<>();

        parsers.add(new InteractionCheckParser(method, appliedChecks::add, marinara.getCheckRegistry()));

        parsers.stream().forEach(AnnotationParser::parse);
    }

    public abstract AnnotationParser[] getParsers();

    public abstract Object getParameter(Object parameter, int index);

    public abstract boolean canRun(Object context);

    public abstract InteractionType getType();

    public void run(Object context) {
        if (this.appliedChecks.stream().filter(x -> !x.pre(context)).count() > 0)
            return;

        method.setAccessible(true);
        try {
            method.invoke(handler, getParameters(context));
        }catch (IllegalAccessException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }

        this.appliedChecks.forEach(x -> x.post(context));
    }

    public Method getMethod() {
        return method;
    }

    private Object[] getParameters(Object context) {
        int parameterCount = method.getParameterCount();
        List<Object> parameters = new ArrayList<>();
        
        for (int i = 0; i < parameterCount; i++) {
            Object parameter;
            if (i == 0) {
                parameter = context;
            }else
                parameter = getParameter(context, i-1);

            logger.trace("Found parameter {}={} for method {}", parameter.getClass().toString(), parameter, ReflectionUtil.getFullMethodName(method));
            parameters.add(parameter);   
        }
        return parameters.toArray();
    }

}
