package net.tomatentum.marinara.interaction.commands.annotation;

import net.tomatentum.marinara.interaction.commands.annotation.SlashCommandOption.PlaceHolderEnum;

public @interface CommandChoices {
    public SlashCommandOptionChoice[] value() default {};
    public Class<? extends Enum<?>> cenum() default PlaceHolderEnum.class;
}
