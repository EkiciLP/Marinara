package net.tomatentum.marinara.test.discord4j.mocks;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import discord4j.core.event.domain.interaction.ButtonInteractionEvent;
import discord4j.core.object.command.Interaction;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.MessageChannel;
import reactor.core.publisher.Mono;

public class CommonMocks {
    public static Interaction getInteractionMock() {
        Interaction interaction = mock(Interaction.class);
        Message message = mock(Message.class);
        MessageChannel channel = mock(MessageChannel.class);
        Guild guild = mock(Guild.class);
        User user = mock(User.class);
        Member member = mock(Member.class);


        when(interaction.getMessage()).thenReturn(Optional.of(message));
        when(interaction.getChannel()).thenReturn(Mono.just(channel));
        when(interaction.getGuild()).thenReturn(Mono.just(guild));
        when(interaction.getUser()).thenReturn(user);
        when(interaction.getMember()).thenReturn(Optional.of(member));


        return interaction;
    }

    public static ButtonInteractionEvent getButtonEventMock(String customId) {
        ButtonInteractionEvent buttonEventMock = mock(ButtonInteractionEvent.class);

        when(buttonEventMock.getCustomId()).thenReturn(customId);
        Interaction interactionMock = getInteractionMock();
        when(buttonEventMock.getInteraction()).thenReturn(interactionMock);
        Optional<Message> message = interactionMock.getMessage();
        when (buttonEventMock.getMessage()).thenReturn(message);
        return buttonEventMock;
    }

    public static ButtonInteractionEvent getButtonEventMock(String customId, Interaction interaction) {
        ButtonInteractionEvent buttonEventMock = mock(ButtonInteractionEvent.class);

        when(buttonEventMock.getCustomId()).thenReturn(customId);
        when(buttonEventMock.getInteraction()).thenReturn(interaction);
        return buttonEventMock;
    }
}
