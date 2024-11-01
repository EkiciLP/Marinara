package net.tomatentum.marinara.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.javacord.api.interaction.SlashCommandInteraction;

import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommandOption;
import net.tomatentum.marinara.interaction.commands.option.SlashCommandOptionType;

public class TestCommand implements InteractionHandler {
    @SlashCommand(
        name = "test", 
        description = "testingen",
        serverIds = {
            1037753048602255440L
        },
        options = {
            @SlashCommandOption(
                name = "foo",
                description = "foo bar is very fooby",
                type = SlashCommandOptionType.STRING
            )
        }
        )
    public void exec(SlashCommandInteraction interaction, String test) {
        assertEquals(test, "test");
        System.out.println("Success!");
    }
}
