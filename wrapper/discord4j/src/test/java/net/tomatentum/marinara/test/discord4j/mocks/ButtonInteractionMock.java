package net.tomatentum.marinara.test.discord4j.mocks;

import discord4j.core.event.domain.interaction.ButtonInteractionEvent;
import discord4j.core.object.command.Interaction;

public class ButtonInteractionMock extends ButtonInteractionEvent {

    private String customId;
    
    public ButtonInteractionMock(String customId) {
        super(null, null, null);
        this.customId = customId;
    }

    @Override
    public String getCustomId() {
        return customId;
    }

    @Override
    public Interaction getInteraction() {
        return new InteractionMock();
    }
     
}
