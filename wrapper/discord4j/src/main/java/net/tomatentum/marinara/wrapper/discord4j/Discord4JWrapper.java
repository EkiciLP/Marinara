package net.tomatentum.marinara.wrapper.discord4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.commands.CommandConverter;
import net.tomatentum.marinara.interaction.commands.SlashCommandDefinition;
import net.tomatentum.marinara.interaction.ident.InteractionIdentifier;
import net.tomatentum.marinara.util.LoggerUtil;
import net.tomatentum.marinara.wrapper.ContextObjectProvider;
import net.tomatentum.marinara.wrapper.LibraryWrapper;

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
    private CommandConverter<ApplicationCommandRequest, ApplicationCommandOptionData, ApplicationCommandOptionChoiceData> commandConverter;

    private Logger logger = LoggerUtil.getLogger(getClass());

    public Discord4JWrapper(GatewayDiscordClient api) {
        this.api = api;
        this.contextObjectProvider = new Discord4JContextObjectProvider();
        this.commandConverter = CommandConverter.of(new Discord4JConverterSpec());

        if (api != null)
            api.on(InteractionCreateEvent.class)
                .subscribe(event -> handleInteraction(event));
        else
            logger.warn("GatewayDiscordClient was null so no Events were subscribed to.");
            
        logger.info("Discord4J wrapper loaded!");
    }

    @Override
    public void registerSlashCommands(SlashCommandDefinition[] defs) {
        HashMap<Long, List<ApplicationCommandRequest>> serverCommands = new HashMap<>();
        List<ApplicationCommandRequest> globalCommands = new ArrayList<>();
        long applicationId = api.getRestClient().getApplicationId().block();

        for (SlashCommandDefinition slashCommandDefinition : defs) {
            ApplicationCommandRequest request = this.commandConverter.convert(slashCommandDefinition);
            if (slashCommandDefinition.rootIdentifier().serverIds().length > 0) {
                for (long serverId : slashCommandDefinition.rootIdentifier().serverIds()) {
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
    public InteractionIdentifier getInteractionIdentifier(Object context) {

        if (context instanceof ButtonInteractionEvent) {
            ButtonInteractionEvent interaction = (ButtonInteractionEvent) context;
            return InteractionIdentifier.builder().name(interaction.getCustomId()).type(InteractionType.BUTTON).build();
        }

        List<ApplicationCommandInteractionOption> options;
        String commandName;
        boolean isAutocomplete = false;

        if (context instanceof ChatInputInteractionEvent) {
            ChatInputInteractionEvent interaction = (ChatInputInteractionEvent) context;
            options = SUB_FILTER.apply(interaction.getOptions());
            commandName = interaction.getCommandName();
        }else if (context instanceof ChatInputAutoCompleteEvent) {
            ChatInputAutoCompleteEvent interaction = (ChatInputAutoCompleteEvent) context;
            options = SUB_FILTER.apply(interaction.getOptions());
            commandName = interaction.getCommandName();
            isAutocomplete = true;
        }else
            return null;

        InteractionIdentifier last = InteractionIdentifier.slashBuilder().name(commandName).autocomplete(isAutocomplete).build();

        if (!options.isEmpty()) {
            List<ApplicationCommandInteractionOption> sub_options = SUB_FILTER.apply(options.getFirst().getOptions());
            if (!sub_options.isEmpty()) {
                last = InteractionIdentifier.builder()
                    .name(options.getFirst().getName())
                    .type(isAutocomplete ? InteractionType.AUTOCOMPLETE : InteractionType.COMMAND)
                    .parent(last).build();
                last = InteractionIdentifier.slashBuilder()
                    .name(sub_options.getFirst().getName())
                    .autocomplete(isAutocomplete)
                    .parent(last).build();
            }else
                last = InteractionIdentifier.slashBuilder()
                    .name(options.getFirst().getName())
                    .autocomplete(isAutocomplete)
                    .parent(last).build();
        }
        return last;
    }

    @Override
    public ContextObjectProvider getContextObjectProvider() {
        return this.contextObjectProvider;
    }
    
}
