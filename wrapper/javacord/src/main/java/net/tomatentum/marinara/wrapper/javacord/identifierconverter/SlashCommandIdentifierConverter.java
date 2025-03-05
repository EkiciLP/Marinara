package net.tomatentum.marinara.wrapper.javacord.identifierconverter;

import java.util.List;

import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandInteractionOption;

import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.wrapper.IdentifierProvider;

public class SlashCommandIdentifierConverter implements IdentifierProvider.Converter<SlashCommandInteraction> {

    @Override
    public InteractionIdentifier convert(SlashCommandInteraction context) {
        List<SlashCommandInteractionOption> options = context.getOptions();
        String commandName = context.getCommandName();
        if (!options.isEmpty()) {
            List<SlashCommandInteractionOption> sub_options = context.getOptions().getFirst().getOptions();
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
