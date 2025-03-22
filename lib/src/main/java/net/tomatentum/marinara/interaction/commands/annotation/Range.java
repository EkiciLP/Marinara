package net.tomatentum.marinara.interaction.commands.annotation;

public @interface Range {
    public double min() default Double.MIN_VALUE;
    public double max() default Double.MAX_VALUE;
}
