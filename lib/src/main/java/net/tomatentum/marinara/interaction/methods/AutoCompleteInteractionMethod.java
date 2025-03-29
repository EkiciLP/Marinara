package net.tomatentum.marinara.interaction.methods;

import java.lang.reflect.Method;
import java.util.List;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.annotation.AutoComplete;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.parser.AnnotationParser;
import net.tomatentum.marinara.parser.SlashCommandParser;
import net.tomatentum.marinara.reflection.ReflectedMethod;

public class AutoCompleteInteractionMethod extends InteractionMethod {

    private InteractionIdentifier interactionIdentifier;

    private AutoCompleteInteractionMethod(Method method, 
        InteractionHandler handler, 
        Marinara marinara
        ) {
        super(method, handler, marinara);
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

    public static class Factory extends InteractionMethod.Factory {

        @Override
        public ReflectedMethod produce(Marinara marinara, Method method, Object containingObject) {
            if (!method.isAnnotationPresent(AutoComplete.class) ||
                !(containingObject instanceof InteractionHandler)
                )
                return null;

            return new AutoCompleteInteractionMethod(method, (InteractionHandler) containingObject, marinara);
        }

        @Override
        public void addParser(ReflectedMethod method, List<AnnotationParser> parser) {
            super.addParser(method, parser);

            AutoCompleteInteractionMethod imethod = (AutoCompleteInteractionMethod) method;
            parser.add(
                new SlashCommandParser(method.method(), true, x -> imethod.interactionIdentifier = x)
            );
        }

    }
    
}
