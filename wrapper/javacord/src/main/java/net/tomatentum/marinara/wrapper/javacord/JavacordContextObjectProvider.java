package net.tomatentum.marinara.wrapper.javacord;

import org.javacord.api.interaction.AutocompleteInteraction;
import org.javacord.api.interaction.ButtonInteraction;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOptionType;

import net.tomatentum.marinara.wrapper.ContextObjectProvider;

public class JavacordContextObjectProvider implements ContextObjectProvider {

    @Override
    public Object convertCommandOption(Object context, String optionName) {
        if (!(context instanceof SlashCommandInteraction))
            return null;
        SlashCommandInteraction interaction = (SlashCommandInteraction) context;
        if (!interaction.getArguments().isEmpty())
            return getOptionValue(interaction.getOptionByName(optionName).get());

        SlashCommandInteractionOption subCommandOption = interaction.getOptions().getFirst();

        if (!subCommandOption.getOptions().isEmpty())
            subCommandOption = subCommandOption.getOptions().getFirst();

        return getOptionValue(subCommandOption.getOptionByName(optionName).get());
    }

    private Object getOptionValue(SlashCommandInteractionOption option) {
        SlashCommandOptionType type = getOptionType(option);
        switch (type) {
            case ATTACHMENT:
                return option.getAttachmentValue().get();
            case BOOLEAN:
                return option.getBooleanValue().get();
            case CHANNEL:
                return option.getChannelValue().get();
            case DECIMAL:
                return option.getDecimalValue().get();
            case LONG:
                return option.getLongValue().get();
            case MENTIONABLE:
                return option.requestMentionableValue().get();
            case ROLE:
                return option.getRoleValue().get();
            case STRING:
                return option.getStringValue().get();
            case USER:
                return option.requestUserValue().get();
            default:
                return null;
        }
    }


    private SlashCommandOptionType getOptionType(SlashCommandInteractionOption option) {
        if (option.getAttachmentValue().isPresent())
            return SlashCommandOptionType.ATTACHMENT;
        if (option.getBooleanValue().isPresent())
            return SlashCommandOptionType.BOOLEAN;
        if (option.getChannelValue().isPresent())
            return SlashCommandOptionType.CHANNEL;
        if (option.getDecimalValue().isPresent())
            return SlashCommandOptionType.DECIMAL;
        if (option.getLongValue().isPresent())
            return SlashCommandOptionType.LONG;
        if (option.requestMentionableValue().isPresent())
            return SlashCommandOptionType.MENTIONABLE;
        if (option.getRoleValue().isPresent())
            return SlashCommandOptionType.ROLE;
        if (option.getStringValue().isPresent())
            return SlashCommandOptionType.STRING;
        if (option.requestUserValue().isPresent())
            return SlashCommandOptionType.USER;

        return SlashCommandOptionType.UNKNOWN;
    }

    @Override
    public Object getComponentContextObject(Object context, Class<?> type) {
        ButtonInteraction button = (ButtonInteraction) context;
        switch (type.getName()) {
            case "org.javacord.api.entity.message.Message":
                return button.getMessage();
            default:
                return getInteractionContextObject(context, type);
        }
    }

    @Override
    public Object getInteractionContextObject(Object context, Class<?> type) {
        ButtonInteraction button = (ButtonInteraction) context;
        switch (type.getName()) {
            case "org.javacord.api.entity.channel.TextChannel":
                return button.getChannel().orElse(null);
            case "org.javacord.api.entity.server.Server":
                return button.getServer().orElse(null);
            case "org.javacord.api.entity.user.User":
                return button.getUser();
        }
        return null;
    }

    @Override
    public Object getAutocompleteFocusedOption(Object context) {
        AutocompleteInteraction interaction = (AutocompleteInteraction) context;
        return getOptionValue(interaction.getFocusedOption());
    }

}
