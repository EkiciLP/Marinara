package net.tomatentum.marinara.parser;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.tomatentum.cutin.MethodParser;
import net.tomatentum.cutin.util.ReflectionUtil;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommandGroup;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;

public class SlashCommandParser implements MethodParser {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public SlashCommandParser() {
    }

    @Override
    public Object parse(Method method, Object containingObject) {
        if (!method.isAnnotationPresent(SlashCommand.class) && !method.isAnnotationPresent(SubCommand.class)) return null;
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
        return lastIdentifier;
    }

    private void checkValidCommandMethod(Method method) {
        if (method.isAnnotationPresent(SlashCommand.class) && 
            method.getDeclaringClass().isAnnotationPresent(SlashCommand.class)) {
            throw new RuntimeException(method.getName() + ": Can't have SlashCommand Annotation on Class and Method");
        }

        if (!ReflectionUtil.isAnnotationPresent(method, SlashCommand.class))
            throw new RuntimeException(method.getName() + ": Missing SlashCommand Annotation on either Class or Method");

        if ((method.isAnnotationPresent(SubCommand.class) && 
            !ReflectionUtil.isAnnotationPresent(method, SlashCommand.class))) {
            throw new RuntimeException(method.getName() + ": Missing SlashCommand Annotation on either Method or Class");
        }
    }
    
}
