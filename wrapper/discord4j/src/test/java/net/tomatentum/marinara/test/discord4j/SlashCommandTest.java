package net.tomatentum.marinara.test.discord4j;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import discord4j.core.object.command.ApplicationCommandOption.Type;
import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.wrapper.LibraryWrapper;
import net.tomatentum.marinara.wrapper.discord4j.Discord4JWrapper;

@TestInstance(Lifecycle.PER_CLASS)
class SlashCommandTest {

    private static String DISCORD_TOKEN = System.getenv("DISCORD_TEST_TOKEN");
    GatewayDiscordClient client;

    @BeforeAll
    void setUp() {
        client = DiscordClient.create(DISCORD_TOKEN).login().block();
    }

    @AfterAll
    void tearDown() {
        client.logout().block();
        client = null;
    }

    @Test
    void testSlashCommand() {
        Marinara marinara = Marinara.load(new Discord4JWrapper(client));
        marinara.getInteractionContainer().addAllMethods(new TestCommand());
        marinara.registerCommands();
        System.out.println("Success!");
    }
    
    @Test
    void testSlashCommandExecution() {
        ApplicationCommandInteractionOption optionMock = mock();
        ChatInputInteractionEvent eventMock = mock();

        when(optionMock.getName()).thenReturn("foo");
        when(optionMock.getType()).thenReturn(Type.STRING);
        when(optionMock.getValue()).thenReturn(
            Optional.of(
                new ApplicationCommandInteractionOptionValue(null, null, Type.STRING.getValue(), "test", null)
                ));

        when(eventMock.getCommandName()).thenReturn("test");
        when(eventMock.getOptions()).thenReturn(Arrays.asList(optionMock));
        when(eventMock.getOption("foo")).thenReturn(Optional.of(optionMock));
        
        LibraryWrapper wrapper = new Discord4JWrapper(client);
        Marinara marinara = Marinara.load(wrapper);
        marinara.getInteractionContainer().addAllMethods(new TestCommand());

        wrapper.handleInteraction(eventMock);
    }

    

}
