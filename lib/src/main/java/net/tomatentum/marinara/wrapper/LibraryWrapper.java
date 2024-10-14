package net.tomatentum.marinara.wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import net.tomatentum.marinara.interaction.commands.ExecutableCommandDefinition;
import net.tomatentum.marinara.interaction.commands.option.OptionType;
import net.tomatentum.marinara.interaction.InteractionType;

public abstract class LibraryWrapper {

    private List<Consumer<Object>> interactionSubscriber;

    protected LibraryWrapper() {
        interactionSubscriber = new ArrayList<>();
    }

    public abstract void registerGlobalCommand(); 
    public abstract void registerServerCommand();

    public void handleInteraction(Object context) {
        interactionSubscriber.forEach((o) -> o.accept(context));
    }

    public void subscribeInteractions(Consumer<Object> consumer) {
        interactionSubscriber.add(consumer);
    }
    public void unsubscribeInteractions(Consumer<Object> consumer) {
        interactionSubscriber.remove(consumer);
    }

    public abstract InteractionType getInteractionType(Class<?> clazz);
    public abstract Object convertCommandOption(Object context, OptionType type, String optionName);
    public abstract ExecutableCommandDefinition getCommandDefinition(Object context);
}