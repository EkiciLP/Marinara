package net.tomatentum.marinara.test.mocks;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ChannelType;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageSet;
import org.javacord.api.entity.user.User;
import org.javacord.api.entity.webhook.IncomingWebhook;
import org.javacord.api.entity.webhook.Webhook;
import org.javacord.api.listener.ObjectAttachableListener;
import org.javacord.api.listener.channel.ChannelAttachableListener;
import org.javacord.api.listener.channel.ServerThreadChannelAttachableListener;
import org.javacord.api.listener.channel.TextChannelAttachableListener;
import org.javacord.api.listener.channel.server.thread.ServerThreadChannelCreateListener;
import org.javacord.api.listener.channel.server.thread.ServerThreadChannelDeleteListener;
import org.javacord.api.listener.channel.server.thread.ServerThreadChannelMembersUpdateListener;
import org.javacord.api.listener.channel.server.thread.ServerThreadChannelUpdateListener;
import org.javacord.api.listener.interaction.AutocompleteCreateListener;
import org.javacord.api.listener.interaction.ButtonClickListener;
import org.javacord.api.listener.interaction.InteractionCreateListener;
import org.javacord.api.listener.interaction.MessageComponentCreateListener;
import org.javacord.api.listener.interaction.MessageContextMenuCommandListener;
import org.javacord.api.listener.interaction.ModalSubmitListener;
import org.javacord.api.listener.interaction.SelectMenuChooseListener;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;
import org.javacord.api.listener.interaction.UserContextMenuCommandListener;
import org.javacord.api.listener.message.CachedMessagePinListener;
import org.javacord.api.listener.message.CachedMessageUnpinListener;
import org.javacord.api.listener.message.ChannelPinsUpdateListener;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.listener.message.MessageDeleteListener;
import org.javacord.api.listener.message.MessageEditListener;
import org.javacord.api.listener.message.MessageReplyListener;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import org.javacord.api.listener.message.reaction.ReactionRemoveAllListener;
import org.javacord.api.listener.message.reaction.ReactionRemoveListener;
import org.javacord.api.listener.server.thread.ServerPrivateThreadJoinListener;
import org.javacord.api.listener.server.thread.ServerThreadChannelChangeArchiveTimestampListener;
import org.javacord.api.listener.server.thread.ServerThreadChannelChangeArchivedListener;
import org.javacord.api.listener.server.thread.ServerThreadChannelChangeAutoArchiveDurationListener;
import org.javacord.api.listener.server.thread.ServerThreadChannelChangeInvitableListener;
import org.javacord.api.listener.server.thread.ServerThreadChannelChangeLastMessageIdListener;
import org.javacord.api.listener.server.thread.ServerThreadChannelChangeLockedListener;
import org.javacord.api.listener.server.thread.ServerThreadChannelChangeMemberCountListener;
import org.javacord.api.listener.server.thread.ServerThreadChannelChangeMessageCountListener;
import org.javacord.api.listener.server.thread.ServerThreadChannelChangeRateLimitPerUserListener;
import org.javacord.api.listener.server.thread.ServerThreadChannelChangeTotalMessageSentListener;
import org.javacord.api.listener.user.UserStartTypingListener;
import org.javacord.api.util.cache.MessageCache;
import org.javacord.api.util.event.ListenerManager;

public class ChannelMock implements TextChannel {

    @Override
    public ChannelType getType() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getType'");
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
    public <T extends ChannelAttachableListener & ObjectAttachableListener> Collection<ListenerManager<T>> addChannelAttachableListener(
            T listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addChannelAttachableListener'");
    }

    @Override
    public <T extends ChannelAttachableListener & ObjectAttachableListener> void removeChannelAttachableListener(
            T listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'removeChannelAttachableListener'");
    }

    @Override
    public <T extends ChannelAttachableListener & ObjectAttachableListener> Map<T, List<Class<T>>> getChannelAttachableListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getChannelAttachableListeners'");
    }

    @Override
    public <T extends ChannelAttachableListener & ObjectAttachableListener> void removeListener(Class<T> listenerClass,
            T listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'removeListener'");
    }

    @Override
    public ListenerManager<InteractionCreateListener> addInteractionCreateListener(InteractionCreateListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addInteractionCreateListener'");
    }

    @Override
    public List<InteractionCreateListener> getInteractionCreateListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getInteractionCreateListeners'");
    }

    @Override
    public ListenerManager<SlashCommandCreateListener> addSlashCommandCreateListener(
            SlashCommandCreateListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addSlashCommandCreateListener'");
    }

    @Override
    public List<SlashCommandCreateListener> getSlashCommandCreateListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getSlashCommandCreateListeners'");
    }

    @Override
    public ListenerManager<AutocompleteCreateListener> addAutocompleteCreateListener(
            AutocompleteCreateListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addAutocompleteCreateListener'");
    }

    @Override
    public List<AutocompleteCreateListener> getAutocompleteCreateListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getAutocompleteCreateListeners'");
    }

    @Override
    public ListenerManager<ModalSubmitListener> addModalSubmitListener(ModalSubmitListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addModalSubmitListener'");
    }

    @Override
    public List<ModalSubmitListener> getModalSubmitListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getModalSubmitListeners'");
    }

    @Override
    public ListenerManager<MessageContextMenuCommandListener> addMessageContextMenuCommandListener(
            MessageContextMenuCommandListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addMessageContextMenuCommandListener'");
    }

    @Override
    public List<MessageContextMenuCommandListener> getMessageContextMenuCommandListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMessageContextMenuCommandListeners'");
    }

    @Override
    public ListenerManager<MessageComponentCreateListener> addMessageComponentCreateListener(
            MessageComponentCreateListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addMessageComponentCreateListener'");
    }

    @Override
    public List<MessageComponentCreateListener> getMessageComponentCreateListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMessageComponentCreateListeners'");
    }

    @Override
    public ListenerManager<UserContextMenuCommandListener> addUserContextMenuCommandListener(
            UserContextMenuCommandListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addUserContextMenuCommandListener'");
    }

    @Override
    public List<UserContextMenuCommandListener> getUserContextMenuCommandListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getUserContextMenuCommandListeners'");
    }

    @Override
    public ListenerManager<SelectMenuChooseListener> addSelectMenuChooseListener(SelectMenuChooseListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addSelectMenuChooseListener'");
    }

    @Override
    public List<SelectMenuChooseListener> getSelectMenuChooseListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getSelectMenuChooseListeners'");
    }

    @Override
    public ListenerManager<ButtonClickListener> addButtonClickListener(ButtonClickListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addButtonClickListener'");
    }

    @Override
    public List<ButtonClickListener> getButtonClickListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getButtonClickListeners'");
    }

    @Override
    public ListenerManager<UserStartTypingListener> addUserStartTypingListener(UserStartTypingListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addUserStartTypingListener'");
    }

    @Override
    public List<UserStartTypingListener> getUserStartTypingListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getUserStartTypingListeners'");
    }

    @Override
    public ListenerManager<MessageEditListener> addMessageEditListener(MessageEditListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addMessageEditListener'");
    }

    @Override
    public List<MessageEditListener> getMessageEditListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMessageEditListeners'");
    }

    @Override
    public ListenerManager<ChannelPinsUpdateListener> addChannelPinsUpdateListener(ChannelPinsUpdateListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addChannelPinsUpdateListener'");
    }

    @Override
    public List<ChannelPinsUpdateListener> getChannelPinsUpdateListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getChannelPinsUpdateListeners'");
    }

    @Override
    public ListenerManager<ReactionRemoveListener> addReactionRemoveListener(ReactionRemoveListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addReactionRemoveListener'");
    }

    @Override
    public List<ReactionRemoveListener> getReactionRemoveListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getReactionRemoveListeners'");
    }

    @Override
    public ListenerManager<ReactionAddListener> addReactionAddListener(ReactionAddListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addReactionAddListener'");
    }

    @Override
    public List<ReactionAddListener> getReactionAddListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getReactionAddListeners'");
    }

    @Override
    public ListenerManager<ReactionRemoveAllListener> addReactionRemoveAllListener(ReactionRemoveAllListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addReactionRemoveAllListener'");
    }

    @Override
    public List<ReactionRemoveAllListener> getReactionRemoveAllListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getReactionRemoveAllListeners'");
    }

    @Override
    public ListenerManager<MessageCreateListener> addMessageCreateListener(MessageCreateListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addMessageCreateListener'");
    }

    @Override
    public List<MessageCreateListener> getMessageCreateListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMessageCreateListeners'");
    }

    @Override
    public ListenerManager<CachedMessageUnpinListener> addCachedMessageUnpinListener(
            CachedMessageUnpinListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addCachedMessageUnpinListener'");
    }

    @Override
    public List<CachedMessageUnpinListener> getCachedMessageUnpinListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getCachedMessageUnpinListeners'");
    }

    @Override
    public ListenerManager<CachedMessagePinListener> addCachedMessagePinListener(CachedMessagePinListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addCachedMessagePinListener'");
    }

    @Override
    public List<CachedMessagePinListener> getCachedMessagePinListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getCachedMessagePinListeners'");
    }

    @Override
    public ListenerManager<MessageReplyListener> addMessageReplyListener(MessageReplyListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addMessageReplyListener'");
    }

    @Override
    public List<MessageReplyListener> getMessageReplyListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMessageReplyListeners'");
    }

    @Override
    public ListenerManager<MessageDeleteListener> addMessageDeleteListener(MessageDeleteListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addMessageDeleteListener'");
    }

    @Override
    public List<MessageDeleteListener> getMessageDeleteListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMessageDeleteListeners'");
    }

    @Override
    public <T extends TextChannelAttachableListener & ObjectAttachableListener> Collection<ListenerManager<? extends TextChannelAttachableListener>> addTextChannelAttachableListener(
            T listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addTextChannelAttachableListener'");
    }

    @Override
    public <T extends TextChannelAttachableListener & ObjectAttachableListener> void removeTextChannelAttachableListener(
            T listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'removeTextChannelAttachableListener'");
    }

    @Override
    public <T extends TextChannelAttachableListener & ObjectAttachableListener> Map<T, List<Class<T>>> getTextChannelAttachableListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getTextChannelAttachableListeners'");
    }

    @Override
    public <T extends TextChannelAttachableListener & ObjectAttachableListener> void removeListener(
            Class<T> listenerClass, T listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'removeListener'");
    }

    @Override
    public ListenerManager<ServerThreadChannelChangeLastMessageIdListener> addServerThreadChannelChangeLastMessageIdListener(
            ServerThreadChannelChangeLastMessageIdListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addServerThreadChannelChangeLastMessageIdListener'");
    }

    @Override
    public List<ServerThreadChannelChangeLastMessageIdListener> getServerThreadChannelChangeLastMessageIdListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getServerThreadChannelChangeLastMessageIdListeners'");
    }

    @Override
    public ListenerManager<ServerThreadChannelChangeArchivedListener> addServerThreadChannelChangeArchivedListener(
            ServerThreadChannelChangeArchivedListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addServerThreadChannelChangeArchivedListener'");
    }

    @Override
    public List<ServerThreadChannelChangeArchivedListener> getServerThreadChannelChangeArchivedListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getServerThreadChannelChangeArchivedListeners'");
    }

    @Override
    public ListenerManager<ServerThreadChannelChangeMemberCountListener> addServerThreadChannelChangeMemberCountListener(
            ServerThreadChannelChangeMemberCountListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addServerThreadChannelChangeMemberCountListener'");
    }

    @Override
    public List<ServerThreadChannelChangeMemberCountListener> getServerThreadChannelChangeMemberCountListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getServerThreadChannelChangeMemberCountListeners'");
    }

    @Override
    public ListenerManager<ServerPrivateThreadJoinListener> addServerPrivateThreadJoinListener(
            ServerPrivateThreadJoinListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addServerPrivateThreadJoinListener'");
    }

    @Override
    public List<ServerPrivateThreadJoinListener> getServerPrivateThreadJoinListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getServerPrivateThreadJoinListeners'");
    }

    @Override
    public ListenerManager<ServerThreadChannelChangeInvitableListener> addServerThreadChannelChangeInvitableListener(
            ServerThreadChannelChangeInvitableListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addServerThreadChannelChangeInvitableListener'");
    }

    @Override
    public List<ServerThreadChannelChangeInvitableListener> getServerThreadChannelChangeInvitableListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getServerThreadChannelChangeInvitableListeners'");
    }

    @Override
    public ListenerManager<ServerThreadChannelChangeAutoArchiveDurationListener> addServerThreadChannelChangeAutoArchiveDurationListener(
            ServerThreadChannelChangeAutoArchiveDurationListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addServerThreadChannelChangeAutoArchiveDurationListener'");
    }

    @Override
    public List<ServerThreadChannelChangeAutoArchiveDurationListener> getServerThreadChannelChangeAutoArchiveDurationListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getServerThreadChannelChangeAutoArchiveDurationListeners'");
    }

    @Override
    public ListenerManager<ServerThreadChannelChangeRateLimitPerUserListener> addServerThreadChannelChangeRateLimitPerUserListener(
            ServerThreadChannelChangeRateLimitPerUserListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addServerThreadChannelChangeRateLimitPerUserListener'");
    }

    @Override
    public List<ServerThreadChannelChangeRateLimitPerUserListener> getServerThreadChannelChangeRateLimitPerUserListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getServerThreadChannelChangeRateLimitPerUserListeners'");
    }

    @Override
    public ListenerManager<ServerThreadChannelChangeLockedListener> addServerThreadChannelChangeLockedListener(
            ServerThreadChannelChangeLockedListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addServerThreadChannelChangeLockedListener'");
    }

    @Override
    public List<ServerThreadChannelChangeLockedListener> getServerThreadChannelChangeLockedListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getServerThreadChannelChangeLockedListeners'");
    }

    @Override
    public ListenerManager<ServerThreadChannelChangeArchiveTimestampListener> addServerThreadChannelChangeArchiveTimestampListener(
            ServerThreadChannelChangeArchiveTimestampListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addServerThreadChannelChangeArchiveTimestampListener'");
    }

    @Override
    public List<ServerThreadChannelChangeArchiveTimestampListener> getServerThreadChannelChangeArchiveTimestampListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getServerThreadChannelChangeArchiveTimestampListeners'");
    }

    @Override
    public ListenerManager<ServerThreadChannelChangeTotalMessageSentListener> addServerThreadChannelChangeTotalMessageSentListener(
            ServerThreadChannelChangeTotalMessageSentListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addServerThreadChannelChangeTotalMessageSentListener'");
    }

    @Override
    public List<ServerThreadChannelChangeTotalMessageSentListener> getServerThreadChannelChangeTotalMessageSentListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getServerThreadChannelChangeTotalMessageSentListeners'");
    }

    @Override
    public ListenerManager<ServerThreadChannelChangeMessageCountListener> addServerThreadChannelChangeMessageCountListener(
            ServerThreadChannelChangeMessageCountListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addServerThreadChannelChangeMessageCountListener'");
    }

    @Override
    public List<ServerThreadChannelChangeMessageCountListener> getServerThreadChannelChangeMessageCountListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getServerThreadChannelChangeMessageCountListeners'");
    }

    @Override
    public ListenerManager<ServerThreadChannelUpdateListener> addServerThreadChannelUpdateListener(
            ServerThreadChannelUpdateListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addServerThreadChannelUpdateListener'");
    }

    @Override
    public List<ServerThreadChannelUpdateListener> getServerThreadChannelUpdateListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getServerThreadChannelUpdateListeners'");
    }

    @Override
    public ListenerManager<ServerThreadChannelMembersUpdateListener> addServerThreadChannelMembersUpdateListener(
            ServerThreadChannelMembersUpdateListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addServerThreadChannelMembersUpdateListener'");
    }

    @Override
    public List<ServerThreadChannelMembersUpdateListener> getServerThreadChannelMembersUpdateListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getServerThreadChannelMembersUpdateListeners'");
    }

    @Override
    public ListenerManager<ServerThreadChannelCreateListener> addServerThreadChannelCreateListener(
            ServerThreadChannelCreateListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addServerThreadChannelCreateListener'");
    }

    @Override
    public List<ServerThreadChannelCreateListener> getServerThreadChannelCreateListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getServerThreadChannelCreateListeners'");
    }

    @Override
    public ListenerManager<ServerThreadChannelDeleteListener> addServerThreadChannelDeleteListener(
            ServerThreadChannelDeleteListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addServerThreadChannelDeleteListener'");
    }

    @Override
    public List<ServerThreadChannelDeleteListener> getServerThreadChannelDeleteListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getServerThreadChannelDeleteListeners'");
    }

    @Override
    public <T extends ServerThreadChannelAttachableListener & ObjectAttachableListener> Collection<ListenerManager<T>> addServerThreadChannelAttachableListener(
            T listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addServerThreadChannelAttachableListener'");
    }

    @Override
    public <T extends ServerThreadChannelAttachableListener & ObjectAttachableListener> void removeServerThreadChannelAttachableListener(
            T listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'removeServerThreadChannelAttachableListener'");
    }

    @Override
    public <T extends ServerThreadChannelAttachableListener & ObjectAttachableListener> Map<T, List<Class<T>>> getServerThreadChannelAttachableListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getServerThreadChannelAttachableListeners'");
    }

    @Override
    public <T extends ServerThreadChannelAttachableListener & ObjectAttachableListener> void removeListener(
            Class<T> listenerClass, T listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'removeListener'");
    }

    @Override
    public CompletableFuture<Void> type() {
        
        throw new UnsupportedOperationException("Unimplemented method 'type'");
    }

    @Override
    public CompletableFuture<Void> bulkDelete(long... messageIds) {
        
        throw new UnsupportedOperationException("Unimplemented method 'bulkDelete'");
    }

    @Override
    public CompletableFuture<Message> getMessageById(long id) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMessageById'");
    }

    @Override
    public CompletableFuture<MessageSet> getPins() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getPins'");
    }

    @Override
    public CompletableFuture<MessageSet> getMessages(int limit) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMessages'");
    }

    @Override
    public CompletableFuture<MessageSet> getMessagesUntil(Predicate<Message> condition) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMessagesUntil'");
    }

    @Override
    public CompletableFuture<MessageSet> getMessagesWhile(Predicate<Message> condition) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMessagesWhile'");
    }

    @Override
    public Stream<Message> getMessagesAsStream() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMessagesAsStream'");
    }

    @Override
    public CompletableFuture<MessageSet> getMessagesBefore(int limit, long before) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMessagesBefore'");
    }

    @Override
    public CompletableFuture<MessageSet> getMessagesBeforeUntil(Predicate<Message> condition, long before) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMessagesBeforeUntil'");
    }

    @Override
    public CompletableFuture<MessageSet> getMessagesBeforeWhile(Predicate<Message> condition, long before) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMessagesBeforeWhile'");
    }

    @Override
    public Stream<Message> getMessagesBeforeAsStream(long before) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMessagesBeforeAsStream'");
    }

    @Override
    public CompletableFuture<MessageSet> getMessagesAfter(int limit, long after) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMessagesAfter'");
    }

    @Override
    public CompletableFuture<MessageSet> getMessagesAfterUntil(Predicate<Message> condition, long after) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMessagesAfterUntil'");
    }

    @Override
    public CompletableFuture<MessageSet> getMessagesAfterWhile(Predicate<Message> condition, long after) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMessagesAfterWhile'");
    }

    @Override
    public Stream<Message> getMessagesAfterAsStream(long after) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMessagesAfterAsStream'");
    }

    @Override
    public CompletableFuture<MessageSet> getMessagesAround(int limit, long around) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMessagesAround'");
    }

    @Override
    public CompletableFuture<MessageSet> getMessagesAroundUntil(Predicate<Message> condition, long around) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMessagesAroundUntil'");
    }

    @Override
    public CompletableFuture<MessageSet> getMessagesAroundWhile(Predicate<Message> condition, long around) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMessagesAroundWhile'");
    }

    @Override
    public Stream<Message> getMessagesAroundAsStream(long around) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMessagesAroundAsStream'");
    }

    @Override
    public CompletableFuture<MessageSet> getMessagesBetween(long from, long to) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMessagesBetween'");
    }

    @Override
    public CompletableFuture<MessageSet> getMessagesBetweenUntil(Predicate<Message> condition, long from, long to) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMessagesBetweenUntil'");
    }

    @Override
    public CompletableFuture<MessageSet> getMessagesBetweenWhile(Predicate<Message> condition, long from, long to) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMessagesBetweenWhile'");
    }

    @Override
    public Stream<Message> getMessagesBetweenAsStream(long from, long to) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMessagesBetweenAsStream'");
    }

    @Override
    public MessageCache getMessageCache() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMessageCache'");
    }

    @Override
    public CompletableFuture<List<Webhook>> getWebhooks() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getWebhooks'");
    }

    @Override
    public CompletableFuture<List<Webhook>> getAllIncomingWebhooks() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getAllIncomingWebhooks'");
    }

    @Override
    public CompletableFuture<List<IncomingWebhook>> getIncomingWebhooks() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getIncomingWebhooks'");
    }

    @Override
    public boolean canWrite(User user) {
        
        throw new UnsupportedOperationException("Unimplemented method 'canWrite'");
    }

    @Override
    public boolean canUseExternalEmojis(User user) {
        
        throw new UnsupportedOperationException("Unimplemented method 'canUseExternalEmojis'");
    }

    @Override
    public boolean canEmbedLinks(User user) {
        
        throw new UnsupportedOperationException("Unimplemented method 'canEmbedLinks'");
    }

    @Override
    public boolean canReadMessageHistory(User user) {
        
        throw new UnsupportedOperationException("Unimplemented method 'canReadMessageHistory'");
    }

    @Override
    public boolean canUseTts(User user) {
        
        throw new UnsupportedOperationException("Unimplemented method 'canUseTts'");
    }

    @Override
    public boolean canAttachFiles(User user) {
        
        throw new UnsupportedOperationException("Unimplemented method 'canAttachFiles'");
    }

    @Override
    public boolean canAddNewReactions(User user) {
        
        throw new UnsupportedOperationException("Unimplemented method 'canAddNewReactions'");
    }

    @Override
    public boolean canManageMessages(User user) {
        
        throw new UnsupportedOperationException("Unimplemented method 'canManageMessages'");
    }

    @Override
    public boolean canMentionEveryone(User user) {
        
        throw new UnsupportedOperationException("Unimplemented method 'canMentionEveryone'");
    }
    
}
