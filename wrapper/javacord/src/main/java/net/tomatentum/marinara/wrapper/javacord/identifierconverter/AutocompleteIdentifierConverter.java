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
        InteractionIdentifier last = InteractionIdentifier.builder()
            .type(InteractionType.AUTOCOMPLETE)
            .name(context.getCommandName())
            .build();

        List<SlashCommandInteractionOption> options = context.getOptions();
        if (!options.isEmpty()) {
            last = InteractionIdentifier.builder()
                .type(InteractionType.AUTOCOMPLETE)
                .name(options.getFirst().getName())
                .parent(last)
                .build();

            List<SlashCommandInteractionOption> subOptions = context.getOptions().getFirst().getOptions();
            if (!subOptions.isEmpty())
                last = InteractionIdentifier.builder()
                    .type(InteractionType.AUTOCOMPLETE)
                    .name(subOptions.getFirst().getName())
                    .parent(last)
                    .build();
        }
        return last;
    }
    
}
