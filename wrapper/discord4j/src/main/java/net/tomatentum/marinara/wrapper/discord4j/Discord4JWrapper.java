package net.tomatentum.marinara.wrapper.discord4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import org.apache.logging.log4j.Logger;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.InteractionCreateEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandOption.Type;
import discord4j.discordjson.json.ApplicationCommandOptionChoiceData;
import discord4j.discordjson.json.ApplicationCommandOptionData;
import discord4j.discordjson.json.ApplicationCommandRequest;

import net.tomatentum.marinara.interaction.commands.SlashCommandDefinition;
import net.tomatentum.marinara.util.LoggerUtil;
import net.tomatentum.marinara.wrapper.CommandConverter;
import net.tomatentum.marinara.wrapper.ContextObjectProvider;
import net.tomatentum.marinara.wrapper.IdentifierProvider;
import net.tomatentum.marinara.wrapper.LibraryWrapper;
import net.tomatentum.marinara.wrapper.discord4j.identifierconverter.AutocompleteIdentifierConverter;
import net.tomatentum.marinara.wrapper.discord4j.identifierconverter.ButtonIdentifierConverter;
import net.tomatentum.marinara.wrapper.discord4j.identifierconverter.SlashCommandIdentifierConverter;

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
    public IdentifierProvider createIdentifierProvider() {
        return IdentifierProvider.of(
            new SlashCommandIdentifierConverter(),
            new AutocompleteIdentifierConverter(),
            new ButtonIdentifierConverter()
        );
    }

    @Override
    public ContextObjectProvider getContextObjectProvider() {
        return this.contextObjectProvider;
    }
    
}
