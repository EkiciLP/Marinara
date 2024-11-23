package net.tomatentum.marinara.interaction.methods;

import java.lang.reflect.Method;

import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.parser.AnnotationParser;
import net.tomatentum.marinara.parser.ButtonParser;
import net.tomatentum.marinara.wrapper.LibraryWrapper;

public class ButtonInteractionMethod extends InteractionMethod {

    private String customId;

    ButtonInteractionMethod(Method method, InteractionHandler handler, LibraryWrapper wrapper) {
        super(method, handler, wrapper);
    }

    @Override
    public AnnotationParser[] getParsers() {
        return new AnnotationParser[] {
            new ButtonParser(method, (x) -> { this.customId = x; } )
        };
    }

    @Override
    public Object getParameter(Object parameter, int index) {
        Class<?> type = getMethod().getParameterTypes()[index+1];
        return wrapper.getComponentContextObject(parameter, type);
    }

    @Override
    public boolean canRun(Object context) {
        return wrapper.getButtonId(context).equals(customId);
    }

    @Override
    public InteractionType getType() {
        return InteractionType.BUTTON;
    }
}
