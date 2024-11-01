package net.tomatentum.marinara.interaction.methods;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommand;
import net.tomatentum.marinara.wrapper.LibraryWrapper;

public abstract class InteractionMethod {

    public static InteractionMethod create(Method method, InteractionHandler handler, LibraryWrapper wrapper) {
        if (method.isAnnotationPresent(SlashCommand.class) || method.isAnnotationPresent(SubCommand.class))
            return new SlashCommandInteractionMethod(method, handler, wrapper);
        
        return null;
    }

    protected Method method;
    protected InteractionHandler handler;
    protected LibraryWrapper wrapper;

    protected InteractionMethod(Method method, InteractionHandler handler, LibraryWrapper wrapper) {
        if (!Arrays.asList(handler.getClass().getMethods()).contains(method))
            throw new InvalidParameterException("Method does not apply to specified handler");
        this.method = method;
        this.handler = handler;
        this.wrapper = wrapper;
    }

    public abstract Object getParameter(Object parameter, int index);

    public abstract boolean canRun(Object context);

    public abstract InteractionType getType();

    public void run(Object context) {
        int parameterCount = method.getParameterCount();
        List<Object> parameters = new ArrayList<>();
        
        for (int i = 0; i < parameterCount; i++) {
            if (i == 0) {
                parameters.add(context);
                continue;
            }
            parameters.add(getParameter(context, i-1));
        }
        method.setAccessible(true);
        try {
            method.invoke(handler, parameters.toArray());
        }catch (IllegalAccessException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Method getMethod() {
        return method;
    }

}
