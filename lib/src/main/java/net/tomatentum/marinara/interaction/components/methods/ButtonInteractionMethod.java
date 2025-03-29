package net.tomatentum.marinara.interaction.components.methods;

import java.lang.reflect.Method;
import java.util.List;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.annotation.Button;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.interaction.methods.InteractionMethod;
import net.tomatentum.marinara.parser.AnnotationParser;
import net.tomatentum.marinara.parser.ButtonParser;
import net.tomatentum.marinara.reflection.ReflectedMethod;

public class ButtonInteractionMethod extends InteractionMethod {

    private String customId;

    private ButtonInteractionMethod(Method method, InteractionHandler handler, Marinara marinara) {
        super(method, handler, marinara);
    }

    @Override
    public Object getParameter(Object context, int index) {
        Class<?> type = method().getParameterTypes()[index+1];
        return marinara.getWrapper().getContextObjectProvider().getComponentContextObject(context, type);
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

        @Override
        public ReflectedMethod produce(Marinara marinara, Method method, Object containingObject) {
            if (!method.isAnnotationPresent(Button.class) ||
                !(containingObject instanceof InteractionHandler)
                )
                return null;

            return new ButtonInteractionMethod(method, (InteractionHandler) containingObject, marinara);
        }

        @Override
        public void addParser(ReflectedMethod method, List<AnnotationParser> parser) {
            super.addParser(method, parser);

            ButtonInteractionMethod imethod = (ButtonInteractionMethod) method;
            parser.add(
                new ButtonParser(method.method(), x -> imethod.customId = x)
            );
        }

    }

}
