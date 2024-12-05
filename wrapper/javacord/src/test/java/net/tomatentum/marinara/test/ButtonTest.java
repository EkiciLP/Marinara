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
public class ButtonTest {

    @Test
    public void testButtonExecution() {
        LibraryWrapper wrapper = new JavacordWrapper(new DiscordApiMock()); //null okay as we don't use the discord API in this test.
        Marinara marinara = Marinara.load(wrapper);
        marinara.getRegistry().addInteractions(new TestButton());
        wrapper.handleInteraction(new ButtonInteractionMock("test"));
        assertTrue(TestButton.didRun);
    }
    
}
