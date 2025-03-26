package net.tomatentum.marinara.interaction.components.methods;

import java.lang.reflect.Method;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.interaction.methods.InteractionMethod;
import net.tomatentum.marinara.parser.AnnotationParser;
import net.tomatentum.marinara.parser.ButtonParser;

public class ButtonInteractionMethod extends InteractionMethod {

    private String customId;

    public ButtonInteractionMethod(Method method, InteractionHandler handler, Marinara marinara) {
        super(method, handler, marinara);
    }

    @Override
    public AnnotationParser[] provideParsers() {
        return new AnnotationParser[] {
            new ButtonParser(method(), (x) -> { this.customId = x; } )
        };
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

}
