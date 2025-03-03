package net.tomatentum.marinara.interaction.commands;

import net.tomatentum.marinara.interaction.commands.annotation.SlashCommandOption;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommandGroup;

public class CommandConverter<A extends Object> {

    private Spec<A, ?, ?> spec;
    
    public A convert(SlashCommandDefinition def) {
        return null;
    }

    public static interface Spec<A extends Object, O extends Object, C extends Object> {

        public A convertCommand(SlashCommandDefinition def);
        public O convertSubCommand(SubCommand def, O[] options);
        public O convertSubCommandGroup(SubCommandGroup def, O[] options);
        public O convertOption(SlashCommandOption option, C[] choices);
        public C[] convertChoice(SlashCommandOption option);
    }
}
