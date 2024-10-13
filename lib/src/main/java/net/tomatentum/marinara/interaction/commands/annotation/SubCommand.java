package net.tomatentum.marinara.interaction.commands.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SubCommand {
    public String name();
    public String description() default "";
    public CommandOption[] options() default {};
}
