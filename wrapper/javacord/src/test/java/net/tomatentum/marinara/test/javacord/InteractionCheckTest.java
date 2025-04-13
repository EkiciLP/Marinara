package net.tomatentum.marinara.test.javacord;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.javacord.api.entity.server.Server;
import org.javacord.api.interaction.ButtonInteraction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.test.javacord.mocks.CommonMocks;
import net.tomatentum.marinara.wrapper.LibraryWrapper;
import net.tomatentum.marinara.wrapper.javacord.JavacordWrapper;
import net.tomatentum.marinara.wrapper.javacord.checks.PermissionCheck;

@TestInstance(Lifecycle.PER_CLASS)
class InteractionCheckTest {
    
    @Test
    void testInteractionCheck() {
        LibraryWrapper wrapper = new JavacordWrapper(null);
        Marinara marinara = Marinara.load(wrapper);
        marinara.getCheckContainer().addAllMethods(new TestInteractionCheck());
        marinara.getInteractionContainer().addAllMethods(new TestButton());
        wrapper.handleInteraction(CommonMocks.getButtonInteractionMock("test"));
        assertTrue(TestInteractionCheck.preExecuted);
        assertTrue(TestInteractionCheck.postExecuted);
    }

    @Test
    void testPermissionCheck() {
        LibraryWrapper wrapper = new JavacordWrapper(null);
        Marinara marinara = Marinara.load(wrapper);
        marinara.getCheckContainer().addAllMethods(new PermissionCheck());
        marinara.getInteractionContainer().addAllMethods(new TestButton());

        Server serverMock = mock();
        ButtonInteraction buttonInteractionMock = CommonMocks.getButtonInteractionMock("permissionCheck", serverMock);
        when(serverMock.hasPermissions(any(), any())).thenReturn(false);
        wrapper.handleInteraction(buttonInteractionMock);
        assertFalse(TestButton.didPermRun);

        when(serverMock.hasPermissions(any(), any())).thenReturn(true);
        wrapper.handleInteraction(buttonInteractionMock);
        assertTrue(TestButton.didPermRun);
    }

}
