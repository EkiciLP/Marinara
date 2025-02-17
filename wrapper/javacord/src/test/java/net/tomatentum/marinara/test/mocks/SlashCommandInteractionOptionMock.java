package net.tomatentum.marinara.test.mocks;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.javacord.api.entity.Attachment;
import org.javacord.api.entity.Mentionable;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;

public class SlashCommandInteractionOptionMock implements SlashCommandInteractionOption{

    @Override
    public List<SlashCommandInteractionOption> getArguments() {
        return Collections.emptyList();
    }

    @Override
    public String getName() {
        return "foo";
    }

    @Override
    public Optional<Boolean> isFocused() {
        throw new UnsupportedOperationException("Unimplemented method 'isFocused'");
    }

    @Override
    public Optional<String> getStringRepresentationValue() {
        throw new UnsupportedOperationException("Unimplemented method 'getStringRepresentationValue'");
    }

    @Override
    public Optional<String> getStringValue() {
        return Optional.of("test");
    }

    @Override
    public Optional<Long> getLongValue() {
        return Optional.empty();
    }

    @Override
    public Optional<Boolean> getBooleanValue() {
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserValue() {
        return Optional.empty();
    }

    @Override
    public Optional<CompletableFuture<User>> requestUserValue() {
        return Optional.empty();
    }

    @Override
    public Optional<ServerChannel> getChannelValue() {
        return Optional.empty();
    }

    @Override
    public Optional<Attachment> getAttachmentValue() {
        return Optional.empty();
    }

    @Override
    public Optional<Role> getRoleValue() {
        return Optional.empty();
    }

    @Override
    public Optional<Mentionable> getMentionableValue() {
        return Optional.empty();
    }

    @Override
    public Optional<Double> getDecimalValue() {
        return Optional.empty();
    }

    @Override
    public Optional<CompletableFuture<Mentionable>> requestMentionableValue() {
        return Optional.empty();
    }

    @Override
    public List<SlashCommandInteractionOption> getOptions() {
        return Collections.emptyList();
    }
    
}
