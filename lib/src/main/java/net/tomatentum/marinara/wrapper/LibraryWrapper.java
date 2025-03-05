package net.tomatentum.marinara.wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import net.tomatentum.marinara.interaction.commands.SlashCommandDefinition;

public abstract class LibraryWrapper {

    private List<Consumer<Object>> interactionSubscriber;

    protected LibraryWrapper() {
        this.interactionSubscriber = new ArrayList<>();
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

    public abstract void registerSlashCommands(SlashCommandDefinition[] defs); 
    
    public abstract IdentifierProvider createIdentifierProvider();
    public abstract ContextObjectProvider getContextObjectProvider();

}