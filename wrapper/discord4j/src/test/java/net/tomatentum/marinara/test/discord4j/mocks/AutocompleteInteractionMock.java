package net.tomatentum.marinara.test.discord4j.mocks;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputAutoCompleteEvent;
import discord4j.core.object.command.Interaction;
import discord4j.gateway.ShardInfo;

public class AutocompleteInteractionMock extends ChatInputAutoCompleteEvent {

    public AutocompleteInteractionMock(GatewayDiscordClient gateway, ShardInfo shardInfo, Interaction interaction) {
        super(gateway, shardInfo, interaction);
        //TODO Auto-generated constructor stub
    }

    public static boolean didAutocompleteRun = false;
    
}
