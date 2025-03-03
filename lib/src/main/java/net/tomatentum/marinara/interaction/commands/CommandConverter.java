package net.tomatentum.marinara.interaction.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.tomatentum.marinara.interaction.commands.annotation.SlashCommandOption;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommandOptionChoice;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.interaction.ident.RootCommandIdentifier;
import net.tomatentum.marinara.interaction.ident.SlashCommandIdentifier;

public class CommandConverter<A extends Object, O extends Object, C extends Object> {

    public static <A, O, C> CommandConverter<A, O, C> of(Spec<A, O, C> spec) {
        return new CommandConverter<>(spec);
    }

    private Spec<A, O, C> spec;

    CommandConverter(Spec<A, O, C> spec) {
        this.spec = spec;
    }
    
    public A convert(SlashCommandDefinition def) {
        List<O> options = new ArrayList<>();
        if (!def.isRootCommand()) {
            Arrays.stream(def.getSubCommands()).map(this::convertSubCommand).forEach(options::add);
            Arrays.stream(def.getSubCommandGroups()).map(x -> this.convertSubCommandGroup(def, x)).forEach(options::add);
        }else
            Arrays.stream(def.rootIdentifier().options()).map(this::convertOption).forEach(options::add);

        return spec.convertCommand(def.rootIdentifier(), options);
    }

    private O convertSubCommandGroup(SlashCommandDefinition def, InteractionIdentifier identifier) {
        SlashCommandIdentifier[] subCommands = def.getSubCommands(identifier.name());
        List<O> convertedSubCommands = Arrays.stream(subCommands).map(this::convertSubCommand).toList();
        return spec.convertSubCommandGroup(identifier, convertedSubCommands);
    }

    private O convertSubCommand(SlashCommandIdentifier identifier) {
        List<O> options = Arrays.stream(identifier.options()).map(this::convertOption).toList();
        return spec.convertSubCommand(identifier, options);
    }

    private O convertOption(SlashCommandOption option) {
        List<C> choices = Arrays.stream(SlashCommandDefinition.getActualChoices(option)).map(spec::convertChoice).toList();
        return spec.convertOption(option, choices);
    }

    public static interface Spec<A extends Object, O extends Object, C extends Object> {

        public A convertCommand(RootCommandIdentifier rootIdentifier, List<O>  options);
        public O convertSubCommandGroup(InteractionIdentifier identifier, List<O> subCommands);
        public O convertSubCommand(InteractionIdentifier identifier, List<O>  options);
        public O convertOption(SlashCommandOption option, List<C> choices);
        public C convertChoice(SlashCommandOptionChoice choice);
    }
}
