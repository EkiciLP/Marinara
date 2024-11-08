package net.tomatentum.marinara.test.mocks;

import java.awt.Color;
import java.time.Instant;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.DiscordClient;
import org.javacord.api.entity.Icon;
import org.javacord.api.entity.activity.Activity;
import org.javacord.api.entity.channel.PrivateChannel;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.entity.user.UserFlag;
import org.javacord.api.entity.user.UserStatus;
import org.javacord.api.listener.ObjectAttachableListener;
import org.javacord.api.listener.channel.server.ServerChannelChangeOverwrittenPermissionsListener;
import org.javacord.api.listener.channel.server.voice.ServerVoiceChannelMemberJoinListener;
import org.javacord.api.listener.channel.server.voice.ServerVoiceChannelMemberLeaveListener;
import org.javacord.api.listener.channel.user.PrivateChannelCreateListener;
import org.javacord.api.listener.channel.user.PrivateChannelDeleteListener;
import org.javacord.api.listener.interaction.AutocompleteCreateListener;
import org.javacord.api.listener.interaction.ButtonClickListener;
import org.javacord.api.listener.interaction.InteractionCreateListener;
import org.javacord.api.listener.interaction.MessageComponentCreateListener;
import org.javacord.api.listener.interaction.MessageContextMenuCommandListener;
import org.javacord.api.listener.interaction.ModalSubmitListener;
import org.javacord.api.listener.interaction.SelectMenuChooseListener;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;
import org.javacord.api.listener.interaction.UserContextMenuCommandListener;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.listener.message.MessageReplyListener;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import org.javacord.api.listener.message.reaction.ReactionRemoveListener;
import org.javacord.api.listener.server.member.ServerMemberBanListener;
import org.javacord.api.listener.server.member.ServerMemberJoinListener;
import org.javacord.api.listener.server.member.ServerMemberLeaveListener;
import org.javacord.api.listener.server.member.ServerMemberUnbanListener;
import org.javacord.api.listener.server.role.UserRoleAddListener;
import org.javacord.api.listener.server.role.UserRoleRemoveListener;
import org.javacord.api.listener.user.UserAttachableListener;
import org.javacord.api.listener.user.UserChangeActivityListener;
import org.javacord.api.listener.user.UserChangeAvatarListener;
import org.javacord.api.listener.user.UserChangeDeafenedListener;
import org.javacord.api.listener.user.UserChangeDiscriminatorListener;
import org.javacord.api.listener.user.UserChangeMutedListener;
import org.javacord.api.listener.user.UserChangeNameListener;
import org.javacord.api.listener.user.UserChangeNicknameListener;
import org.javacord.api.listener.user.UserChangePendingListener;
import org.javacord.api.listener.user.UserChangeSelfDeafenedListener;
import org.javacord.api.listener.user.UserChangeSelfMutedListener;
import org.javacord.api.listener.user.UserChangeServerAvatarListener;
import org.javacord.api.listener.user.UserChangeStatusListener;
import org.javacord.api.listener.user.UserChangeTimeoutListener;
import org.javacord.api.listener.user.UserStartTypingListener;
import org.javacord.api.util.event.ListenerManager;

public class UserMock implements User {

    @Override
    public DiscordApi getApi() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getApi'");
    }

    @Override
    public long getId() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getId'");
    }

    @Override
    public String getName() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getName'");
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
    public ListenerManager<ServerMemberJoinListener> addServerMemberJoinListener(ServerMemberJoinListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addServerMemberJoinListener'");
    }

    @Override
    public List<ServerMemberJoinListener> getServerMemberJoinListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getServerMemberJoinListeners'");
    }

    @Override
    public ListenerManager<ServerMemberLeaveListener> addServerMemberLeaveListener(ServerMemberLeaveListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addServerMemberLeaveListener'");
    }

    @Override
    public List<ServerMemberLeaveListener> getServerMemberLeaveListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getServerMemberLeaveListeners'");
    }

    @Override
    public ListenerManager<ServerMemberBanListener> addServerMemberBanListener(ServerMemberBanListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addServerMemberBanListener'");
    }

    @Override
    public List<ServerMemberBanListener> getServerMemberBanListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getServerMemberBanListeners'");
    }

    @Override
    public ListenerManager<ServerMemberUnbanListener> addServerMemberUnbanListener(ServerMemberUnbanListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addServerMemberUnbanListener'");
    }

    @Override
    public List<ServerMemberUnbanListener> getServerMemberUnbanListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getServerMemberUnbanListeners'");
    }

    @Override
    public ListenerManager<UserRoleRemoveListener> addUserRoleRemoveListener(UserRoleRemoveListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addUserRoleRemoveListener'");
    }

    @Override
    public List<UserRoleRemoveListener> getUserRoleRemoveListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getUserRoleRemoveListeners'");
    }

    @Override
    public ListenerManager<UserRoleAddListener> addUserRoleAddListener(UserRoleAddListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addUserRoleAddListener'");
    }

    @Override
    public List<UserRoleAddListener> getUserRoleAddListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getUserRoleAddListeners'");
    }

    @Override
    public ListenerManager<ServerChannelChangeOverwrittenPermissionsListener> addServerChannelChangeOverwrittenPermissionsListener(
            ServerChannelChangeOverwrittenPermissionsListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addServerChannelChangeOverwrittenPermissionsListener'");
    }

    @Override
    public List<ServerChannelChangeOverwrittenPermissionsListener> getServerChannelChangeOverwrittenPermissionsListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getServerChannelChangeOverwrittenPermissionsListeners'");
    }

    @Override
    public ListenerManager<ServerVoiceChannelMemberLeaveListener> addServerVoiceChannelMemberLeaveListener(
            ServerVoiceChannelMemberLeaveListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addServerVoiceChannelMemberLeaveListener'");
    }

    @Override
    public List<ServerVoiceChannelMemberLeaveListener> getServerVoiceChannelMemberLeaveListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getServerVoiceChannelMemberLeaveListeners'");
    }

    @Override
    public ListenerManager<ServerVoiceChannelMemberJoinListener> addServerVoiceChannelMemberJoinListener(
            ServerVoiceChannelMemberJoinListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addServerVoiceChannelMemberJoinListener'");
    }

    @Override
    public List<ServerVoiceChannelMemberJoinListener> getServerVoiceChannelMemberJoinListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getServerVoiceChannelMemberJoinListeners'");
    }

    @Override
    public ListenerManager<PrivateChannelDeleteListener> addPrivateChannelDeleteListener(
            PrivateChannelDeleteListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addPrivateChannelDeleteListener'");
    }

    @Override
    public List<PrivateChannelDeleteListener> getPrivateChannelDeleteListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getPrivateChannelDeleteListeners'");
    }

    @Override
    public ListenerManager<PrivateChannelCreateListener> addPrivateChannelCreateListener(
            PrivateChannelCreateListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addPrivateChannelCreateListener'");
    }

    @Override
    public List<PrivateChannelCreateListener> getPrivateChannelCreateListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getPrivateChannelCreateListeners'");
    }

    @Override
    public ListenerManager<UserChangeDeafenedListener> addUserChangeDeafenedListener(
            UserChangeDeafenedListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addUserChangeDeafenedListener'");
    }

    @Override
    public List<UserChangeDeafenedListener> getUserChangeDeafenedListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getUserChangeDeafenedListeners'");
    }

    @Override
    public ListenerManager<UserChangeNicknameListener> addUserChangeNicknameListener(
            UserChangeNicknameListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addUserChangeNicknameListener'");
    }

    @Override
    public List<UserChangeNicknameListener> getUserChangeNicknameListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getUserChangeNicknameListeners'");
    }

    @Override
    public ListenerManager<UserChangePendingListener> addUserChangePendingListener(UserChangePendingListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addUserChangePendingListener'");
    }

    @Override
    public List<UserChangePendingListener> getUserChangePendingListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getUserChangePendingListeners'");
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
    public ListenerManager<UserChangeDiscriminatorListener> addUserChangeDiscriminatorListener(
            UserChangeDiscriminatorListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addUserChangeDiscriminatorListener'");
    }

    @Override
    public List<UserChangeDiscriminatorListener> getUserChangeDiscriminatorListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getUserChangeDiscriminatorListeners'");
    }

    @Override
    public ListenerManager<UserChangeStatusListener> addUserChangeStatusListener(UserChangeStatusListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addUserChangeStatusListener'");
    }

    @Override
    public List<UserChangeStatusListener> getUserChangeStatusListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getUserChangeStatusListeners'");
    }

    @Override
    public ListenerManager<UserChangeServerAvatarListener> addUserChangeServerAvatarListener(
            UserChangeServerAvatarListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addUserChangeServerAvatarListener'");
    }

    @Override
    public List<UserChangeServerAvatarListener> getUserChangeServerAvatarListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getUserChangeServerAvatarListeners'");
    }

    @Override
    public ListenerManager<UserChangeSelfMutedListener> addUserChangeSelfMutedListener(
            UserChangeSelfMutedListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addUserChangeSelfMutedListener'");
    }

    @Override
    public List<UserChangeSelfMutedListener> getUserChangeSelfMutedListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getUserChangeSelfMutedListeners'");
    }

    @Override
    public ListenerManager<UserChangeNameListener> addUserChangeNameListener(UserChangeNameListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addUserChangeNameListener'");
    }

    @Override
    public List<UserChangeNameListener> getUserChangeNameListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getUserChangeNameListeners'");
    }

    @Override
    public ListenerManager<UserChangeTimeoutListener> addUserChangeTimeoutListener(UserChangeTimeoutListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addUserChangeTimeoutListener'");
    }

    @Override
    public List<UserChangeTimeoutListener> getUserChangeTimeoutListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getUserChangeTimeoutListeners'");
    }

    @Override
    public ListenerManager<UserChangeAvatarListener> addUserChangeAvatarListener(UserChangeAvatarListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addUserChangeAvatarListener'");
    }

    @Override
    public List<UserChangeAvatarListener> getUserChangeAvatarListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getUserChangeAvatarListeners'");
    }

    @Override
    public ListenerManager<UserChangeSelfDeafenedListener> addUserChangeSelfDeafenedListener(
            UserChangeSelfDeafenedListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addUserChangeSelfDeafenedListener'");
    }

    @Override
    public List<UserChangeSelfDeafenedListener> getUserChangeSelfDeafenedListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getUserChangeSelfDeafenedListeners'");
    }

    @Override
    public ListenerManager<UserChangeMutedListener> addUserChangeMutedListener(UserChangeMutedListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addUserChangeMutedListener'");
    }

    @Override
    public List<UserChangeMutedListener> getUserChangeMutedListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getUserChangeMutedListeners'");
    }

    @Override
    public ListenerManager<UserChangeActivityListener> addUserChangeActivityListener(
            UserChangeActivityListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addUserChangeActivityListener'");
    }

    @Override
    public List<UserChangeActivityListener> getUserChangeActivityListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getUserChangeActivityListeners'");
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
    public ListenerManager<MessageCreateListener> addMessageCreateListener(MessageCreateListener listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addMessageCreateListener'");
    }

    @Override
    public List<MessageCreateListener> getMessageCreateListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMessageCreateListeners'");
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
    public <T extends UserAttachableListener & ObjectAttachableListener> Collection<ListenerManager<T>> addUserAttachableListener(
            T listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'addUserAttachableListener'");
    }

    @Override
    public <T extends UserAttachableListener & ObjectAttachableListener> void removeUserAttachableListener(T listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'removeUserAttachableListener'");
    }

    @Override
    public <T extends UserAttachableListener & ObjectAttachableListener> Map<T, List<Class<T>>> getUserAttachableListeners() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getUserAttachableListeners'");
    }

    @Override
    public <T extends UserAttachableListener & ObjectAttachableListener> void removeListener(Class<T> listenerClass,
            T listener) {
        
        throw new UnsupportedOperationException("Unimplemented method 'removeListener'");
    }

    @Override
    public String getDiscriminator() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getDiscriminator'");
    }

    @Override
    public boolean isBot() {
        
        throw new UnsupportedOperationException("Unimplemented method 'isBot'");
    }

    @Override
    public Set<Activity> getActivities() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getActivities'");
    }

    @Override
    public UserStatus getStatus() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getStatus'");
    }

    @Override
    public UserStatus getStatusOnClient(DiscordClient client) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getStatusOnClient'");
    }

    @Override
    public EnumSet<UserFlag> getUserFlags() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getUserFlags'");
    }

    @Override
    public Optional<String> getAvatarHash() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getAvatarHash'");
    }

    @Override
    public Icon getAvatar() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getAvatar'");
    }

    @Override
    public Icon getAvatar(int size) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getAvatar'");
    }

    @Override
    public Optional<String> getServerAvatarHash(Server server) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getServerAvatarHash'");
    }

    @Override
    public Optional<Icon> getServerAvatar(Server server) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getServerAvatar'");
    }

    @Override
    public Optional<Icon> getServerAvatar(Server server, int size) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getServerAvatar'");
    }

    @Override
    public Icon getEffectiveAvatar(Server server) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getEffectiveAvatar'");
    }

    @Override
    public Icon getEffectiveAvatar(Server server, int size) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getEffectiveAvatar'");
    }

    @Override
    public boolean hasDefaultAvatar() {
        
        throw new UnsupportedOperationException("Unimplemented method 'hasDefaultAvatar'");
    }

    @Override
    public Set<Server> getMutualServers() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getMutualServers'");
    }

    @Override
    public String getDisplayName(Server server) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getDisplayName'");
    }

    @Override
    public Optional<String> getNickname(Server server) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getNickname'");
    }

    @Override
    public Optional<Instant> getServerBoostingSinceTimestamp(Server server) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getServerBoostingSinceTimestamp'");
    }

    @Override
    public Optional<Instant> getTimeout(Server server) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getTimeout'");
    }

    @Override
    public boolean isPending(Server server) {
        
        throw new UnsupportedOperationException("Unimplemented method 'isPending'");
    }

    @Override
    public boolean isSelfMuted(Server server) {
        
        throw new UnsupportedOperationException("Unimplemented method 'isSelfMuted'");
    }

    @Override
    public boolean isSelfDeafened(Server server) {
        
        throw new UnsupportedOperationException("Unimplemented method 'isSelfDeafened'");
    }

    @Override
    public Optional<Instant> getJoinedAtTimestamp(Server server) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getJoinedAtTimestamp'");
    }

    @Override
    public List<Role> getRoles(Server server) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getRoles'");
    }

    @Override
    public Optional<Color> getRoleColor(Server server) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getRoleColor'");
    }

    @Override
    public Optional<PrivateChannel> getPrivateChannel() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getPrivateChannel'");
    }

    @Override
    public CompletableFuture<PrivateChannel> openPrivateChannel() {
        
        throw new UnsupportedOperationException("Unimplemented method 'openPrivateChannel'");
    }
    
}
