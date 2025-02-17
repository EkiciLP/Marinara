package net.tomatentum.marinara.test.discord4j.mocks;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;

public class SlashCommandInteractionMock extends ChatInputInteractionEvent {

    public SlashCommandInteractionMock() {
        super(null, null, null);
    }

    @Override
    public String getCommandName() {
        return "test";
    }
    
}
