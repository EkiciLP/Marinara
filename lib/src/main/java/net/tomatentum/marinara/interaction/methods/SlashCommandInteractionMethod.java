package net.tomatentum.marinara.interaction.methods;

import java.lang.reflect.Method;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.commands.ExecutableSlashCommandDefinition;
import net.tomatentum.marinara.parser.AnnotationParser;
import net.tomatentum.marinara.parser.SlashCommandParser;

public class SlashCommandInteractionMethod extends InteractionMethod {

    private ExecutableSlashCommandDefinition commandDefinition;

    SlashCommandInteractionMethod(Method method, InteractionHandler handler, Marinara marinara) {
        super(method, handler, marinara);
    }

    @Override
    public AnnotationParser[] getParsers() {
        return new AnnotationParser[] { 
            new SlashCommandParser(method, (x) -> { this.commandDefinition = x; } ) 
        };
    }

    @Override
    public Object getParameter(Object context, int index) {
        return marinara.getWrapper().getContextObjectProvider().convertCommandOption(context, commandDefinition.options()[index].name());
    }

    @Override
    public boolean canRun(Object context) {
        ExecutableSlashCommandDefinition other = marinara.getWrapper().getCommandDefinition(context);
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
