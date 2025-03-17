package net.tomatentum.marinara.parser;

import java.lang.reflect.Method;
import java.util.function.Consumer;

import org.apache.logging.log4j.Logger;

import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommandGroup;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.interaction.ident.SlashCommandIdentifier;
import net.tomatentum.marinara.util.LoggerUtil;
import net.tomatentum.marinara.util.ReflectionUtil;

public class SlashCommandParser implements AnnotationParser {

    private Method method;
    private boolean isAutoComplete;
    private Consumer<SlashCommandIdentifier> consumer;

    private Logger logger = LoggerUtil.getLogger(getClass());

    public SlashCommandParser(Method method, boolean isAutoComplete, Consumer<SlashCommandIdentifier> consumer) {
        this.method = method;
        this.isAutoComplete = isAutoComplete;
        this.consumer = consumer;
    }

    @Override
    public void parse() {
        this.checkValidCommandMethod(method);

        SlashCommand cmd = ReflectionUtil.getAnnotation(method, SlashCommand.class);
        InteractionIdentifier lastIdentifier = InteractionIdentifier.rootBuilder()
            .name(cmd.name())
            .description(cmd.description())
            .options(cmd.options())
            .serverIds(cmd.serverIds())
            .build(isAutoComplete);

        if (ReflectionUtil.isAnnotationPresent(method, SubCommandGroup.class)) {
            SubCommandGroup cmdGroup = ReflectionUtil.getAnnotation(method, SubCommandGroup.class);
            lastIdentifier = InteractionIdentifier.builder()
                .name(cmdGroup.name())
                .description(cmdGroup.description())
                .type(isAutoComplete ? InteractionType.AUTOCOMPLETE : InteractionType.COMMAND)
                .parent(lastIdentifier)
                .build();
        }

        if (ReflectionUtil.isAnnotationPresent(method, SubCommand.class)) {
            SubCommand subCmd = ReflectionUtil.getAnnotation(method, SubCommand.class);
            lastIdentifier = InteractionIdentifier.slashBuilder()
                .name(subCmd.name())
                .description(subCmd.description())
                .options(subCmd.options())
                .build(isAutoComplete);
        }

        logger.trace("Parsed using SlashCommandParser for method {} with the result:\n{}", ReflectionUtil.getFullMethodName(method), lastIdentifier.toString());
        consumer.accept((SlashCommandIdentifier) lastIdentifier);
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
