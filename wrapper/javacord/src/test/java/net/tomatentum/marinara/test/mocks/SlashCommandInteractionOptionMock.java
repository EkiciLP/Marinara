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
        throw new UnsupportedOperationException("Unimplemented method 'getLongValue'");
    }

    @Override
    public Optional<Boolean> getBooleanValue() {
        throw new UnsupportedOperationException("Unimplemented method 'getBooleanValue'");
    }

    @Override
    public Optional<User> getUserValue() {
        throw new UnsupportedOperationException("Unimplemented method 'getUserValue'");
    }

    @Override
    public Optional<CompletableFuture<User>> requestUserValue() {
        throw new UnsupportedOperationException("Unimplemented method 'requestUserValue'");
    }

    @Override
    public Optional<ServerChannel> getChannelValue() {
        throw new UnsupportedOperationException("Unimplemented method 'getChannelValue'");
    }

    @Override
    public Optional<Attachment> getAttachmentValue() {
        throw new UnsupportedOperationException("Unimplemented method 'getAttachmentValue'");
    }

    @Override
    public Optional<Role> getRoleValue() {
        throw new UnsupportedOperationException("Unimplemented method 'getRoleValue'");
    }

    @Override
    public Optional<Mentionable> getMentionableValue() {
        throw new UnsupportedOperationException("Unimplemented method 'getMentionableValue'");
    }

    @Override
    public Optional<Double> getDecimalValue() {
        throw new UnsupportedOperationException("Unimplemented method 'getDecimalValue'");
    }

    @Override
    public Optional<CompletableFuture<Mentionable>> requestMentionableValue() {
        throw new UnsupportedOperationException("Unimplemented method 'requestMentionableValue'");
    }

    @Override
    public List<SlashCommandInteractionOption> getOptions() {
        return Collections.emptyList();
    }
    
}
