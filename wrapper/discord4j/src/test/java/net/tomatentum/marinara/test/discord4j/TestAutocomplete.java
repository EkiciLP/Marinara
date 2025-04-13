package net.tomatentum.marinara.test.discord4j;

import static org.junit.jupiter.api.Assertions.assertEquals;

import discord4j.core.event.domain.interaction.ChatInputAutoCompleteEvent;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.discordjson.json.ApplicationCommandOptionChoiceData;
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
    public void exec(ChatInputInteractionEvent context) {
        // Not executed just there for autocomplete to work
    }
    
    @AutoComplete("testAuto")
    public ApplicationCommandOptionChoiceData[] autocomplete(ChatInputAutoCompleteEvent context, String value) {
        System.out.println("Success!");
        assertEquals("test", value);
        return new ApplicationCommandOptionChoiceData[]{
            ApplicationCommandOptionChoiceData.builder().name("TestValue").value("test").build()
        };
    }

}
