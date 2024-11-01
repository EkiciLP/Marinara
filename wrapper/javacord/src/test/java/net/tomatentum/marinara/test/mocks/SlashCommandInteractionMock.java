package net.tomatentum.marinara.test.mocks;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.component.HighLevelComponent;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.DiscordLocale;
import org.javacord.api.interaction.InteractionType;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.callback.InteractionFollowupMessageBuilder;
import org.javacord.api.interaction.callback.InteractionImmediateResponseBuilder;
import org.javacord.api.interaction.callback.InteractionOriginalResponseUpdater;

public class SlashCommandInteractionMock implements SlashCommandInteraction{

    @Override
    public long getCommandId() {
        throw new UnsupportedOperationException("Unimplemented method 'getCommandId'");
    }

    @Override
    public String getCommandIdAsString() {
        throw new UnsupportedOperationException("Unimplemented method 'getCommandIdAsString'");
    }

    @Override
    public String getCommandName() {
        return "test";
    }

    @Override
    public Optional<Long> getRegisteredCommandServerId() {
        throw new UnsupportedOperationException("Unimplemented method 'getRegisteredCommandServerId'");
    }

    @Override
    public long getApplicationId() {
        throw new UnsupportedOperationException("Unimplemented method 'getApplicationId'");
    }

    @Override
    public InteractionType getType() {
        return InteractionType.APPLICATION_COMMAND;
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
        throw new UnsupportedOperationException("Unimplemented method 'getServer'");
    }

    @Override
    public Optional<TextChannel> getChannel() {
        throw new UnsupportedOperationException("Unimplemented method 'getChannel'");
    }

    @Override
    public User getUser() {
        throw new UnsupportedOperationException("Unimplemented method 'getUser'");
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

    @Override
    public List<SlashCommandInteractionOption> getOptions() {
        return Arrays.asList(new SlashCommandInteractionOptionMock());
    }

    @Override
    public List<SlashCommandInteractionOption> getArguments() {
        return Arrays.asList(new SlashCommandInteractionOptionMock());
    }

    @Override
    public String getFullCommandName() {
        return "test";
    }
    
}
