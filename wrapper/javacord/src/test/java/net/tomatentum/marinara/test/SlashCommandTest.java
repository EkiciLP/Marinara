package net.tomatentum.marinara.test;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.test.mocks.SlashCommandInteractionMock;
import net.tomatentum.marinara.wrapper.LibraryWrapper;
import net.tomatentum.marinare.wrapper.javacord.JavacordWrapper;
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

        wrapper.handleInteraction(new SlashCommandInteractionMock());
    }

    

}
