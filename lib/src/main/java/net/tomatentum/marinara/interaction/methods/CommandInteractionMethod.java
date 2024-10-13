package net.tomatentum.marinara.interaction.methods;

import java.lang.reflect.Method;

import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.commands.CommandDefinition;
import net.tomatentum.marinara.interaction.commands.annotation.ApplicationCommand;
import net.tomatentum.marinara.interaction.commands.annotation.CommandOption;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommandGroup;
import net.tomatentum.marinara.util.ReflectionUtil;
import net.tomatentum.marinara.handler.InteractionHandler;
import net.tomatentum.marinara.wrapper.LibraryWrapper;

public class CommandInteractionMethod extends InteractionMethod {

    private CommandOption[] options;

    private CommandDefinition commandDefinition;

    CommandInteractionMethod(Method method, InteractionHandler handler, LibraryWrapper wrapper) {
        super(method, handler, wrapper);
        parseMethod();
    }

    @Override
    public Object getParameter(Object context, int index) {
        return wrapper.convertCommandOption(context, options[index].type(), options[index].name());
    }

    @Override
    public boolean canRun(Object context) {
        CommandDefinition other = wrapper.getCommandDefinition(context);
        return commandDefinition.equals(other);
    }

    @Override
    public InteractionType getType() {
        return InteractionType.COMMAND;
    }

    public CommandOption[] getOptions() {
        return options;
    }

    public CommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    private void parseMethod() {
        ReflectionUtil.checkValidCommandMethod(method);
        parseOptions();

        ApplicationCommand cmd = ReflectionUtil.getAnnotation(method, ApplicationCommand.class);
        CommandDefinition.Builder builder = new CommandDefinition.Builder();
        builder.setApplicationCommandName(cmd.name());
        builder.setApplicationCommandDescription(cmd.description());

        if (ReflectionUtil.isAnnotationPresent(method, SubCommandGroup.class)) {
            SubCommandGroup cmdGroup = ReflectionUtil.getAnnotation(method, SubCommandGroup.class);
            builder.setSubCommandGroupNames(cmdGroup.name().split(" "));
        }

        if (ReflectionUtil.isAnnotationPresent(method, SubCommand.class)) {
            SubCommand subCmd = ReflectionUtil.getAnnotation(method, SubCommand.class);
            builder.setSubCommandName(subCmd.name());
            builder.setSubCommandDescription(subCmd.description());
        }

        this.commandDefinition = builder.build();
    }

    private CommandOption[] parseOptions() {
        if (method.isAnnotationPresent(SubCommand.class)) {
            SubCommand subCmd = method.getAnnotation(SubCommand.class);
            return subCmd.options();
        }else {
            ApplicationCommand subCmd = method.getAnnotation(ApplicationCommand.class);
            return subCmd.options();
        }
    }

}
