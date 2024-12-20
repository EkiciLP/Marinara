package net.tomatentum.marinara.wrapper.javacord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.interaction.ApplicationCommandInteraction;
import org.javacord.api.interaction.AutocompleteInteraction;
import org.javacord.api.interaction.ButtonInteraction;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOptionBuilder;
import org.javacord.api.interaction.SlashCommandOptionChoiceBuilder;
import org.javacord.api.interaction.SlashCommandOptionType;

import io.leangen.geantyref.AnnotationFormatException;
import io.leangen.geantyref.TypeFactory;
import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.commands.ExecutableSlashCommandDefinition;
import net.tomatentum.marinara.interaction.commands.SlashCommandDefinition;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommandOption;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommandOptionChoice;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommand;
import net.tomatentum.marinara.interaction.commands.annotation.SubCommandGroup;
import net.tomatentum.marinara.wrapper.ContextObjectProvider;
import net.tomatentum.marinara.util.LoggerUtil;
import net.tomatentum.marinara.wrapper.LibraryWrapper;

public class JavacordWrapper extends LibraryWrapper {

    private DiscordApi api;
    private JavacordContextObjectProvider contextObjectProvider;
    
    private Logger logger = LoggerUtil.getLogger(getClass());

    public JavacordWrapper(DiscordApi api) {
        this.api = api;
        this.contextObjectProvider = new JavacordContextObjectProvider();
        api.addInteractionCreateListener((e) -> handleInteraction(e.getInteraction()));
        logger.info("Javacord wrapper loaded!");
    }

    @Override
    public InteractionType getInteractionType(Class<?> clazz) {
        if (AutocompleteInteraction.class.isAssignableFrom(clazz))
            return InteractionType.AUTOCOMPLETE;
        if (ApplicationCommandInteraction.class.isAssignableFrom(clazz))
            return InteractionType.COMMAND;
        if (ButtonInteraction.class.isAssignableFrom(clazz))
            return InteractionType.BUTTON;
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
    public ExecutableSlashCommandDefinition getCommandDefinition(Object context) {
        if (!(context instanceof SlashCommandInteraction))
            return null;

        SlashCommandInteraction interaction = (SlashCommandInteraction) context;
        ExecutableSlashCommandDefinition.Builder builder = new ExecutableSlashCommandDefinition.Builder();
        List<SlashCommandInteractionOption> options = interaction.getOptions();
        try {
            builder.setApplicationCommand(TypeFactory.annotation(SlashCommand.class, Map.of("name", interaction.getCommandName())));
            if (!options.isEmpty()) {
                if (!options.getFirst().getArguments().isEmpty()) {
                    builder.setSubCommandGroup(TypeFactory.annotation(SubCommandGroup.class, Map.of("name", options.getFirst().getName())));
                    builder.setSubCommand(TypeFactory.annotation(SubCommand.class, Map.of("name", options.getFirst().getOptions().getFirst().getName())));
                }else
                    builder.setSubCommand(TypeFactory.annotation(SubCommand.class, Map.of("name", options.getFirst().getName())));
            }
        } catch (AnnotationFormatException e) {
            logger.fatal(e);
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
        return org.javacord.api.interaction.SlashCommandOption.createWithOptions(
            org.javacord.api.interaction.SlashCommandOptionType.SUB_COMMAND_GROUP, 
            subGroup.name(), 
            subGroup.description(), 
            Arrays.asList(convertedSubCommands));
    }

    private org.javacord.api.interaction.SlashCommandOption convertSubCommandDef(SubCommand sub) {
        List<org.javacord.api.interaction.SlashCommandOption> convertedOptions = new ArrayList<>();
        Arrays.stream(sub.options()).map(this::convertOptionDef).forEach(convertedOptions::add);
        return org.javacord.api.interaction.SlashCommandOption.createWithOptions(
            org.javacord.api.interaction.SlashCommandOptionType.SUB_COMMAND, 
            sub.name(), 
            sub.description(), 
            convertedOptions);
    }

    private org.javacord.api.interaction.SlashCommandOption convertOptionDef(SlashCommandOption option) {
        SlashCommandOptionType type = SlashCommandOptionType.fromValue(option.type().getValue());
        SlashCommandOptionBuilder builder = new SlashCommandOptionBuilder();
        builder
            .setType(type)
            .setName(option.name())
            .setDescription(option.description())
            .setRequired(option.required())
            .setAutocompletable(option.autocomplete())
            .setChoices(convertChoices(option));
        
        return builder.build();
    }

    private List<org.javacord.api.interaction.SlashCommandOptionChoice> convertChoices(SlashCommandOption option) {
        List<org.javacord.api.interaction.SlashCommandOptionChoice> convertedChoices = new ArrayList<>();
        for (SlashCommandOptionChoice choice : ExecutableSlashCommandDefinition.getActualChoices(option)) {
            SlashCommandOptionChoiceBuilder builder = new SlashCommandOptionChoiceBuilder();
            builder.setName(choice.name());
            if (choice.longValue() != Long.MAX_VALUE)
                builder.setValue(choice.longValue());
            /*
            not yet available
            if (choice.doubleValue() != Double.MAX_VALUE)
                builder.setValue(choice.doubleValue());
            */
            if (!choice.stringValue().isEmpty())
                builder.setValue(choice.stringValue());
        }
        return convertedChoices;
    }

    @Override
    public String getButtonId(Object context) {
        ButtonInteraction button = (ButtonInteraction) context;
        return button.getCustomId();
    }

    @Override
    public ContextObjectProvider getContextObjectProvider() {
        return contextObjectProvider;
    }

}
