package net.tomatentum.marinara.wrapper.javacord;

import java.util.List;

import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionBuilder;
import org.javacord.api.interaction.SlashCommandOptionChoice;
import org.javacord.api.interaction.SlashCommandOptionChoiceBuilder;
import org.javacord.api.interaction.SlashCommandOptionType;

import net.tomatentum.marinara.interaction.commands.CommandConverter;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.interaction.ident.RootCommandIdentifier;

public class JavacordConverterSpec implements CommandConverter.Spec<SlashCommandBuilder, SlashCommandOption, SlashCommandOptionChoice> {

	@Override
	public SlashCommandBuilder convertCommand(RootCommandIdentifier rootIdentifier, List<SlashCommandOption> options) {
		return SlashCommand.with(rootIdentifier.name(), rootIdentifier.description(), options);
	}

	@Override
	public SlashCommandOption convertSubCommandGroup(InteractionIdentifier identifier,
			List<SlashCommandOption> subCommands) {
		return SlashCommandOption.createWithOptions(
			SlashCommandOptionType.SUB_COMMAND_GROUP, 
			identifier.name(), 
			identifier.description(), 
			subCommands);
	}

	@Override
	public SlashCommandOption convertSubCommand(InteractionIdentifier identifier, List<SlashCommandOption> options) {
		return SlashCommandOption.createWithOptions(
            SlashCommandOptionType.SUB_COMMAND, 
            identifier.name(), 
            identifier.description(), 
            options);
	}

	@Override
	public SlashCommandOption convertOption(
			net.tomatentum.marinara.interaction.commands.annotation.SlashCommandOption option,
			List<SlashCommandOptionChoice> choices) {
		SlashCommandOptionType type = SlashCommandOptionType.fromValue(option.type().getValue());
		return new SlashCommandOptionBuilder()
            .setType(type)
            .setName(option.name())
            .setDescription(option.description())
            .setRequired(option.required())
            .setAutocompletable(option.autocomplete())
            .setChoices(choices)
			.build();
	}

	@Override
	public SlashCommandOptionChoice convertChoice(
			net.tomatentum.marinara.interaction.commands.annotation.SlashCommandOptionChoice choice) {
		SlashCommandOptionChoiceBuilder builder = new SlashCommandOptionChoiceBuilder().setName(choice.name());
		if (choice.longValue() != Long.MAX_VALUE)
			builder.setValue(choice.longValue());
		/*
		not yet available
		if (choice.doubleValue() != Double.MAX_VALUE)
			builder.setValue(choice.doubleValue());
		*/
		if (!choice.stringValue().isEmpty())
			builder.setValue(choice.stringValue());
		return builder.build();
	}
}
