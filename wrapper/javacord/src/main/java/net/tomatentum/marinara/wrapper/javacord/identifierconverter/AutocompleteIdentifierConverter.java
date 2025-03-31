package net.tomatentum.marinara.wrapper.javacord.identifierconverter;

import java.util.List;
import java.util.Optional;

import org.javacord.api.interaction.AutocompleteInteraction;
import org.javacord.api.interaction.SlashCommandInteractionOption;

import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.interaction.ident.SlashCommandIdentifier;
import net.tomatentum.marinara.registry.InteractionEntry;
import net.tomatentum.marinara.registry.InteractionRegistry;
import net.tomatentum.marinara.wrapper.IdentifierProvider;

public class AutocompleteIdentifierConverter implements IdentifierProvider.Converter<AutocompleteInteraction> {

    @Override
    public InteractionIdentifier convert(AutocompleteInteraction context, InteractionRegistry registry) {
        List<SlashCommandInteractionOption> options = context.getOptions();
        String commandName = context.getCommandName();
        InteractionIdentifier ident;

        if (!options.isEmpty()) {
            List<SlashCommandInteractionOption> sub_options = context.getOptions().getFirst().getOptions();
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
