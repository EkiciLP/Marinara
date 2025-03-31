package net.tomatentum.marinara.wrapper.discord4j.identifierconverter;

import java.util.List;
import java.util.Optional;

import discord4j.core.event.domain.interaction.ChatInputAutoCompleteEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.interaction.ident.SlashCommandIdentifier;
import net.tomatentum.marinara.registry.InteractionEntry;
import net.tomatentum.marinara.registry.InteractionRegistry;
import net.tomatentum.marinara.wrapper.IdentifierProvider;
import net.tomatentum.marinara.wrapper.discord4j.Discord4JWrapper;

public class AutocompleteIdentifierConverter implements IdentifierProvider.Converter<ChatInputAutoCompleteEvent> {

    @Override
    public InteractionIdentifier convert(ChatInputAutoCompleteEvent context, InteractionRegistry registry) {
        List<ApplicationCommandInteractionOption> options = Discord4JWrapper.SUB_FILTER.apply(context.getOptions());
        String commandName = context.getCommandName();
        InteractionIdentifier ident;

        if (!options.isEmpty()) {
            List<ApplicationCommandInteractionOption> sub_options = Discord4JWrapper.SUB_FILTER.apply(options.getFirst().getOptions());
            if (!sub_options.isEmpty())
                ident = InteractionIdentifier.createHierarchy(
                    InteractionType.COMMAND, 
                    commandName, 
                    options.getFirst().getName(),
                    sub_options.getFirst().getName());
            else
                ident = InteractionIdentifier.createHierarchy(
                    InteractionType.COMMAND, 
                    commandName, 
                    options.getFirst().getName());
        }else
            ident = InteractionIdentifier.createHierarchy(
                InteractionType.COMMAND, 
                commandName); 

        Optional<InteractionEntry> entry = registry.findFor(ident);
        if (entry.isPresent() && entry.get().identifier() instanceof SlashCommandIdentifier) {
            SlashCommandIdentifier sIdent = (SlashCommandIdentifier) entry.get().identifier();
            return InteractionIdentifier.builder()
                .type(InteractionType.AUTOCOMPLETE)
                .name(sIdent.autocompleteRef()[0])
                .build();
        }
        return null;
    }
    
}
