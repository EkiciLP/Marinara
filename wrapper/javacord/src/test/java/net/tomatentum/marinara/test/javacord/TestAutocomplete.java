package net.tomatentum.marinara.test.javacord;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.AutocompleteInteraction;
import org.javacord.api.interaction.SlashCommandInteraction;

import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.annotation.AutoComplete;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommandOption;
import net.tomatentum.marinara.interaction.commands.option.SlashCommandOptionType;

public class TestAutocomplete implements InteractionHandler {
    
    @SlashCommand(
        name = "test",
        options = @SlashCommandOption(
                name = "foo",
                type = SlashCommandOptionType.STRING,
                autocompletes = @AutoComplete("testAuto")
            )
        )
    @AutoComplete("testAuto")
    public void exec(SlashCommandInteraction context) {

    }

    @AutoComplete("testAuto")
    public void autocomplete(AutocompleteInteraction context, String value) {
        System.out.println("Success!");
        assertEquals(value, "test");
        context.respondWithChoices(Collections.emptyList());
    }

}
