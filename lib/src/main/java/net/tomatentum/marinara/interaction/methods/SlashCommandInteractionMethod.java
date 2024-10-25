package net.tomatentum.marinara.interaction.methods;

import java.lang.reflect.Method;

import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.commands.ExecutableSlashCommandDefinition;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommandGroup;
import net.tomatentum.marinara.util.ReflectionUtil;
import net.tomatentum.marinara.wrapper.LibraryWrapper;

public class SlashCommandInteractionMethod extends InteractionMethod {

    private ExecutableSlashCommandDefinition commandDefinition;

    SlashCommandInteractionMethod(Method method, InteractionHandler handler, LibraryWrapper wrapper) {
        super(method, handler, wrapper);
        parseMethod();
    }

    @Override
    public Object getParameter(Object context, int index) {
        return wrapper.convertCommandOption(context, commandDefinition.options()[index].type(), commandDefinition.options()[index].name());
    }

    @Override
    public boolean canRun(Object context) {
        ExecutableSlashCommandDefinition other = wrapper.getCommandDefinition(context);
        return commandDefinition.equals(other);
    }

    @Override
    public InteractionType getType() {
        return InteractionType.COMMAND;
    }

    public ExecutableSlashCommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    private void parseMethod() {
        ReflectionUtil.checkValidCommandMethod(method);

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

        this.commandDefinition = builder.build();
    }

}
