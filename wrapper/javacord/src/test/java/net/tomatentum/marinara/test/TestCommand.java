package net.tomatentum.marinara.test;

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
                name = "pommes",
                description = "mit Fett",
                type = SlashCommandOptionType.MENTIONABLE
            )
        }
        )
    public void exec(SlashCommandInteraction interaction) {
        System.out.println("Success!");
    }
}
