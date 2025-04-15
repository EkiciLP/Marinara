package net.tomatentum.marinara.interaction.components.methods;

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
import net.tomatentum.marinara.interaction.methods.InteractionMethod;
import net.tomatentum.marinara.parser.ButtonParser;
import net.tomatentum.marinara.parser.InteractionCheckParser;
import net.tomatentum.marinara.wrapper.ContextObjectProvider;

public class ButtonInteractionMethod extends InteractionMethod {

    private String customId;
    private ContextObjectProvider cop;

    private ButtonInteractionMethod(
            Method method, 
            InteractionHandler handler,
            List<AppliedCheck> appliedChecks,
            String customId, 
            ContextObjectProvider cop
        ) {
        super(method, handler, appliedChecks);
        this.customId = customId;
        this.cop = cop;
    }

    @Override
    public Object getParameter(Object context, int index) {
        Class<?> type = method().getParameterTypes()[index];
        Object superResult = super.getParameter(context, index);
        if (superResult == null)
            return this.cop.getComponentContextObject(context, type);
        else
            return superResult;
    }

    @Override
    public InteractionIdentifier identifier() {
        return InteractionIdentifier.builder()
            .name(customId)
            .description("Button")
            .type(InteractionType.BUTTON)
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
            String customId = parserResults.get(ButtonParser.class);
            if (customId == null) return Optional.empty();
            return Optional.of(new ButtonInteractionMethod(
                method, 
                (InteractionHandler) containingObject,
                parserResults.get(InteractionCheckParser.class),
                customId,
                this.cop
            ));
        }

        @Override
        public void addParser(Set<MethodParser> parser) {
            super.addParser(parser);

            parser.add(
                new ButtonParser()
            );
        }

    }

}
