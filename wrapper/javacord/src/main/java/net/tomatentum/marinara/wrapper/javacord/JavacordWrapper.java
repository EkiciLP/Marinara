package net.tomatentum.marinara.wrapper.javacord;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionChoice;

import net.tomatentum.marinara.interaction.commands.SlashCommandDefinition;
import net.tomatentum.marinara.wrapper.CommandConverter;
import net.tomatentum.marinara.wrapper.ContextObjectProvider;
import net.tomatentum.marinara.wrapper.IdentifierProvider;
import net.tomatentum.marinara.util.LoggerUtil;
import net.tomatentum.marinara.wrapper.LibraryWrapper;
import net.tomatentum.marinara.wrapper.javacord.identifierconverter.AutocompleteIdentifierConverter;
import net.tomatentum.marinara.wrapper.javacord.identifierconverter.ButtonIdentifierConverter;
import net.tomatentum.marinara.wrapper.javacord.identifierconverter.SlashCommandIdentifierConverter;

public class JavacordWrapper extends LibraryWrapper {

    private DiscordApi api;
    private JavacordContextObjectProvider contextObjectProvider;
    private CommandConverter<SlashCommandBuilder, SlashCommandOption, SlashCommandOptionChoice> commandConverter;
    
    private Logger logger = LoggerUtil.getLogger(getClass());

    public JavacordWrapper(DiscordApi api) {
        this.api = api;
        this.contextObjectProvider = new JavacordContextObjectProvider();
        this.commandConverter = CommandConverter.of(new JavacordConverterSpec());

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
            SlashCommandBuilder builder = commandConverter.convert(slashCommandDefinition);
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
    public IdentifierProvider createIdentifierProvider() {
        return IdentifierProvider.of(
            new SlashCommandIdentifierConverter(),
            new AutocompleteIdentifierConverter(),
            new ButtonIdentifierConverter()
        );
    }

    @Override
    public ContextObjectProvider getContextObjectProvider() {
        return contextObjectProvider;
    }

}
