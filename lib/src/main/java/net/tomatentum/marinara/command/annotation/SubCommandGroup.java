package net.tomatentum.marinara.command.annotation;

public @interface SubCommandGroup {
    public String name();
    public String description() default "";
}
