package net.tomatentum.marinara.interaction.commands.annotation;

public @interface SlashCommandOptionChoice {
    public String name();
    public long longValue() default Long.MAX_VALUE;
    public double doubleValue() default Double.MAX_VALUE;
    public String stringValue() default "";
}
