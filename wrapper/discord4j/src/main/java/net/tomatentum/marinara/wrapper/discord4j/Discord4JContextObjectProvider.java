package net.tomatentum.marinara.wrapper.discord4j;

import java.util.List;

import discord4j.core.event.domain.interaction.ChatInputAutoCompleteEvent;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.event.domain.interaction.ComponentInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import net.tomatentum.marinara.interaction.commands.option.SlashCommandOptionType;
import net.tomatentum.marinara.wrapper.ContextObjectProvider;

public class Discord4JContextObjectProvider implements ContextObjectProvider {

    @Override
    public Object convertCommandOption(Object context, String optionName) {
        if (!(context instanceof ChatInputInteractionEvent))
            return null;
        ChatInputInteractionEvent interactionEvent = (ChatInputInteractionEvent) context;

        List<ApplicationCommandInteractionOption> subOptions = Discord4JWrapper.SUB_FILTER.apply(interactionEvent.getOptions());

        if (subOptions.isEmpty())
            return getOptionValue(interactionEvent.getOption(optionName).get());


        ApplicationCommandInteractionOption subCommandOption = interactionEvent.getOptions().getFirst();
        subOptions = Discord4JWrapper.SUB_FILTER.apply(subCommandOption.getOptions());

        if (!subOptions.isEmpty())
            subCommandOption = subOptions.getFirst();

        return getOptionValue(interactionEvent.getOption(optionName).get());

    }

    private Object getOptionValue(ApplicationCommandInteractionOption option) {
        if (!option.getValue().isPresent())
            return null;
        SlashCommandOptionType type = getOptionType(option);

        switch (type) {
            case ATTACHMENT:
                return option.getValue().get().asAttachment();
            case BOOLEAN:
                return option.getValue().get().asBoolean();
            case CHANNEL:
                return option.getValue().get().asChannel();
            case DOUBLE:
                return option.getValue().get().asDouble();
            case INTEGER:
                return option.getValue().get().asLong();
            case MENTIONABLE:
                return option.getValue().get().asSnowflake();
            case ROLE:
                return option.getValue().get().asRole();
            case STRING:
                return option.getValue().get().asString();
            case USER:
                return option.getValue().get().asUser();
            default:
                return null;
        }
    }

    private SlashCommandOptionType getOptionType(ApplicationCommandInteractionOption option) {
        return SlashCommandOptionType.fromValue(option.getType().getValue());
    }

    @Override
    public Object getComponentContextObject(Object context, Class<?> type) {
        ComponentInteractionEvent componentInteractionEvent = (ComponentInteractionEvent) context;
        switch (type.getName()) {
            case "discord4j.core.object.entity.Message":
                return componentInteractionEvent.getMessage().orElse(null);
            default:
                return getInteractionContextObject(context, type);
        }
    }

    @Override
    public Object getInteractionContextObject(Object context, Class<?> type) {
        ComponentInteractionEvent componentInteractionEvent = (ComponentInteractionEvent) context;
        switch (type.getName()) {
            case "discord4j.core.object.entity.channel.MessageChannel":
                return componentInteractionEvent.getInteraction().getChannel().block();
            case "discord4j.core.object.entity.Guild":
                return componentInteractionEvent.getInteraction().getGuild().block();
            case "discord4j.core.object.entity.Member":
                return componentInteractionEvent.getInteraction().getMember().orElse(null);
            case "discord4j.core.object.entity.User":
                return componentInteractionEvent.getInteraction().getUser();
        }
        return null;
    }

    @Override
    public Object getAutocompleteFocusedOption(Object context) {
        ChatInputAutoCompleteEvent interaction = (ChatInputAutoCompleteEvent) context;
        return getOptionValue(interaction.getFocusedOption());
    }

}
