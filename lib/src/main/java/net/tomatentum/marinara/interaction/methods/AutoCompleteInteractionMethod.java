package net.tomatentum.marinara.interaction.methods;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import net.tomatentum.cutin.MethodParser;
import net.tomatentum.cutin.container.MethodContainer;
import net.tomatentum.cutin.method.ReflectedMethod;
import net.tomatentum.marinara.checks.CheckExecutionContext;
import net.tomatentum.marinara.checks.CheckMethodIdentifier;
import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.annotation.AutoComplete;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommand;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.parser.AutocompleteParser;
import net.tomatentum.marinara.wrapper.ContextObjectProvider;

public class AutoCompleteInteractionMethod extends InteractionMethod {

    private String autocompleteRef;
    private ContextObjectProvider cop;

    private AutoCompleteInteractionMethod(Method method, 
        InteractionHandler handler, 
        ContextObjectProvider cop
        ) {
        super(method, handler);
        this.cop = cop;
    }

    @Override
    public Object getParameter(Object context, int index) {
        Class<?> type = method().getParameterTypes()[index];
        Object contextObject = this.cop.getInteractionContextObject(context, type);
        if (contextObject != null)
            return contextObject;

        Object autocompleteOptionValue = this.cop.getAutocompleteFocusedOption(context).input();
        if (type.isInstance(autocompleteOptionValue))
            return autocompleteOptionValue;

        return super.getParameter(context, index);
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

        private ContextObjectProvider cop;

        public Factory(MethodContainer<CheckMethodIdentifier, CheckExecutionContext> checkContainer, ContextObjectProvider cop) {
            super(checkContainer);
            this.cop = cop;
        }

        @Override
        public Optional<ReflectedMethod<InteractionIdentifier, Object>> produce(Method method, Object containingObject) {
            AutoCompleteInteractionMethod rMethod = null;
            if ((containingObject instanceof InteractionHandler iHandler) &&
                method.isAnnotationPresent(AutoComplete.class) &&
                !(method.isAnnotationPresent(SlashCommand.class) ||
                method.isAnnotationPresent(SubCommand.class)))
                rMethod = new AutoCompleteInteractionMethod(method, iHandler, cop);

            return Optional.ofNullable(rMethod);
        }

        @Override
        public void addParser(ReflectedMethod<InteractionIdentifier, Object> method, List<MethodParser> parser) {
            super.addParser(method, parser);

            parser.add(
                new AutocompleteParser(method.method(), x -> ((AutoCompleteInteractionMethod) method).autocompleteRef = x[0])
            );
        }

    }
    
}
