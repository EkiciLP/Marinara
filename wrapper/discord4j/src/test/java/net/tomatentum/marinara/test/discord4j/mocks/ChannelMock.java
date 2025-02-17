package net.tomatentum.marinara.test.discord4j.mocks;

import java.time.Instant;
import java.util.Optional;
import java.util.function.Consumer;

import org.reactivestreams.Publisher;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.retriever.EntityRetrievalStrategy;
import discord4j.core.spec.MessageCreateSpec;
import discord4j.core.spec.legacy.LegacyMessageCreateSpec;
import discord4j.rest.entity.RestChannel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ChannelMock implements MessageChannel {

    @Override
    public Type getType() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getType'");
    }

    @Override
    public Mono<Void> delete(String reason) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public RestChannel getRestChannel() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRestChannel'");
    }

    @Override
    public Snowflake getId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getId'");
    }

    @Override
    public GatewayDiscordClient getClient() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getClient'");
    }

    @Override
    public Optional<Snowflake> getLastMessageId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLastMessageId'");
    }

    @Override
    public Mono<Message> getLastMessage() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLastMessage'");
    }

    @Override
    public Mono<Message> getLastMessage(EntityRetrievalStrategy retrievalStrategy) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLastMessage'");
    }

    @Override
    public Optional<Instant> getLastPinTimestamp() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLastPinTimestamp'");
    }

    @Override
    public Mono<Message> createMessage(Consumer<? super LegacyMessageCreateSpec> spec) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createMessage'");
    }

    @Override
    public Mono<Message> createMessage(MessageCreateSpec spec) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createMessage'");
    }

    @Override
    public Mono<Void> type() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'type'");
    }

    @Override
    public Flux<Long> typeUntil(Publisher<?> until) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'typeUntil'");
    }

    @Override
    public Flux<Message> getMessagesBefore(Snowflake messageId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMessagesBefore'");
    }

    @Override
    public Flux<Message> getMessagesAfter(Snowflake messageId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMessagesAfter'");
    }

    @Override
    public Mono<Message> getMessageById(Snowflake id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMessageById'");
    }

    @Override
    public Mono<Message> getMessageById(Snowflake id, EntityRetrievalStrategy retrievalStrategy) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMessageById'");
    }

    @Override
    public Flux<Message> getPinnedMessages() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPinnedMessages'");
    }
    
}
