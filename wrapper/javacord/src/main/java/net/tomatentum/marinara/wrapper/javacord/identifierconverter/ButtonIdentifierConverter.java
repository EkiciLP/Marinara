package net.tomatentum.marinara.wrapper.javacord.identifierconverter;

import org.javacord.api.interaction.ButtonInteraction;

import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.wrapper.IdentifierProvider;

public class ButtonIdentifierConverter implements IdentifierProvider.Converter<ButtonInteraction> {

    @Override
    public InteractionIdentifier convert(ButtonInteraction context) {
        return InteractionIdentifier.builder().name(context.getCustomId()).type(InteractionType.BUTTON).build();
    }
    
}
