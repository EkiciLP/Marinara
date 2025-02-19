package net.tomatentum.marinara.test.javacord.mocks;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.ButtonInteraction;

public class CommonMocks {

    public static ButtonInteraction getButtonInteractionMock(String customId) {
        ButtonInteraction buttonInteractionMock = mock();
        
        when(buttonInteractionMock.getCustomId()).thenReturn(customId);
        when(buttonInteractionMock.getMessage()).thenReturn(mock(Message.class));
        when(buttonInteractionMock.getServer()).thenReturn(Optional.of(mock(Server.class)));
        when(buttonInteractionMock.getChannel()).thenReturn(Optional.of(mock(TextChannel.class)));
        when(buttonInteractionMock.getUser()).thenReturn(mock(User.class));


        return buttonInteractionMock;
    }

    public static ButtonInteraction getButtonInteractionMock(String customId, Server serverMock) {
        ButtonInteraction buttonInteractionMock = mock();
        
        when(buttonInteractionMock.getCustomId()).thenReturn(customId);
        when(buttonInteractionMock.getMessage()).thenReturn(mock(Message.class));
        when(buttonInteractionMock.getServer()).thenReturn(Optional.of(serverMock));
        when(buttonInteractionMock.getChannel()).thenReturn(Optional.of(mock(TextChannel.class)));
        when(buttonInteractionMock.getUser()).thenReturn(mock(User.class));


        return buttonInteractionMock;
    }
    
}
