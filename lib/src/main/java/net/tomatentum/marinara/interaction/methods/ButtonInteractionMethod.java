package net.tomatentum.marinara.interaction.methods;

import java.lang.reflect.Method;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.parser.AnnotationParser;
import net.tomatentum.marinara.parser.ButtonParser;

public class ButtonInteractionMethod extends InteractionMethod {

    private String customId;

    ButtonInteractionMethod(Method method, InteractionHandler handler, Marinara marinara) {
        super(method, handler, marinara);
    }

    @Override
    public AnnotationParser[] getParsers() {
        return new AnnotationParser[] {
            new ButtonParser(method, (x) -> { this.customId = x; } )
        };
    }

    @Override
    public Object getParameter(Object context, int index) {
        Class<?> type = getMethod().getParameterTypes()[index+1];
        return marinara.getWrapper().getContextObjectProvider().getComponentContextObject(context, type);
    }

    @Override
    public boolean canRun(Object context) {
        return marinara.getWrapper().getButtonId(context).equals(customId);
    }

    @Override
    public InteractionType getType() {
        return InteractionType.BUTTON;
    }
    
}
