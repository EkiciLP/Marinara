package net.tomatentum.marinara.wrapper.discord4j.identifierconverter;

import discord4j.core.event.domain.interaction.ButtonInteractionEvent;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.wrapper.IdentifierProvider;

public class ButtonIdentifierConverter implements IdentifierProvider.Converter<ButtonInteractionEvent> {

    @Override
    public InteractionIdentifier convert(ButtonInteractionEvent context) {
        return InteractionIdentifier.builder().name(context.getCustomId()).type(InteractionType.BUTTON).build();
    }
    
}
