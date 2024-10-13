package net.tomatentum.marinara.command.annotation;

public @interface SubCommand {
    public String name();
    public String description() default "";
    public CommandOption[] options() default {};
}
