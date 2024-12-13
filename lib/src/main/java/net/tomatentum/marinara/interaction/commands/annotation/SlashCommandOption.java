package net.tomatentum.marinara.interaction.commands.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.tomatentum.marinara.interaction.commands.option.SlashCommandOptionType;

@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SlashCommandOption {
    public String name();
    public String description() default "";
    public SlashCommandOptionType type() default SlashCommandOptionType.STRING;
    public boolean required() default false;
    public SlashCommandOptionChoice[] choices() default {};
    public Class<? extends Enum<?>> choiceEnum() default PlaceHolderEnum.class;

    public static enum PlaceHolderEnum {

    }
}
