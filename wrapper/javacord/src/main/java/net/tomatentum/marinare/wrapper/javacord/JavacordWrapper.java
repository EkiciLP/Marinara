package net.tomatentum.marinare.wrapper.javacord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.javacord.api.DiscordApi;
import org.javacord.api.interaction.SlashCommandBuilder;

import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.commands.ExecutableSlashCommandDefinition;
import net.tomatentum.marinara.interaction.commands.SlashCommandDefinition;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommandOption;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommandGroup;
import net.tomatentum.marinara.interaction.commands.option.SlashCommandOptionType;
import net.tomatentum.marinara.wrapper.LibraryWrapper;

public class JavacordWrapper extends LibraryWrapper {

    private DiscordApi api;

    public JavacordWrapper(DiscordApi api) {
        this.api = api;
    }

    @Override
    public void registerSlashCommands(SlashCommandDefinition[] defs) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registerSlashCommands'");
    }

    @Override
    public InteractionType getInteractionType(Class<?> clazz) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInteractionType'");
    }

    @Override
    public Object convertCommandOption(Object context, SlashCommandOptionType type, String optionName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertCommandOption'");
    }

    @Override
    public ExecutableSlashCommandDefinition getCommandDefinition(Object context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCommandDefinition'");
    }

    private SlashCommandBuilder convertSlashCommand(SlashCommandDefinition def) {
        List<org.javacord.api.interaction.SlashCommandOption> options = new ArrayList<>();
        SlashCommand cmd = def.getFullSlashCommand();
        if (!def.isRootCommand()) {
            Arrays.stream(def.getSubCommands(null)).map(this::convertSubCommandDef).forEach(options::add);
            Arrays.stream(def.getSubCommandGroups()).map((x) -> convertSubCommandGroupDef(def, x)).forEach(options::add);
        }else {
            Arrays.stream(cmd.options()).map(this::convertOptionDef).forEach(options::add);
        }

        return org.javacord.api.interaction.SlashCommand.with(cmd.name(), cmd.description(), options);
    }

    private org.javacord.api.interaction.SlashCommandOption convertSubCommandGroupDef(SlashCommandDefinition def, SubCommandGroup subGroup) {
        SubCommand[] subCommands = def.getSubCommands(subGroup.name());
        org.javacord.api.interaction.SlashCommandOption[] convertedSubCommands = (org.javacord.api.interaction.SlashCommandOption[]) Arrays.stream(subCommands).map(this::convertSubCommandDef).toArray();
        return org.javacord.api.interaction.SlashCommandOption.createWithOptions(org.javacord.api.interaction.SlashCommandOptionType.SUB_COMMAND_GROUP, subGroup.name(), subGroup.description(), Arrays.asList(convertedSubCommands));
    }

    private org.javacord.api.interaction.SlashCommandOption convertSubCommandDef(SubCommand sub) {
        List<org.javacord.api.interaction.SlashCommandOption> convertedOptions = new ArrayList<>();
        Arrays.stream(sub.options()).map(this::convertOptionDef).forEach(convertedOptions::add);
        return org.javacord.api.interaction.SlashCommandOption.createWithOptions(org.javacord.api.interaction.SlashCommandOptionType.SUB_COMMAND, sub.name(), sub.description(), convertedOptions);
    }

    private org.javacord.api.interaction.SlashCommandOption convertOptionDef(SlashCommandOption option) {
        org.javacord.api.interaction.SlashCommandOptionType type = Enum.valueOf(org.javacord.api.interaction.SlashCommandOptionType.class, option.type().toString());
        return org.javacord.api.interaction.SlashCommandOption.create(type, option.name(), option.description(), option.required());
    }
    
}
