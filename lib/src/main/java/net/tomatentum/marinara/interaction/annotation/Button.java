package net.tomatentum.marinara.interaction.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.tomatentum.marinara.structure.data.ButtonStructureData.ButtonStyle;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Button {
    public String value(); //aka customId
    public String label() default "default_button";
    public ButtonStyle style() default ButtonStyle.PRIMARY;
    public String url() default "";
    public boolean disabled() default false;
    public String emoji() default "";
}
