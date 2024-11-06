package net.tomatentum.marinare.wrapper.javacord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.javacord.api.DiscordApi;
import org.javacord.api.interaction.ApplicationCommandInteraction;
import org.javacord.api.interaction.ButtonInteraction;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandInteractionOption;

import io.leangen.geantyref.AnnotationFormatException;
import io.leangen.geantyref.TypeFactory;
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
        api.addInteractionCreateListener((e) -> handleInteraction(e.getInteraction()));
    }

    @Override
    public InteractionType getInteractionType(Class<?> clazz) {
        if (ApplicationCommandInteraction.class.isAssignableFrom(clazz))
            return InteractionType.COMMAND;

        return null;
    }

    @Override
    public void registerSlashCommands(SlashCommandDefinition[] defs) {
        HashMap<Long, Set<SlashCommandBuilder>> serverCommands = new HashMap<>();
        Set<SlashCommandBuilder> globalCommands = new HashSet<>();
        for (SlashCommandDefinition slashCommandDefinition : defs) {
            SlashCommandBuilder builder = convertSlashCommand(slashCommandDefinition);
            if (slashCommandDefinition.getFullSlashCommand().serverIds().length > 0) {
                for (long serverId : slashCommandDefinition.getFullSlashCommand().serverIds()) {
                    serverCommands.putIfAbsent(serverId, new HashSet<>());
                    serverCommands.get(serverId).add(builder);
                }
            }else
                globalCommands.add(builder);
        }

        for (long serverId : serverCommands.keySet()) {
            api.bulkOverwriteServerApplicationCommands(serverId, serverCommands.get(serverId));
        }
        api.bulkOverwriteGlobalApplicationCommands(globalCommands);
    }

    @Override
    public Object convertCommandOption(Object context, SlashCommandOptionType type, String optionName) {
        if (!(context instanceof SlashCommandInteraction))
            return null;
        SlashCommandInteraction interaction = (SlashCommandInteraction) context;
        if (!interaction.getArguments().isEmpty())
            return getOptionValue(interaction.getOptionByName(optionName).get(), type);

        SlashCommandInteractionOption subCommandOption = interaction.getOptions().getFirst();

        if (!subCommandOption.getOptions().isEmpty())
            subCommandOption = subCommandOption.getOptions().getFirst();

        return getOptionValue(subCommandOption.getOptionByName(optionName).get(), type);
    }

    @Override
    public ExecutableSlashCommandDefinition getCommandDefinition(Object context) {
        if (!(context instanceof SlashCommandInteraction))
            return null;

        SlashCommandInteraction interaction = (SlashCommandInteraction) context;
        ExecutableSlashCommandDefinition.Builder builder = new ExecutableSlashCommandDefinition.Builder();
        List<SlashCommandInteractionOption> options = interaction.getOptions();
        try {
            builder.setApplicationCommand(TypeFactory.annotation(SlashCommand.class, Map.of("name", interaction.getCommandName())));
            if (!options.getFirst().getArguments().isEmpty()) {
                builder.setSubCommandGroup(TypeFactory.annotation(SubCommandGroup.class, Map.of("name", options.getFirst().getName())));
                builder.setSubCommand(TypeFactory.annotation(SubCommand.class, Map.of("name", options.getFirst().getOptions().getFirst().getName())));
            }else
                builder.setSubCommand(TypeFactory.annotation(SubCommand.class, Map.of("name", options.getFirst().getName())));
        } catch (AnnotationFormatException e) {
            e.printStackTrace();
        }

        return builder.build();
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

    private Object getOptionValue(SlashCommandInteractionOption option, SlashCommandOptionType type) {
        switch (type) {
            case ATTACHMENT:
                return option.getAttachmentValue().get();
            case BOOLEAN:
                return option.getBooleanValue().get();
            case CHANNEL:
                return option.getChannelValue().get();
            case DECIMAL:
                return option.getDecimalValue().get();
            case LONG:
                return option.getLongValue().get();
            case MENTIONABLE:
                return option.getMentionableValue().get();
            case ROLE:
                return option.getRoleValue().get();
            case STRING:
                return option.getStringValue().get();
            case USER:
                return option.getUserValue().get();
            default:
                return null;
        }
    }

    @Override
    public String getButtonId(Object context) {
        ButtonInteraction button = (ButtonInteraction) context;
        return button.getCustomId();
    }

    @Override
    public Object getComponentContextObject(Object context, Class<?> type) {
        ButtonInteraction button = (ButtonInteraction) context;
        switch (type.getName()) {
            case "TextChannel":
                return button.getChannel().orElse(null);
            case "Message":
                return button.getMessage();
            case "Server":
                return button.getServer().orElse(null);
            case "User":
                return button.getUser();
            case "Member":
                return button.getUser();
        }
        return null;
    }

    
}
