package net.tomatentum.marinara.command.annotation;

import net.tomatentum.marinara.command.option.OptionType;

public @interface CommandOption {
    public String name();
    public String description() default "";
    public OptionType type() default OptionType.STRING;
    public boolean required() default false;
    
}
