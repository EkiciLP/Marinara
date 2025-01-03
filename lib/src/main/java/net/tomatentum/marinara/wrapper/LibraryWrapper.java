package net.tomatentum.marinara.wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import net.tomatentum.marinara.interaction.commands.SlashCommandDefinition;
import net.tomatentum.marinara.interaction.commands.ExecutableSlashCommandDefinition;
import net.tomatentum.marinara.interaction.commands.option.SlashCommandOptionType;
import net.tomatentum.marinara.interaction.InteractionType;

public abstract class LibraryWrapper {

    private List<Consumer<Object>> interactionSubscriber;

    protected LibraryWrapper() {
        interactionSubscriber = new ArrayList<>();
    }


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

    public abstract void registerSlashCommands(SlashCommandDefinition[] defs); 
    public abstract Object convertCommandOption(Object context, SlashCommandOptionType type, String optionName);
    public abstract ExecutableSlashCommandDefinition getCommandDefinition(Object context);

    public abstract String getButtonId(Object context);
    public abstract Object getComponentContextObject(Object context, Class<?> type);
}