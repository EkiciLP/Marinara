package net.tomatentum.marinara.test.mocks;

import java.time.Instant;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.CustomEmoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageActivity;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.MessageReference;
import org.javacord.api.entity.message.MessageType;
import org.javacord.api.entity.message.Reaction;
import org.javacord.api.entity.message.component.HighLevelComponent;
import org.javacord.api.entity.message.embed.Embed;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.sticker.StickerItem;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.MessageInteraction;
import org.javacord.api.listener.ObjectAttachableListener;
import org.javacord.api.listener.interaction.ButtonClickListener;
import org.javacord.api.listener.interaction.MessageComponentCreateListener;
import org.javacord.api.listener.interaction.MessageContextMenuCommandListener;
import org.javacord.api.listener.interaction.SelectMenuChooseListener;
import org.javacord.api.listener.message.CachedMessagePinListener;
import org.javacord.api.listener.message.CachedMessageUnpinListener;
import org.javacord.api.listener.message.MessageAttachableListener;
import org.javacord.api.listener.message.MessageDeleteListener;
import org.javacord.api.listener.message.MessageEditListener;
import org.javacord.api.listener.message.MessageReplyListener;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import org.javacord.api.listener.message.reaction.ReactionRemoveAllListener;
import org.javacord.api.listener.message.reaction.ReactionRemoveListener;
import org.javacord.api.util.event.ListenerManager;

public class MessageMock implements Message{

    @Override
    public DiscordApi getApi() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getApi'");
    }

    @Override
    public long getId() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getId'");
    }

    @Override
    public int compareTo(Message arg0) {
        
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
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
    public ListenerManager<MessageEditListener> addMessageEditListener(MessageEditListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addMessageEditListener'");
    }

    @Override
    public List<MessageEditListener> getMessageEditListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMessageEditListeners'");
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
    public <T extends MessageAttachableListener & ObjectAttachableListener> Collection<ListenerManager<T>> addMessageAttachableListener(
            T listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addMessageAttachableListener'");
    }

    @Override
    public <T extends MessageAttachableListener & ObjectAttachableListener> void removeMessageAttachableListener(
            T listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'removeMessageAttachableListener'");
    }

    @Override
    public <T extends MessageAttachableListener & ObjectAttachableListener> Map<T, List<Class<T>>> getMessageAttachableListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMessageAttachableListeners'");
    }

    @Override
    public <T extends MessageAttachableListener & ObjectAttachableListener> void removeListener(Class<T> listenerClass,
            T listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'removeListener'");
    }

    @Override
    public boolean canYouReadContent() {
        
        throw new UnsupportedOperationException("Unimplemented method 'canYouReadContent'");
    }

    @Override
    public String getContent() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getContent'");
    }

    @Override
    public Optional<Instant> getLastEditTimestamp() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getLastEditTimestamp'");
    }

    @Override
    public List<MessageAttachment> getAttachments() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getAttachments'");
    }

    @Override
    public List<CustomEmoji> getCustomEmojis() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getCustomEmojis'");
    }

    @Override
    public MessageType getType() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getType'");
    }

    @Override
    public TextChannel getChannel() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getChannel'");
    }

    @Override
    public Optional<MessageActivity> getActivity() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getActivity'");
    }

    @Override
    public EnumSet<MessageFlag> getFlags() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getFlags'");
    }

    @Override
    public boolean isPinned() {
        
        throw new UnsupportedOperationException("Unimplemented method 'isPinned'");
    }

    @Override
    public boolean isTts() {
        
        throw new UnsupportedOperationException("Unimplemented method 'isTts'");
    }

    @Override
    public boolean mentionsEveryone() {
        
        throw new UnsupportedOperationException("Unimplemented method 'mentionsEveryone'");
    }

    @Override
    public List<Embed> getEmbeds() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getEmbeds'");
    }

    @Override
    public Optional<User> getUserAuthor() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getUserAuthor'");
    }

    @Override
    public MessageAuthor getAuthor() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getAuthor'");
    }

    @Override
    public Optional<MessageReference> getMessageReference() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMessageReference'");
    }

    @Override
    public Optional<Message> getReferencedMessage() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getReferencedMessage'");
    }

    @Override
    public boolean isCachedForever() {
        
        throw new UnsupportedOperationException("Unimplemented method 'isCachedForever'");
    }

    @Override
    public void setCachedForever(boolean cachedForever) {
        
        throw new UnsupportedOperationException("Unimplemented method 'setCachedForever'");
    }

    @Override
    public List<Reaction> getReactions() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getReactions'");
    }

    @Override
    public Optional<MessageInteraction> getMessageInteraction() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMessageInteraction'");
    }

    @Override
    public List<HighLevelComponent> getComponents() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getComponents'");
    }

    @Override
    public List<User> getMentionedUsers() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMentionedUsers'");
    }

    @Override
    public List<Role> getMentionedRoles() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMentionedRoles'");
    }

    @Override
    public Optional<String> getNonce() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getNonce'");
    }

    @Override
    public Set<StickerItem> getStickerItems() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getStickerItems'");
    }

    @Override
    public Optional<Integer> getPosition() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getPosition'");
    }

    @Override
    public CompletableFuture<Void> addReactions(String... unicodeEmojis) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addReactions'");
    }

    @Override
    public CompletableFuture<Void> removeReactionByEmoji(User user, String unicodeEmoji) {
        
        throw new UnsupportedOperationException("Unimplemented method 'removeReactionByEmoji'");
    }

    @Override
    public CompletableFuture<Void> removeReactionByEmoji(String unicodeEmoji) {
        
        throw new UnsupportedOperationException("Unimplemented method 'removeReactionByEmoji'");
    }

    @Override
    public CompletableFuture<Void> removeReactionsByEmoji(User user, String... unicodeEmojis) {
        
        throw new UnsupportedOperationException("Unimplemented method 'removeReactionsByEmoji'");
    }

    @Override
    public CompletableFuture<Void> removeReactionsByEmoji(String... unicodeEmojis) {
        
        throw new UnsupportedOperationException("Unimplemented method 'removeReactionsByEmoji'");
    }

    @Override
    public CompletableFuture<Void> removeOwnReactionByEmoji(String unicodeEmoji) {
        
        throw new UnsupportedOperationException("Unimplemented method 'removeOwnReactionByEmoji'");
    }

    @Override
    public CompletableFuture<Void> removeOwnReactionsByEmoji(String... unicodeEmojis) {
        
        throw new UnsupportedOperationException("Unimplemented method 'removeOwnReactionsByEmoji'");
    }
    
}
