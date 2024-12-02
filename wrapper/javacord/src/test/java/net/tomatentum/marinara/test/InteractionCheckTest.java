package net.tomatentum.marinara.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.javacord.api.entity.permission.PermissionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.test.mocks.ButtonInteractionMock;
import net.tomatentum.marinara.test.mocks.DiscordApiMock;
import net.tomatentum.marinara.test.mocks.ServerMock;
import net.tomatentum.marinara.wrapper.LibraryWrapper;
import net.tomatentum.marinara.wrapper.javacord.JavacordWrapper;
import net.tomatentum.marinara.wrapper.javacord.checks.PermissionCheck;

@TestInstance(Lifecycle.PER_CLASS)
public class InteractionCheckTest {
    
    @Test
    public void testInteractionCheck() {
        LibraryWrapper wrapper = new JavacordWrapper(new DiscordApiMock());
        Marinara marinara = Marinara.load(wrapper);
        marinara.getCheckRegistry().addCheck(new TestInteractionCheck());
        marinara.getRegistry().addInteractions(new TestButton());
        wrapper.handleInteraction(new ButtonInteractionMock("test"));
        assertTrue(TestInteractionCheck.preExecuted);
        assertTrue(TestInteractionCheck.postExecuted);
    }

    @Test
    public void testPermissionCheck() {
        LibraryWrapper wrapper = new JavacordWrapper(new DiscordApiMock());
        Marinara marinara = Marinara.load(wrapper);
        marinara.getCheckRegistry().addCheck(new PermissionCheck());
        marinara.getRegistry().addInteractions(new TestButton());
        wrapper.handleInteraction(new ButtonInteractionMock("permissionCheck"));
        assertTrue(TestButton.didPermRun);
        TestButton.didPermRun = false;
        ServerMock.TESTPERMISSION = PermissionType.ATTACH_FILE;
        wrapper.handleInteraction(new ButtonInteractionMock("permissionCheck"));
        assertFalse(TestButton.didPermRun);
    }

}
