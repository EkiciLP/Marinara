package net.tomatentum.marinara.wrapper.javacord;

import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.interaction.SlashCommandBuilder;

import net.tomatentum.marinara.wrapper.CommandConverter;
import net.tomatentum.marinara.wrapper.CommandRegisterer;
import net.tomatentum.marinara.wrapper.ContextObjectProvider;
import net.tomatentum.marinara.wrapper.IdentifierProvider;
import net.tomatentum.marinara.util.LoggerUtil;
import net.tomatentum.marinara.wrapper.LibraryWrapper;
import net.tomatentum.marinara.wrapper.javacord.identifierconverter.AutocompleteIdentifierConverter;
import net.tomatentum.marinara.wrapper.javacord.identifierconverter.ButtonIdentifierConverter;
import net.tomatentum.marinara.wrapper.javacord.identifierconverter.SlashCommandIdentifierConverter;

public class JavacordWrapper extends LibraryWrapper {

    private JavacordContextObjectProvider contextObjectProvider;
    private CommandRegisterer<SlashCommandBuilder> commandRegisterer;
    
    private Logger logger = LoggerUtil.getLogger(getClass());

    public JavacordWrapper(DiscordApi api) {
        this.contextObjectProvider = new JavacordContextObjectProvider();
        var converter = CommandConverter.of(new JavacordConverterSpec());
        this.commandRegisterer = CommandRegisterer.of(new JavacordRegistererStrategy(api), converter);

        if (api != null)
            api.addInteractionCreateListener((e) -> handleInteraction(e.getInteraction()));
        else
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

}
