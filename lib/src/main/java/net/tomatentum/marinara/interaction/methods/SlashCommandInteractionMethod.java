package net.tomatentum.marinara.interaction.methods;

import java.lang.reflect.Method;

import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.commands.ExecutableSlashCommandDefinition;
import net.tomatentum.marinara.parser.AnnotationParser;
import net.tomatentum.marinara.parser.SlashCommandParser;
import net.tomatentum.marinara.wrapper.LibraryWrapper;

public class SlashCommandInteractionMethod extends InteractionMethod {

    private ExecutableSlashCommandDefinition commandDefinition;

    SlashCommandInteractionMethod(Method method, InteractionHandler handler, LibraryWrapper wrapper) {
        super(method, handler, wrapper);
    }

    @Override
    public AnnotationParser[] getParsers() {
        return new AnnotationParser[] { 
            new SlashCommandParser(method, (x) -> { this.commandDefinition = x; } ) 
        };
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

    public void setCommandDefinition(ExecutableSlashCommandDefinition commandDefinition) {
        this.commandDefinition = commandDefinition;
    }

}
