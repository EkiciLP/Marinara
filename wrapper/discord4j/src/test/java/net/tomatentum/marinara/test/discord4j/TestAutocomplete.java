package net.tomatentum.marinara.test.discord4j;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import discord4j.core.event.domain.interaction.ChatInputAutoCompleteEvent;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.annotation.AutoComplete;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommand;

public class TestAutocomplete implements InteractionHandler {

    @SlashCommand(name = "test")
    @AutoComplete("testAuto")
    public void exec(ChatInputInteractionEvent context) {

    }
    
    @AutoComplete("testAuto")
    public void autocomplete(ChatInputAutoCompleteEvent context, String value) {
        System.out.println("Success!");
        assertEquals(value, "test");
        context.respondWithSuggestions(Collections.emptyList());
    }

}
