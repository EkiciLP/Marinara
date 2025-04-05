package net.tomatentum.marinara.interaction.methods;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

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
        Object contextObject = marinara.getWrapper().getContextObjectProvider().getInteractionContextObject(context, type);
        if (contextObject != null)
            return contextObject;

        Object autocompleteOptionValue = marinara.getWrapper().getContextObjectProvider().getAutocompleteFocusedOption(context).input();
        if (type.isInstance(autocompleteOptionValue))
            return autocompleteOptionValue;

        return null;
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
        public Optional<ReflectedMethod> produce(Marinara marinara, Method method, Object containingObject) {
            ReflectedMethod rMethod = null;
            if ((containingObject instanceof InteractionHandler iHandler) &&
                method.isAnnotationPresent(AutoComplete.class) &&
                !(method.isAnnotationPresent(SlashCommand.class) ||
                method.isAnnotationPresent(SubCommand.class)))
                rMethod = new AutoCompleteInteractionMethod(method, iHandler, marinara);

            return Optional.ofNullable(rMethod);
        }

        @Override
        public void addParser(ReflectedMethod method, List<AnnotationParser> parser) {
            super.addParser(method, parser);

            parser.add(
                new AutocompleteParser(method.method(), x -> ((AutoCompleteInteractionMethod) method).autocompleteRef = x[0])
            );
        }

    }
    
}
