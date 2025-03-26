package net.tomatentum.marinara.interaction.methods;

import java.lang.reflect.Method;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.parser.AnnotationParser;
import net.tomatentum.marinara.parser.SlashCommandParser;

public class AutoCompleteInteractionMethod extends InteractionMethod {

    private InteractionIdentifier interactionIdentifier;

    public AutoCompleteInteractionMethod(Method method, 
        InteractionHandler handler, 
        Marinara marinara
        ) {
        super(method, handler, marinara);
    }

    @Override
    public AnnotationParser[] provideParsers() {
        return new AnnotationParser[] { 
            new SlashCommandParser(method(), true, (x) -> { this.interactionIdentifier = x; } ) 
        };
    }

    @Override
    public Object getParameter(Object context, int index) {
        Class<?> type = method().getParameterTypes()[index+1];
        Object autocompleteOptionValue = marinara.getWrapper().getContextObjectProvider().getAutocompleteFocusedOption(context);
        if (autocompleteOptionValue != null)
            return autocompleteOptionValue;

        return marinara.getWrapper().getContextObjectProvider().getInteractionContextObject(context, type);
    }

    @Override
    public InteractionIdentifier identifier() {
        return interactionIdentifier;
    }
    
}
