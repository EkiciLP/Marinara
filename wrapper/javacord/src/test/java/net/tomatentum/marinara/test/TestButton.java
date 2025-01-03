package net.tomatentum.marinara.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.ButtonInteraction;

import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.annotation.Button;

public class TestButton implements InteractionHandler {


    public static boolean didRun = false;
    @Button("test")
    public void exec(ButtonInteraction interaction, TextChannel channel, Message message, User member, Server server) {
        assertNotNull(interaction);
        assertNotNull(channel);
        assertNotNull(message);
        assertNotNull(member);
        assertNotNull(server);
        didRun = true;
        System.out.println("Success!");
    }
    
}
