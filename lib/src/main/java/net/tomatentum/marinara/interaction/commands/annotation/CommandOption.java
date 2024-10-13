package net.tomatentum.marinara.interaction.commands.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.tomatentum.marinara.interaction.commands.option.OptionType;

@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandOption {
    public String name();
    public String description() default "";
    public OptionType type() default OptionType.STRING;
    public boolean required() default false;
}
