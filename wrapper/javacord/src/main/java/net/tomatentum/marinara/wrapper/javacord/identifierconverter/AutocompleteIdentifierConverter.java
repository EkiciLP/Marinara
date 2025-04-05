package net.tomatentum.marinara.wrapper.javacord.identifierconverter;

import java.util.List;

import org.javacord.api.interaction.AutocompleteInteraction;
import org.javacord.api.interaction.SlashCommandInteractionOption;

import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.wrapper.IdentifierProvider;

public class AutocompleteIdentifierConverter implements IdentifierProvider.Converter<AutocompleteInteraction> {

    @Override
    public InteractionIdentifier convert(AutocompleteInteraction context) {
        List<SlashCommandInteractionOption> options = context.getOptions();
        String commandName = context.getCommandName();

        if (!options.isEmpty()) {
            List<SlashCommandInteractionOption> subOptions = context.getOptions().getFirst().getOptions();
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
