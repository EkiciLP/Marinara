package net.tomatentum.marinara.test.javacord;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.test.javacord.mocks.CommonMocks;
import net.tomatentum.marinara.wrapper.LibraryWrapper;
import net.tomatentum.marinara.wrapper.javacord.JavacordWrapper;

@TestInstance(Lifecycle.PER_CLASS)
public class ButtonTest {

    @Test
    public void testButtonExecution() {
        LibraryWrapper wrapper = new JavacordWrapper(null); //null okay as we don't use the discord API in this test.
        Marinara marinara = Marinara.load(wrapper);
        marinara.getRegistry().addInteractions(new TestButton());
        wrapper.handleInteraction(CommonMocks.getButtonInteractionMock("test"));
        assertTrue(TestButton.didRun);
    }
    
}
