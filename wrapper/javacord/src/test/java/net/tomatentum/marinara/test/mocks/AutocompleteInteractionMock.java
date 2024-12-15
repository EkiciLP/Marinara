package net.tomatentum.marinara.test.mocks;

import java.util.ArrayList;
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
import org.javacord.api.interaction.AutocompleteInteraction;
import org.javacord.api.interaction.DiscordLocale;
import org.javacord.api.interaction.InteractionType;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOptionChoice;
import org.javacord.api.interaction.callback.InteractionFollowupMessageBuilder;
import org.javacord.api.interaction.callback.InteractionImmediateResponseBuilder;
import org.javacord.api.interaction.callback.InteractionOriginalResponseUpdater;

public class AutocompleteInteractionMock implements AutocompleteInteraction {

    public static boolean didAutocompleteRun = false;

    @Override
    public String getFullCommandName() {
        return "test";
    }

    @Override
    public long getCommandId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCommandId'");
    }

    @Override
    public String getCommandIdAsString() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCommandIdAsString'");
    }

    @Override
    public String getCommandName() {
        return "test";
    }

    @Override
    public Optional<Long> getRegisteredCommandServerId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRegisteredCommandServerId'");
    }

    @Override
    public long getApplicationId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getApplicationId'");
    }

    @Override
    public InteractionType getType() {
        return InteractionType.APPLICATION_COMMAND_AUTOCOMPLETE;
    }

    @Override
    public InteractionImmediateResponseBuilder createImmediateResponder() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createImmediateResponder'");
    }

    @Override
    public CompletableFuture<InteractionOriginalResponseUpdater> respondLater() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'respondLater'");
    }

    @Override
    public CompletableFuture<InteractionOriginalResponseUpdater> respondLater(boolean ephemeral) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'respondLater'");
    }

    @Override
    public CompletableFuture<Void> respondWithModal(String customId, String title,
            List<HighLevelComponent> components) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'respondWithModal'");
    }

    @Override
    public InteractionFollowupMessageBuilder createFollowupMessageBuilder() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createFollowupMessageBuilder'");
    }

    @Override
    public Optional<Server> getServer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getServer'");
    }

    @Override
    public Optional<TextChannel> getChannel() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getChannel'");
    }

    @Override
    public User getUser() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUser'");
    }

    @Override
    public String getToken() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getToken'");
    }

    @Override
    public int getVersion() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getVersion'");
    }

    @Override
    public DiscordLocale getLocale() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLocale'");
    }

    @Override
    public Optional<DiscordLocale> getServerLocale() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getServerLocale'");
    }

    @Override
    public Optional<EnumSet<PermissionType>> getBotPermissions() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBotPermissions'");
    }

    @Override
    public DiscordApi getApi() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getApi'");
    }

    @Override
    public long getId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getId'");
    }

    @Override
    public List<SlashCommandInteractionOption> getOptions() {
        return new ArrayList<>();
    }

    @Override
    public List<SlashCommandInteractionOption> getArguments() {
        return new ArrayList<>();
    }

    @Override
    public CompletableFuture<Void> respondWithChoices(List<SlashCommandOptionChoice> choices) {
        didAutocompleteRun = true;
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public SlashCommandInteractionOption getFocusedOption() {
        return new SlashCommandInteractionOptionMock();
    }
    
}
