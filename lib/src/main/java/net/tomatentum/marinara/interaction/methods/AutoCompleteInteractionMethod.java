package net.tomatentum.marinara.interaction.methods;

import java.lang.reflect.Method;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.commands.ExecutableSlashCommandDefinition;
import net.tomatentum.marinara.parser.AnnotationParser;
import net.tomatentum.marinara.parser.SlashCommandParser;

public class AutoCompleteInteractionMethod extends InteractionMethod {

    private ExecutableSlashCommandDefinition commandDefinition;

    public AutoCompleteInteractionMethod(Method method, 
        InteractionHandler handler, 
        Marinara marinara
        ) {
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
        Class<?> type = getMethod().getParameterTypes()[index+1];
        Object autocompleteOptionValue = marinara.getWrapper().getContextObjectProvider().getAutocompleteFocusedOption(context);
        if (autocompleteOptionValue != null)
            return autocompleteOptionValue;

        return marinara.getWrapper().getContextObjectProvider().getComponentContextObject(context, type);
    }

    @Override
    public boolean canRun(Object context) {
        ExecutableSlashCommandDefinition other = marinara.getWrapper().getCommandDefinition(context);
        return commandDefinition.equals(other);
    }

    @Override
    public InteractionType getType() {
        return InteractionType.AUTOCOMPLETE;
    }
    
}
