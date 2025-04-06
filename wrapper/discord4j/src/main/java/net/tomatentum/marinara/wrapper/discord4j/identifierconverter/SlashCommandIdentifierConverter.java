package net.tomatentum.marinara.wrapper.discord4j.identifierconverter;

import java.util.List;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.wrapper.IdentifierProvider;
import net.tomatentum.marinara.wrapper.discord4j.Discord4JWrapper;

public class SlashCommandIdentifierConverter implements IdentifierProvider.Converter<ChatInputInteractionEvent> {

    @Override
    public InteractionIdentifier convert(ChatInputInteractionEvent context) {
        InteractionIdentifier last = InteractionIdentifier.builder()
            .type(InteractionType.COMMAND)
            .name(context.getCommandName())
            .build();

        List<ApplicationCommandInteractionOption> options = Discord4JWrapper.SUB_FILTER.apply(context.getOptions());
        if (!options.isEmpty()) {
            last = InteractionIdentifier.builder()
                .type(InteractionType.COMMAND)
                .name(options.getFirst().getName())
                .parent(last)
                .build();

            List<ApplicationCommandInteractionOption> subOptions = Discord4JWrapper.SUB_FILTER.apply(options.getFirst().getOptions());
            if (!subOptions.isEmpty())
                last = InteractionIdentifier.builder()
                    .type(InteractionType.COMMAND)
                    .name(subOptions.getFirst().getName())
                    .parent(last)
                    .build();
        }
        return last;
    }
    
}
