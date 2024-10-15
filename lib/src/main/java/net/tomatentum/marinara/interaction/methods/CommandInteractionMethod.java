package net.tomatentum.marinara.interaction.methods;

import java.lang.reflect.Method;

import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.commands.ExecutableCommandDefinition;
import net.tomatentum.marinara.interaction.commands.annotation.ApplicationCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommandGroup;
import net.tomatentum.marinara.util.ReflectionUtil;
import net.tomatentum.marinara.handler.InteractionHandler;
import net.tomatentum.marinara.wrapper.LibraryWrapper;

public class CommandInteractionMethod extends InteractionMethod {

    private ExecutableCommandDefinition commandDefinition;

    CommandInteractionMethod(Method method, InteractionHandler handler, LibraryWrapper wrapper) {
        super(method, handler, wrapper);
        parseMethod();
    }

    @Override
    public Object getParameter(Object context, int index) {
        return wrapper.convertCommandOption(context, commandDefinition.options()[index].type(), commandDefinition.options()[index].name());
    }

    @Override
    public boolean canRun(Object context) {
        ExecutableCommandDefinition other = wrapper.getCommandDefinition(context);
        return commandDefinition.equals(other);
    }

    @Override
    public InteractionType getType() {
        return InteractionType.COMMAND;
    }

    public ExecutableCommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    private void parseMethod() {
        ReflectionUtil.checkValidCommandMethod(method);

        ApplicationCommand cmd = ReflectionUtil.getAnnotation(method, ApplicationCommand.class);
        ExecutableCommandDefinition.Builder builder = new ExecutableCommandDefinition.Builder();
        builder.setApplicationCommand(cmd);

        if (ReflectionUtil.isAnnotationPresent(method, SubCommandGroup.class)) {
            SubCommandGroup cmdGroup = ReflectionUtil.getAnnotation(method, SubCommandGroup.class);
            builder.setSubCommandGroupNames(cmdGroup.name().split(" "));
        }

        if (ReflectionUtil.isAnnotationPresent(method, SubCommand.class)) {
            SubCommand subCmd = ReflectionUtil.getAnnotation(method, SubCommand.class);
            builder.setSubCommand(subCmd);
        }

        this.commandDefinition = builder.build();
    }

}
