package net.tomatentum.marinara.interaction.commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.leangen.geantyref.AnnotationFormatException;
import io.leangen.geantyref.GenericTypeReflector;
import io.leangen.geantyref.TypeFactory;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommandOptionChoice;

public record EnumChoices(Class<? extends Enum<?>> enumClass, ChoiceType type, SlashCommandOptionChoice[] choices) {

    private static Method method;

    static {
        try {
            method = ChoiceValueProvider.class.getMethod("getChoiceValue");
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
    }

    public static EnumChoices of(Class<? extends Enum<?>> enumClass) {
        if (!ChoiceValueProvider.class.isAssignableFrom(enumClass))
            throw new IllegalArgumentException("Provided class needs to implement the ChoiceValueProvider interface.");
        ChoiceType type = parseChoiceType(enumClass);
        SlashCommandOptionChoice[] choices = parseChoices(enumClass, type);
        return new EnumChoices(enumClass, type, choices);
    }

    private static ChoiceType parseChoiceType(Class<? extends Enum<?>> enumClass) {
        ParameterizedType type = (ParameterizedType) GenericTypeReflector.getExactSuperType(enumClass, ChoiceValueProvider.class);
        Type typeParam = type.getActualTypeArguments()[0];

        if (!(typeParam instanceof Class<?>))
            throw new IllegalArgumentException("ChoiceValueProvider need either a String or Number type parameter.");

        if (Long.class.isAssignableFrom((Class<?>) typeParam))
            return ChoiceType.INTEGER;
        if (Double.class.isAssignableFrom((Class<?>) typeParam))
            return ChoiceType.DOUBLE;
        if (String.class.isAssignableFrom((Class<?>) typeParam))
            return ChoiceType.String;
        throw new IllegalArgumentException("ChoiceValueProvider need either a String, Number or Decimal type parameter.");
    }

    private static SlashCommandOptionChoice[] parseChoices(Class<? extends Enum<?>> enumClass, ChoiceType type) {
        Enum<? extends Enum<?>>[] constants = enumClass.getEnumConstants();
        List<SlashCommandOptionChoice> choices = new ArrayList<>();
        for (Enum<? extends Enum<?>> enumInstance : constants) {
            Object value;
            try {
                value = method.invoke(enumInstance);
                if (type.equals(ChoiceType.INTEGER))
                    choices.add(TypeFactory.annotation(SlashCommandOptionChoice.class, Map.of("name", enumInstance.name(), "longValue", value)));
                if (type.equals(ChoiceType.DOUBLE))
                    choices.add(TypeFactory.annotation(SlashCommandOptionChoice.class, Map.of("name", enumInstance.name(), "doubleValue", value)));
                if (type.equals(ChoiceType.String))
                    choices.add(TypeFactory.annotation(SlashCommandOptionChoice.class, Map.of("name", enumInstance.name(), "stringValue", value)));
            } catch (IllegalAccessException | InvocationTargetException | AnnotationFormatException e) {
                e.printStackTrace();
                return null;
            }
        }
        return choices.toArray(new SlashCommandOptionChoice[0]);
    }

    public static enum ChoiceType {
        String,
        INTEGER,
        DOUBLE
    }
}
