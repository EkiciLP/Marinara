package net.tomatentum.marinara.test.javacord;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.wrapper.LibraryWrapper;
import net.tomatentum.marinara.wrapper.javacord.JavacordWrapper;
@TestInstance(Lifecycle.PER_CLASS)
public class SlashCommandTest {

    String DISCORD_TOKEN = System.getenv("DISCORD_TEST_TOKEN");
    DiscordApi api;

    @BeforeAll
    void setUp() {
        api = new DiscordApiBuilder()
        .setToken(DISCORD_TOKEN)
        .login().join();
    }

    @AfterAll
    void tearDown() {
        api.disconnect();
        api = null;
    }

    @Test
    void testSlashCommand() {
        Marinara marinara = Marinara.load(new JavacordWrapper(api));
        marinara.getRegistry().addInteractions(new TestCommand());
        marinara.getRegistry().registerCommands();
        System.out.println("Success!");
    }
    
    @Test
    void testSlashCommandExecution() {
        LibraryWrapper wrapper = new JavacordWrapper(api);
        Marinara marinara = Marinara.load(wrapper);
        marinara.getRegistry().addInteractions(new TestCommand());

        SlashCommandInteractionOption optionMock = mock();
        SlashCommandInteraction interactionMock = mock();

        when(optionMock.getName()).thenReturn("foo");
        when(optionMock.getStringValue()).thenReturn(Optional.of("test"));

        when(interactionMock.getCommandName()).thenReturn("test");
        when(interactionMock.getOptions()).thenReturn(Arrays.asList(optionMock));
        when(interactionMock.getArguments()).thenReturn(Arrays.asList(optionMock));
        when(interactionMock.getOptionByName("foo")).thenReturn(Optional.of(optionMock));

        wrapper.handleInteraction(interactionMock);
    }

    

}
