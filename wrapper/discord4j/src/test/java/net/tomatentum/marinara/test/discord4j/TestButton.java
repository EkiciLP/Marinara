package net.tomatentum.marinara.test.discord4j;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import discord4j.core.event.domain.interaction.ButtonInteractionEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.rest.util.Permission;
import net.tomatentum.marinara.interaction.InteractionHandler;
import net.tomatentum.marinara.interaction.annotation.Button;
import net.tomatentum.marinara.test.discord4j.TestInteractionCheck.TestCheck;
import net.tomatentum.marinara.wrapper.discord4j.checks.PermissionCheck.HasPermission;

public class TestButton implements InteractionHandler {


    public static boolean didRun = false;
    @Button("test")
    @TestCheck
    public void exec(ButtonInteractionEvent interaction, MessageChannel channel, Message message, Member member, User user, Guild server) {
        assertNotNull(interaction);
        assertNotNull(channel);
        assertNotNull(message);
        assertNotNull(member);
        assertNotNull(user);
        assertNotNull(server);
        didRun = true;
        System.out.println("Success!");
    }

    public static boolean didPermRun = false;

    @Button("permissionCheck")
    @HasPermission({Permission.ATTACH_FILES})
    public void exec(ButtonInteractionEvent interaction) {
        didPermRun = true;
        System.out.println("It worked!");
    }
    
}
