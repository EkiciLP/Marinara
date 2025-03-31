package net.tomatentum.marinara.interaction.methods;

import java.lang.reflect.Method;
import java.util.List;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.annotation.AutoComplete;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommand;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.parser.AnnotationParser;
import net.tomatentum.marinara.parser.AutocompleteParser;
import net.tomatentum.marinara.reflection.ReflectedMethod;

public class AutoCompleteInteractionMethod extends InteractionMethod {

    private String autocompleteRef;

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
        return InteractionIdentifier.builder()
            .type(InteractionType.AUTOCOMPLETE)
            .name(autocompleteRef)
            .description("AUTOCOMPLETE")
            .build();
    }

    public static class Factory extends InteractionMethod.Factory {

        @Override
        public ReflectedMethod produce(Marinara marinara, Method method, Object containingObject) {
            if (!(containingObject instanceof InteractionHandler) ||
                !method.isAnnotationPresent(AutoComplete.class) ||
                (method.isAnnotationPresent(SlashCommand.class) ||
                method.isAnnotationPresent(SubCommand.class)))
                return null;

            return new AutoCompleteInteractionMethod(method, (InteractionHandler) containingObject, marinara);
        }

        @Override
        public void addParser(ReflectedMethod method, List<AnnotationParser> parser) {
            super.addParser(method, parser);

            AutoCompleteInteractionMethod imethod = (AutoCompleteInteractionMethod) method;
            parser.add(
                new AutocompleteParser(method.method(), x -> imethod.autocompleteRef = x[0])
            );
        }

    }
    
}
