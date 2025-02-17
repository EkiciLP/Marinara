package net.tomatentum.marinara.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import org.javacord.api.interaction.AutocompleteInteraction;

import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.annotation.AutoComplete;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommand;

public class TestAutocomplete implements InteractionHandler {
    
    @SlashCommand(name = "test")
    @AutoComplete
    public void autocomplete(AutocompleteInteraction context, String value) {
        System.out.println("Success!");
        assertEquals(value, "test");
        context.respondWithChoices(Collections.emptyList());
    }

}
