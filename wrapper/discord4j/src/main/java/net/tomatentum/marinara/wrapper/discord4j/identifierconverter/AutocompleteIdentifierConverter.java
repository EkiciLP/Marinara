package net.tomatentum.marinara.wrapper.discord4j.identifierconverter;

import java.util.List;

import discord4j.core.event.domain.interaction.ChatInputAutoCompleteEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.wrapper.IdentifierProvider;
import net.tomatentum.marinara.wrapper.discord4j.Discord4JWrapper;

public class AutocompleteIdentifierConverter implements IdentifierProvider.Converter<ChatInputAutoCompleteEvent> {

    @Override
    public InteractionIdentifier convert(ChatInputAutoCompleteEvent context) {
        List<ApplicationCommandInteractionOption> options = Discord4JWrapper.SUB_FILTER.apply(context.getOptions());
        String commandName = context.getCommandName();

        if (!options.isEmpty()) {
            List<ApplicationCommandInteractionOption> subOptions = Discord4JWrapper.SUB_FILTER.apply(options.getFirst().getOptions());
            if (!subOptions.isEmpty())
                return InteractionIdentifier.createHierarchy(
                    InteractionType.AUTOCOMPLETE, 
                    commandName, 
                    options.getFirst().getName(),
                    subOptions.getFirst().getName());
            else
                return InteractionIdentifier.createHierarchy(
                    InteractionType.AUTOCOMPLETE, 
                    commandName, 
                    options.getFirst().getName());
        }else
            return InteractionIdentifier.createHierarchy(
                InteractionType.AUTOCOMPLETE, 
                commandName); 

    }
    
}
