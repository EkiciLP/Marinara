package net.tomatentum.marinara.wrapper.javacord;

import java.util.List;

import org.javacord.api.DiscordApi;
import org.javacord.api.interaction.AutocompleteInteraction;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandOptionChoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.tomatentum.marinara.wrapper.CommandConverter;
import net.tomatentum.marinara.wrapper.CommandRegisterer;
import net.tomatentum.marinara.wrapper.ContextObjectProvider;
import net.tomatentum.marinara.wrapper.IdentifierProvider;
import net.tomatentum.marinara.wrapper.LibraryWrapper;
import net.tomatentum.marinara.wrapper.javacord.identifierconverter.AutocompleteIdentifierConverter;
import net.tomatentum.marinara.wrapper.javacord.identifierconverter.ButtonIdentifierConverter;
import net.tomatentum.marinara.wrapper.javacord.identifierconverter.SlashCommandIdentifierConverter;

public class JavacordWrapper extends LibraryWrapper {

    private JavacordContextObjectProvider contextObjectProvider;
    private CommandRegisterer<SlashCommandBuilder> commandRegisterer;
    
    private Logger logger = LoggerFactory.getLogger(getClass());

    public JavacordWrapper(DiscordApi api) {
        this.contextObjectProvider = new JavacordContextObjectProvider();
        var converter = CommandConverter.of(new JavacordConverterSpec());

        if (api != null) {
            this.commandRegisterer = CommandRegisterer.of(new JavacordRegistererStrategy(api), converter);
            api.addInteractionCreateListener(e -> handleInteraction(e.getInteraction()));
        }else
            logger.warn("DiscordApi was null so no Events were subscribed to.");
        logger.info("Javacord wrapper loaded!");
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
        return contextObjectProvider;
    }

    @Override
    public void respondAutocomplete(Object context, List<Object> options) {
        if (context instanceof AutocompleteInteraction interaction) {
            List<SlashCommandOptionChoice> choices = options.stream()
                .filter(SlashCommandOptionChoice.class::isInstance)
                .map(o -> (SlashCommandOptionChoice)o)
                .toList();
            interaction.respondWithChoices(choices);
        }
    }

}
