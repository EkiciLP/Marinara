package net.tomatentum.marinara.test;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.junit.jupiter.api.Test;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinare.wrapper.javacord.JavacordWrapper;

public class SlashCommandTest {

    String DISCORD_TOKEN = System.getenv("DISCORD_TEST_TOKEN");

    @Test
    public void testSlashCommand() {
        DiscordApi api = new DiscordApiBuilder()
        .setToken(DISCORD_TOKEN)
        .login().join();
        Marinara marinara = Marinara.load(new JavacordWrapper(api));
        marinara.getRegistry().addInteractions(new TestCommand());
        marinara.getRegistry().registerCommands();
        System.out.println("done");
    }

}
