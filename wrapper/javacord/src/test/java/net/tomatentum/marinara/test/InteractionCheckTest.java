package net.tomatentum.marinara.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.test.mocks.ButtonInteractionMock;
import net.tomatentum.marinara.test.mocks.DiscordApiMock;
import net.tomatentum.marinara.wrapper.LibraryWrapper;
import net.tomatentum.marinara.wrapper.javacord.JavacordWrapper;

@TestInstance(Lifecycle.PER_CLASS)
public class InteractionCheckTest {
    
    @Test
    public void testInteractionCheck() {
        LibraryWrapper wrapper = new JavacordWrapper(new DiscordApiMock());
        Marinara marinara = Marinara.load(wrapper);
        marinara.getCheckRegistry().addCheck(new TestInteractionCheck());
        marinara.getRegistry().addInteractions(new TestButton());
        wrapper.handleInteraction(new ButtonInteractionMock());
        assertTrue(TestInteractionCheck.preExecuted);
        assertTrue(TestInteractionCheck.postExecuted);
    }

}
