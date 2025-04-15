package net.tomatentum.marinara.interaction.methods;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import net.tomatentum.cutin.MethodParser;
import net.tomatentum.cutin.ReflectedMethodFactory.ParserResults;
import net.tomatentum.cutin.container.MethodContainer;
import net.tomatentum.cutin.method.ReflectedMethod;
import net.tomatentum.marinara.checks.AppliedCheck;
import net.tomatentum.marinara.checks.CheckExecutionContext;
import net.tomatentum.marinara.checks.CheckMethodIdentifier;
import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.parser.AutocompleteParser;
import net.tomatentum.marinara.parser.InteractionCheckParser;
import net.tomatentum.marinara.wrapper.ContextObjectProvider;

public class AutoCompleteInteractionMethod extends InteractionMethod {

    private String autocompleteRef;
    private ContextObjectProvider cop;

    private AutoCompleteInteractionMethod(
            Method method, 
            InteractionHandler handler,
            List<AppliedCheck> appliedChecks,
            String autocompleteRef,
            ContextObjectProvider cop
        ) {
        super(method, handler, appliedChecks);
        this.autocompleteRef = autocompleteRef;
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
        public Optional<ReflectedMethod<InteractionIdentifier, Object>> produce(Method method, Object containingObject, ParserResults parserResults) {
            if (!(containingObject instanceof InteractionHandler)) return Optional.empty();
            String[] autocompletes = parserResults.get(AutocompleteParser.class);
            if (autocompletes.length <= 0) return Optional.empty();
            
            return Optional.of(new AutoCompleteInteractionMethod(
                method, 
                (InteractionHandler) containingObject,
                parserResults.get(InteractionCheckParser.class),
                autocompletes[0],
                cop
            ));

        }

        @Override
        public void addParser(Set<MethodParser> parser) {
            super.addParser(parser);

            parser.add(
                new AutocompleteParser()
            );
        }

    }
    
}
