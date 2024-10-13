package net.tomatentum.marinara.registry;

import java.lang.reflect.Method;
import java.util.List;

import net.tomatentum.marinara.handler.InteractionHandler;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.methods.InteractionMethod;
import net.tomatentum.marinara.wrapper.LibraryWrapper;

public class InteractionRegistry {
    private List<InteractionMethod> interactionMethods;
    private LibraryWrapper wrapper;

    public InteractionRegistry(LibraryWrapper wrapper) {
        this.wrapper = wrapper;
        wrapper.subscribeInteractions(this::handle);
    }

    public void addInteractions(InteractionHandler interactionHandler) {
        for (Method method : interactionHandler.getClass().getMethods()) {
            interactionMethods.add(InteractionMethod.create(method, interactionHandler, wrapper));
        }
    }

    public void handle(Object context) {
        interactionMethods.forEach((m) -> {
            InteractionType type = wrapper.getInteractionType(context.getClass());
            if (m.getType().equals(type))
                m.run(context);
        });
    }
}
