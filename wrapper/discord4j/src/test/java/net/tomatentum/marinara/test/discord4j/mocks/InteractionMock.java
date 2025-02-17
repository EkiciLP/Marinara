package net.tomatentum.marinara.test.discord4j.mocks;

import java.util.Optional;

import discord4j.core.object.command.Interaction;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.MessageChannel;
import reactor.core.publisher.Mono;

public class InteractionMock extends Interaction
 {

    public InteractionMock() {
        super(null, null);
    }

    @Override
    public Optional<Message> getMessage() {
        //return Optional.of(new MessageMock());
        return Optional.empty();
    }

    @Override
    public Mono<MessageChannel> getChannel() {
        return Mono.just(new ChannelMock());
    }

    @Override
    public Mono<Guild> getGuild() {
        //return Mono.just(new ServerMock());
        return Mono.empty();
    }

    @Override
    public User getUser() {
        return new UserMock();
    }
    
}
