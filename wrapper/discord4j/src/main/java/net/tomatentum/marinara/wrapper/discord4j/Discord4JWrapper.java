package net.tomatentum.marinara.wrapper.discord4j;

import java.util.List;
import java.util.function.UnaryOperator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputAutoCompleteEvent;
import discord4j.core.event.domain.interaction.InteractionCreateEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandOption.Type;
import discord4j.discordjson.json.ApplicationCommandOptionChoiceData;
import discord4j.discordjson.json.ApplicationCommandRequest;

import net.tomatentum.marinara.wrapper.CommandConverter;
import net.tomatentum.marinara.wrapper.CommandRegisterer;
import net.tomatentum.marinara.wrapper.ContextObjectProvider;
import net.tomatentum.marinara.wrapper.IdentifierProvider;
import net.tomatentum.marinara.wrapper.LibraryWrapper;
import net.tomatentum.marinara.wrapper.discord4j.identifierconverter.AutocompleteIdentifierConverter;
import net.tomatentum.marinara.wrapper.discord4j.identifierconverter.ButtonIdentifierConverter;
import net.tomatentum.marinara.wrapper.discord4j.identifierconverter.SlashCommandIdentifierConverter;

public class Discord4JWrapper extends LibraryWrapper {
    public static final UnaryOperator<List<ApplicationCommandInteractionOption>> SUB_FILTER = i ->
        i.stream()
            .filter(o -> o.getType().equals(Type.SUB_COMMAND) || o.getType().equals(Type.SUB_COMMAND_GROUP))
            .toList();
    
    public static final UnaryOperator<List<ApplicationCommandInteractionOption>> ARG_FILTER = i ->
            i.stream()
                .filter(o -> !o.getType().equals(Type.SUB_COMMAND) && !o.getType().equals(Type.SUB_COMMAND_GROUP))
                .toList();

    private Discord4JContextObjectProvider contextObjectProvider;
    private CommandRegisterer<ApplicationCommandRequest> commandRegisterer;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public Discord4JWrapper(GatewayDiscordClient api) {
        this.contextObjectProvider = new Discord4JContextObjectProvider();
        var converter = CommandConverter.of(new Discord4JConverterSpec());

        if (api != null) {
            this.commandRegisterer = CommandRegisterer.of(new Discord4JRegistererStrategy(api), converter);
            api.on(InteractionCreateEvent.class)
                .subscribe(event -> handleInteraction(event));
        }else
            logger.warn("GatewayDiscordClient was null so no Events were subscribed to.");
            
        logger.info("Discord4J wrapper loaded!");
    }

    @Override
    public CommandRegisterer<?> getRegisterer() {
        return this.commandRegisterer;
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

    @Override
    public void respondAutocomplete(Object context, List<Object> options) {
        if (context instanceof ChatInputAutoCompleteEvent event) {
            List<ApplicationCommandOptionChoiceData> choices = options.stream()
                .filter(ApplicationCommandOptionChoiceData.class::isInstance)
                .map(o -> (ApplicationCommandOptionChoiceData)o)
                .toList();
            event.respondWithSuggestions(choices);
        }
    }

}
