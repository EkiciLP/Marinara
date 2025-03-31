package net.tomatentum.marinara.wrapper.discord4j.identifierconverter;

import java.util.List;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.registry.InteractionRegistry;
import net.tomatentum.marinara.wrapper.IdentifierProvider;
import net.tomatentum.marinara.wrapper.discord4j.Discord4JWrapper;

public class SlashCommandIdentifierConverter implements IdentifierProvider.Converter<ChatInputInteractionEvent> {

    @Override
    public InteractionIdentifier convert(ChatInputInteractionEvent context, InteractionRegistry registry) {
        List<ApplicationCommandInteractionOption> options = Discord4JWrapper.SUB_FILTER.apply(context.getOptions());
        String commandName = context.getCommandName();

        if (!options.isEmpty()) {
            List<ApplicationCommandInteractionOption> sub_options = Discord4JWrapper.SUB_FILTER.apply(options.getFirst().getOptions());
            if (!sub_options.isEmpty())
                return InteractionIdentifier.createHierarchy(
                    InteractionType.COMMAND, 
                    commandName, 
                    options.getFirst().getName(),
                    sub_options.getFirst().getName());
            else
                return InteractionIdentifier.createHierarchy(
                    InteractionType.COMMAND, 
                    commandName, 
                    options.getFirst().getName());
        }else
            return InteractionIdentifier.createHierarchy(
                InteractionType.COMMAND, 
                commandName); 

    }
    
}
