package net.tomatentum.marinara.wrapper.discord4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.apache.logging.log4j.Logger;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ButtonInteractionEvent;
import discord4j.core.event.domain.interaction.ChatInputAutoCompleteEvent;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.event.domain.interaction.InteractionCreateEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandOption.Type;
import discord4j.discordjson.json.ApplicationCommandOptionChoiceData;
import discord4j.discordjson.json.ApplicationCommandOptionData;
import discord4j.discordjson.json.ApplicationCommandRequest;

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
import net.tomatentum.marinara.util.LoggerUtil;
import net.tomatentum.marinara.wrapper.ContextObjectProvider;
import net.tomatentum.marinara.wrapper.LibraryWrapper;
import reactor.core.publisher.Mono;

public class Discord4JWrapper extends LibraryWrapper {

    public static final Function<List<ApplicationCommandInteractionOption>, List<ApplicationCommandInteractionOption>> SUB_FILTER = (i) ->
        i.stream()
            .filter(o -> o.getType().equals(Type.SUB_COMMAND) || o.getType().equals(Type.SUB_COMMAND_GROUP))
            .toList();
    
    public static final Function<List<ApplicationCommandInteractionOption>, List<ApplicationCommandInteractionOption>> ARG_FILTER = (i) ->
            i.stream()
                .filter(o -> !o.getType().equals(Type.SUB_COMMAND) && !o.getType().equals(Type.SUB_COMMAND_GROUP))
                .toList();

    private GatewayDiscordClient api;
    private Discord4JContextObjectProvider contextObjectProvider;

    private Logger logger = LoggerUtil.getLogger(getClass());

    public Discord4JWrapper(GatewayDiscordClient api) {
        this.api = api;
        this.contextObjectProvider = new Discord4JContextObjectProvider();
        api.on(InteractionCreateEvent.class)
            .subscribe(event -> handleInteraction(event));
        Mono.just("test").subscribe(logger::debug);
            
        logger.info("Discord4J wrapper loaded!");
    }

    @Override
    public InteractionType getInteractionType(Object context) {
        if (ChatInputAutoCompleteEvent.class.isAssignableFrom(context.getClass()))
            return InteractionType.AUTOCOMPLETE;
        if (ChatInputInteractionEvent.class.isAssignableFrom(context.getClass()))
            return InteractionType.COMMAND;
        if (ButtonInteractionEvent.class.isAssignableFrom(context.getClass()))
            return InteractionType.BUTTON;

        return null;
    }

    @Override
    public void registerSlashCommands(SlashCommandDefinition[] defs) {
        HashMap<Long, List<ApplicationCommandRequest>> serverCommands = new HashMap<>();
        List<ApplicationCommandRequest> globalCommands = new ArrayList<>();
        long applicationId = api.getRestClient().getApplicationId().block();

        for (SlashCommandDefinition slashCommandDefinition : defs) {
            ApplicationCommandRequest request = convertSlashCommand(slashCommandDefinition);
            if (slashCommandDefinition.getFullSlashCommand().serverIds().length > 0) {
                for (long serverId : slashCommandDefinition.getFullSlashCommand().serverIds()) {
                    serverCommands.putIfAbsent(serverId, new ArrayList<>());
                    serverCommands.get(serverId).add(request);
                }
            }else
                globalCommands.add(request);
        }

        for (long serverId : serverCommands.keySet()) {
            api.getRestClient().getApplicationService().bulkOverwriteGuildApplicationCommand(applicationId, serverId, serverCommands.get(serverId));
        }
        api.getRestClient().getApplicationService().bulkOverwriteGlobalApplicationCommand(applicationId, globalCommands);
    }

    @Override
    public ExecutableSlashCommandDefinition getCommandDefinition(Object context) {
        if (!(context instanceof ChatInputInteractionEvent))
            return null;

        ChatInputInteractionEvent interaction = (ChatInputInteractionEvent) context;
        ExecutableSlashCommandDefinition.Builder builder = new ExecutableSlashCommandDefinition.Builder();
        List<ApplicationCommandInteractionOption> options = SUB_FILTER.apply(interaction.getOptions());

        try {
            builder.setApplicationCommand(TypeFactory.annotation(SlashCommand.class, Map.of("name", interaction.getCommandName())));
            if (!options.isEmpty()) {
                if (!ARG_FILTER.apply(options.getFirst().getOptions()).isEmpty()) {
                    builder.setSubCommandGroup(TypeFactory.annotation(SubCommandGroup.class, Map.of("name", options.getFirst().getName())));
                    builder.setSubCommand(TypeFactory.annotation(SubCommand.class, Map.of("name", SUB_FILTER.apply(options.getFirst().getOptions()).getFirst().getName())));
                }else
                    builder.setSubCommand(TypeFactory.annotation(SubCommand.class, Map.of("name", options.getFirst().getName())));
            }
        } catch (AnnotationFormatException e) {
            logger.fatal(e);
        }

        return builder.build();
    }

    private ApplicationCommandRequest convertSlashCommand(SlashCommandDefinition def) {
        List<ApplicationCommandOptionData> options = new ArrayList<>();
        SlashCommand cmd = def.getFullSlashCommand();
        if (!def.isRootCommand()) {
            Arrays.stream(def.getSubCommands(null)).map(this::convertSubCommandDef).forEach(options::add);
            Arrays.stream(def.getSubCommandGroups()).map((x) -> convertSubCommandGroupDef(def, x)).forEach(options::add);
        }else {
            Arrays.stream(cmd.options()).map(this::convertOptionDef).forEach(options::add);
        }

        return ApplicationCommandRequest.builder()
            .name(cmd.name())
            .description(cmd.description())
            .options(options)
            .build();
    }

    private ApplicationCommandOptionData convertSubCommandGroupDef(SlashCommandDefinition def, SubCommandGroup subGroup) {
        SubCommand[] subCommands = def.getSubCommands(subGroup.name());
        List<ApplicationCommandOptionData> convertedSubCommands = Arrays.stream(subCommands).map(this::convertSubCommandDef).toList();
        return ApplicationCommandOptionData.builder()
            .type(Type.SUB_COMMAND_GROUP.getValue())
            .name(subGroup.name())
            .description(subGroup.description())
            .options(convertedSubCommands)
            .build();
    }

    private ApplicationCommandOptionData convertSubCommandDef(SubCommand sub) {
        List<ApplicationCommandOptionData> convertedOptions = Arrays.stream(sub.options()).map(this::convertOptionDef).toList();
        return ApplicationCommandOptionData.builder()
            .type(Type.SUB_COMMAND_GROUP.getValue())
            .name(sub.name())
            .description(sub.description())
            .options(convertedOptions)
            .build();
    }

    private ApplicationCommandOptionData convertOptionDef(SlashCommandOption option) {
        Type type = Enum.valueOf(Type.class, option.type().toString());
        return ApplicationCommandOptionData.builder()
            .type(type.getValue())
            .name(option.name())
            .description(option.description())
            .required(option.required())
            .autocomplete(option.autocomplete())
            .choices(convertChoices(option))
            .build();
    }

    private List<ApplicationCommandOptionChoiceData> convertChoices(SlashCommandOption option) {
        List<ApplicationCommandOptionChoiceData> convertedChoices = new ArrayList<>();
        for (SlashCommandOptionChoice choice : ExecutableSlashCommandDefinition.getActualChoices(option)) {
            var builder = ApplicationCommandOptionChoiceData.builder();
            builder.name(choice.name());
            if (choice.longValue() != Long.MAX_VALUE)
                builder.value(choice.longValue());
            if (choice.doubleValue() != Double.MAX_VALUE)
                builder.value(choice.doubleValue());
            if (!choice.stringValue().isEmpty())
                builder.value(choice.stringValue());
        }
        return convertedChoices;
    }

    @Override
    public String getButtonId(Object context) {
        ButtonInteractionEvent button = (ButtonInteractionEvent) context;
        return button.getCustomId();
    }

    @Override
    public ContextObjectProvider getContextObjectProvider() {
        return this.contextObjectProvider;
    }
    
}
