package net.tomatentum.marinara.interaction.components.methods;

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
import net.tomatentum.marinara.interaction.annotation.Button;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.interaction.methods.InteractionMethod;
import net.tomatentum.marinara.parser.ButtonParser;
import net.tomatentum.marinara.wrapper.ContextObjectProvider;

public class ButtonInteractionMethod extends InteractionMethod {

    private String customId;
    private ContextObjectProvider cop;

    private ButtonInteractionMethod(Method method, InteractionHandler handler, ContextObjectProvider cop) {
        super(method, handler);
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
        public Optional<ReflectedMethod<InteractionIdentifier, Object>> produce(Method method, Object containingObject) {
            ButtonInteractionMethod rMethod = null;
            if (method.isAnnotationPresent(Button.class) &&
                (containingObject instanceof InteractionHandler iHandler)
                )
                rMethod = new ButtonInteractionMethod(method, iHandler, this.cop);

            return Optional.ofNullable(rMethod);
        }

        @Override
        public void addParser(ReflectedMethod<InteractionIdentifier, Object> method, List<MethodParser> parser) {
            super.addParser(method, parser);

            parser.add(
                new ButtonParser(method.method(), x -> ((ButtonInteractionMethod) method).customId = x)
            );
        }

    }

}
