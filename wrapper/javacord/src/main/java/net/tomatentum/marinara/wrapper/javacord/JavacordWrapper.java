package net.tomatentum.marinara.wrapper.javacord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.interaction.AutocompleteInteraction;
import org.javacord.api.interaction.ButtonInteraction;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOptionBuilder;
import org.javacord.api.interaction.SlashCommandOptionChoiceBuilder;
import org.javacord.api.interaction.SlashCommandOptionType;

import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.commands.SlashCommandDefinition;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommandOption;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommandOptionChoice;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.interaction.ident.RootCommandIdentifier;
import net.tomatentum.marinara.interaction.ident.SlashCommandIdentifier;
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
        if (api != null)
            api.addInteractionCreateListener((e) -> handleInteraction(e.getInteraction()));
        else
            logger.warn("DiscordApi was null so no Events were subscribed to.");
        logger.info("Javacord wrapper loaded!");
    }

    @Override
    public void registerSlashCommands(SlashCommandDefinition[] defs) {
        HashMap<Long, Set<SlashCommandBuilder>> serverCommands = new HashMap<>();
        Set<SlashCommandBuilder> globalCommands = new HashSet<>();
        for (SlashCommandDefinition slashCommandDefinition : defs) {
            SlashCommandBuilder builder = convertSlashCommand(slashCommandDefinition);
            if (slashCommandDefinition.rootIdentifier().serverIds().length > 0) {
                for (long serverId : slashCommandDefinition.rootIdentifier().serverIds()) {
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
    public InteractionIdentifier getInteractionIdentifier(Object context) {
        if (context instanceof ButtonInteraction) {
            ButtonInteraction button = (ButtonInteraction) context;
            return InteractionIdentifier.builder().name(button.getCustomId()).type(InteractionType.BUTTON).build();
        }

        if (!(context instanceof SlashCommandInteraction))
            return null;

        boolean isAutocomplete = false;

        if (context instanceof AutocompleteInteraction)
            isAutocomplete = true;

        SlashCommandInteraction interaction = (SlashCommandInteraction) context;
        InteractionIdentifier lastIdentifier = InteractionIdentifier.rootBuilder()
            .name(interaction.getCommandName())
            .autocomplete(isAutocomplete)
            .build();
        List<SlashCommandInteractionOption> options = interaction.getOptions();
        if (!options.isEmpty()) {
            if (!options.getFirst().getArguments().isEmpty()) {
                lastIdentifier = InteractionIdentifier.builder()
                    .name(options.getFirst().getName())
                    .type(isAutocomplete ? InteractionType.AUTOCOMPLETE : InteractionType.COMMAND)
                    .parent(lastIdentifier)
                    .build();
                lastIdentifier = InteractionIdentifier.slashBuilder()
                    .name(options.getFirst().getOptions().getFirst().getName())
                    .autocomplete(isAutocomplete)
                    .parent(lastIdentifier)
                    .build();
            }else
                lastIdentifier = InteractionIdentifier.slashBuilder()
                    .name(options.getFirst().getName())
                    .autocomplete(isAutocomplete)
                    .parent(lastIdentifier)
                    .build();
        }

        return lastIdentifier;
    }

    private SlashCommandBuilder convertSlashCommand(SlashCommandDefinition def) {
        List<org.javacord.api.interaction.SlashCommandOption> options = new ArrayList<>();
        RootCommandIdentifier cmd = def.rootIdentifier();
        if (!def.isRootCommand()) {
            Arrays.stream(def.getSubCommands(null)).map(this::convertSubCommandDef).forEach(options::add);
            Arrays.stream(def.getSubCommandGroups()).map((x) -> convertSubCommandGroupDef(def, x)).forEach(options::add);
        }else {
            Arrays.stream(cmd.options()).map(this::convertOptionDef).forEach(options::add);
        }

        return org.javacord.api.interaction.SlashCommand.with(cmd.name(), cmd.description(), options);
    }

    private org.javacord.api.interaction.SlashCommandOption convertSubCommandGroupDef(SlashCommandDefinition def, SlashCommandIdentifier subGroup) {
        SlashCommandIdentifier[] subCommands = def.getSubCommands(subGroup.name());
        List<org.javacord.api.interaction.SlashCommandOption> convertedSubCommands = Arrays.stream(subCommands).map(this::convertSubCommandDef).toList();
        return org.javacord.api.interaction.SlashCommandOption.createWithOptions(
            org.javacord.api.interaction.SlashCommandOptionType.SUB_COMMAND_GROUP, 
            subGroup.name(), 
            subGroup.description(), 
            convertedSubCommands);
    }

    private org.javacord.api.interaction.SlashCommandOption convertSubCommandDef(SlashCommandIdentifier sub) {
        List<org.javacord.api.interaction.SlashCommandOption> convertedOptions = Arrays.stream(sub.options()).map(this::convertOptionDef).toList();
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
        for (SlashCommandOptionChoice choice : SlashCommandDefinition.getActualChoices(option)) {
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
            convertedChoices.add(builder.build());
        }
        return convertedChoices;
    }

    @Override
    public ContextObjectProvider getContextObjectProvider() {
        return contextObjectProvider;
    }

}
