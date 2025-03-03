package net.tomatentum.marinara.wrapper.discord4j;

import java.util.List;

import discord4j.core.object.command.ApplicationCommandOption.Type;
import discord4j.discordjson.json.ApplicationCommandOptionChoiceData;
import discord4j.discordjson.json.ApplicationCommandOptionData;
import discord4j.discordjson.json.ApplicationCommandRequest;
import net.tomatentum.marinara.interaction.commands.CommandConverter;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommandOption;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommandOptionChoice;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.interaction.ident.RootCommandIdentifier;

public class Discord4JConverterSpec implements CommandConverter.Spec<ApplicationCommandRequest, ApplicationCommandOptionData, ApplicationCommandOptionChoiceData> {

	@Override
	public ApplicationCommandRequest convertCommand(RootCommandIdentifier rootIdentifier,
			List<ApplicationCommandOptionData> options) {
        return ApplicationCommandRequest.builder()
            .name(rootIdentifier.name())
            .description(rootIdentifier.description())
            .options(options)
            .build();
	}

	@Override
	public ApplicationCommandOptionData convertSubCommandGroup(InteractionIdentifier identifier,
			List<ApplicationCommandOptionData> subCommands) {
		return ApplicationCommandOptionData.builder()
			.type(Type.SUB_COMMAND_GROUP.getValue())
			.name(identifier.name())
			.description(identifier.description())
			.options(subCommands)
			.build();
	}

	@Override
	public ApplicationCommandOptionData convertSubCommand(InteractionIdentifier identifier,
			List<ApplicationCommandOptionData> options) {
        return ApplicationCommandOptionData.builder()
            .type(Type.SUB_COMMAND_GROUP.getValue())
            .name(identifier.name())
            .description(identifier.description())
            .options(options)
            .build();
	}

	@Override
	public ApplicationCommandOptionData convertOption(SlashCommandOption option,
			List<ApplicationCommandOptionChoiceData> choices) {
		Type type = Type.of(option.type().getValue());
        return ApplicationCommandOptionData.builder()
            .type(type.getValue())
            .name(option.name())
            .description(option.description())
            .required(option.required())
            .autocomplete(option.autocomplete())
            .choices(choices)
            .build();
	}

	@Override
	public ApplicationCommandOptionChoiceData convertChoice(SlashCommandOptionChoice choice) {
		var builder = ApplicationCommandOptionChoiceData.builder().name(choice.name());
		if (choice.longValue() != Long.MAX_VALUE)
			builder.value(choice.longValue());
		if (choice.doubleValue() != Double.MAX_VALUE)
			builder.value(choice.doubleValue());
		if (!choice.stringValue().isEmpty())
			builder.value(choice.stringValue());
		return builder.build();
	}
	
}
