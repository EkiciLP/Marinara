package net.tomatentum.marinara.parser;

import java.lang.reflect.Method;
import java.util.function.Consumer;

import org.slf4j.Logger;

import net.tomatentum.cutin.MethodParser;
import net.tomatentum.cutin.util.ReflectionUtil;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommandGroup;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.interaction.ident.SlashCommandIdentifier;
import net.tomatentum.marinara.util.LoggerUtil;

public class SlashCommandParser implements MethodParser {

    private Method method;
    private Consumer<SlashCommandIdentifier> consumer;

    private Logger logger = LoggerUtil.getLogger(getClass());

    public SlashCommandParser(Method method, Consumer<SlashCommandIdentifier> consumer) {
        this.method = method;
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
            .build();

        if (ReflectionUtil.isAnnotationPresent(method, SubCommandGroup.class)) {
            SubCommandGroup cmdGroup = ReflectionUtil.getAnnotation(method, SubCommandGroup.class);
            lastIdentifier = InteractionIdentifier.builder()
                .name(cmdGroup.name())
                .description(cmdGroup.description())
                .type(InteractionType.COMMAND)
                .parent(lastIdentifier)
                .build();
        }

        if (ReflectionUtil.isAnnotationPresent(method, SubCommand.class)) {
            SubCommand subCmd = ReflectionUtil.getAnnotation(method, SubCommand.class);
            lastIdentifier = InteractionIdentifier.slashBuilder()
                .name(subCmd.name())
                .description(subCmd.description())
                .options(subCmd.options())
                .build();
        }

        logger.trace("Parsed using SlashCommandParser for method {} with the result: {}", ReflectionUtil.getFullMethodName(method), lastIdentifier);
        consumer.accept((SlashCommandIdentifier) lastIdentifier);
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
