package net.tomatentum.marinara.test.mocks;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.component.ComponentType;
import org.javacord.api.entity.message.component.HighLevelComponent;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.ButtonInteraction;
import org.javacord.api.interaction.DiscordLocale;
import org.javacord.api.interaction.InteractionType;
import org.javacord.api.interaction.callback.ComponentInteractionOriginalMessageUpdater;
import org.javacord.api.interaction.callback.InteractionFollowupMessageBuilder;
import org.javacord.api.interaction.callback.InteractionImmediateResponseBuilder;
import org.javacord.api.interaction.callback.InteractionOriginalResponseUpdater;

public class ButtonInteractionMock implements ButtonInteraction {

    @Override
    public Message getMessage() {
        return new MessageMock();
    }

    @Override
    public String getCustomId() {
        return "test";
    }

    @Override
    public ComponentType getComponentType() {
        return ComponentType.BUTTON;
    }

    @Override
    public CompletableFuture<Void> acknowledge() {
        throw new UnsupportedOperationException("Unimplemented method 'acknowledge'");
    }

    @Override
    public ComponentInteractionOriginalMessageUpdater createOriginalMessageUpdater() {
        throw new UnsupportedOperationException("Unimplemented method 'createOriginalMessageUpdater'");
    }

    @Override
    public long getApplicationId() {
        throw new UnsupportedOperationException("Unimplemented method 'getApplicationId'");
    }

    @Override
    public InteractionType getType() {
        return InteractionType.MESSAGE_COMPONENT;
    }

    @Override
    public InteractionImmediateResponseBuilder createImmediateResponder() {
        throw new UnsupportedOperationException("Unimplemented method 'createImmediateResponder'");
    }

    @Override
    public CompletableFuture<InteractionOriginalResponseUpdater> respondLater() {
        throw new UnsupportedOperationException("Unimplemented method 'respondLater'");
    }

    @Override
    public CompletableFuture<InteractionOriginalResponseUpdater> respondLater(boolean ephemeral) {
        throw new UnsupportedOperationException("Unimplemented method 'respondLater'");
    }

    @Override
    public CompletableFuture<Void> respondWithModal(String customId, String title,
            List<HighLevelComponent> components) {
        throw new UnsupportedOperationException("Unimplemented method 'respondWithModal'");
    }

    @Override
    public InteractionFollowupMessageBuilder createFollowupMessageBuilder() {
        throw new UnsupportedOperationException("Unimplemented method 'createFollowupMessageBuilder'");
    }

    @Override
    public Optional<Server> getServer() {
        return Optional.of(new ServerMock());
    }

    @Override
    public Optional<TextChannel> getChannel() {
        return Optional.of(new ChannelMock());
    }

    @Override
    public User getUser() {
        return new UserMock();
    }

    @Override
    public String getToken() {
        throw new UnsupportedOperationException("Unimplemented method 'getToken'");
    }

    @Override
    public int getVersion() {
        throw new UnsupportedOperationException("Unimplemented method 'getVersion'");
    }

    @Override
    public DiscordLocale getLocale() {
        throw new UnsupportedOperationException("Unimplemented method 'getLocale'");
    }

    @Override
    public Optional<DiscordLocale> getServerLocale() {
        throw new UnsupportedOperationException("Unimplemented method 'getServerLocale'");
    }

    @Override
    public Optional<EnumSet<PermissionType>> getBotPermissions() {
        throw new UnsupportedOperationException("Unimplemented method 'getBotPermissions'");
    }

    @Override
    public DiscordApi getApi() {
        throw new UnsupportedOperationException("Unimplemented method 'getApi'");
    }

    @Override
    public long getId() {
        throw new UnsupportedOperationException("Unimplemented method 'getId'");
    }
     
}
