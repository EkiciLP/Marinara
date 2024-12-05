package net.tomatentum.marinara.parser;

import java.lang.reflect.Method;
import java.util.function.Consumer;

import net.tomatentum.marinara.interaction.commands.ExecutableSlashCommandDefinition;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommandGroup;
import net.tomatentum.marinara.util.ReflectionUtil;

public class SlashCommandParser implements AnnotationParser {

    private Method method;
    private Consumer<ExecutableSlashCommandDefinition> consumer;

    public SlashCommandParser(Method method, Consumer<ExecutableSlashCommandDefinition> consumer) {
        this.method = method;
        this.consumer = consumer;
    }

    @Override
    public void parse() {
        this.checkValidCommandMethod(method);

        SlashCommand cmd = ReflectionUtil.getAnnotation(method, SlashCommand.class);
        ExecutableSlashCommandDefinition.Builder builder = new ExecutableSlashCommandDefinition.Builder();
        builder.setApplicationCommand(cmd);

        if (ReflectionUtil.isAnnotationPresent(method, SubCommandGroup.class)) {
            SubCommandGroup cmdGroup = ReflectionUtil.getAnnotation(method, SubCommandGroup.class);
            builder.setSubCommandGroup(cmdGroup);
        }

        if (ReflectionUtil.isAnnotationPresent(method, SubCommand.class)) {
            SubCommand subCmd = ReflectionUtil.getAnnotation(method, SubCommand.class);
            builder.setSubCommand(subCmd);
        }

        consumer.accept(builder.build());
    }

    @Override
    public Method getMethod() {
       return this.method;
    }

    private void checkValidCommandMethod(Method method) {
        if (method.isAnnotationPresent(SlashCommand.class) && 
            method.getDeclaringClass().isAnnotationPresent(SlashCommand.class)) {
            throw new RuntimeException(method.getName() + ": Can't have ApplicationCommand Annotation on Class and Method");
        }

        if (!ReflectionUtil.isAnnotationPresent(method, SlashCommand.class))
            throw new RuntimeException(method.getName() + ": Missing ApplicationCommand Annotation on either Class or Method");

        if ((method.isAnnotationPresent(SubCommand.class) && 
            !ReflectionUtil.isAnnotationPresent(method, SlashCommand.class))) {
            throw new RuntimeException(method.getName() + ": Missing ApplicationCommand Annotation on either Method or Class");
        }
    }
    
}
