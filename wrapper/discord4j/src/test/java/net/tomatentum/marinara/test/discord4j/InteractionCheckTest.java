package net.tomatentum.marinara.test.discord4j;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import discord4j.core.event.domain.interaction.ButtonInteractionEvent;
import discord4j.core.object.command.Interaction;
import discord4j.core.object.entity.Member;
import discord4j.rest.util.Permission;
import discord4j.rest.util.PermissionSet;
import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.test.discord4j.mocks.CommonMocks;
import net.tomatentum.marinara.wrapper.LibraryWrapper;
import net.tomatentum.marinara.wrapper.discord4j.Discord4JWrapper;
import net.tomatentum.marinara.wrapper.discord4j.checks.PermissionCheck;
import reactor.core.publisher.Mono;

@TestInstance(Lifecycle.PER_CLASS)
class InteractionCheckTest {
    
    @Test
    void testInteractionCheck() {
        ButtonInteractionEvent buttonEventMock = CommonMocks.getButtonEventMock("test");

        LibraryWrapper wrapper = new Discord4JWrapper(null);
        Marinara marinara = Marinara.load(wrapper);
        marinara.getCheckContainer().addAllMethods(new TestInteractionCheck());
        marinara.getInteractionContainer().addAllMethods(new TestButton());
        wrapper.handleInteraction(buttonEventMock);

        assertTrue(TestInteractionCheck.preExecuted);
        assertTrue(TestInteractionCheck.postExecuted);
        assertTrue(TestButton.didRun);
    }

    @Test
    void testPermissionCheck() {
        Member memberMock = mock();
        Interaction interactionMock = mock();

        when(memberMock.getBasePermissions()).thenReturn(Mono.just(PermissionSet.none()));

        when(interactionMock.getMember()).thenReturn(Optional.of(memberMock));

        ButtonInteractionEvent buttonEventMock = CommonMocks.getButtonEventMock("permissionCheck", interactionMock);

        LibraryWrapper wrapper = new Discord4JWrapper(null);
        Marinara marinara = Marinara.load(wrapper);
        marinara.getCheckContainer().addAllMethods(new PermissionCheck());
        marinara.getInteractionContainer().addAllMethods(new TestButton());

        wrapper.handleInteraction(buttonEventMock);
        assertFalse(TestButton.didPermRun);
        TestButton.didPermRun = false;
        
        when(memberMock.getBasePermissions()).thenReturn(Mono.just(PermissionSet.of(Permission.ATTACH_FILES)));

        wrapper.handleInteraction(buttonEventMock);
        assertTrue(TestButton.didPermRun);
    }

}
