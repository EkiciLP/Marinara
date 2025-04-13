package net.tomatentum.marinara.test.discord4j;

import static org.junit.jupiter.api.Assertions.assertEquals;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.commands.annotation.CommandChoices;
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
                type = SlashCommandOptionType.STRING,
                choices = @CommandChoices(cenum = TestChoiceEnum.class)
            )
        }
        )
    public void exec(ChatInputInteractionEvent event, String test) {
        assertEquals("test", test);
        System.out.println("Success!");
    }
}
