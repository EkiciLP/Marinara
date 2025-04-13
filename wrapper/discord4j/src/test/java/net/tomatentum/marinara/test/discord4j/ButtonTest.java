package net.tomatentum.marinara.test.discord4j;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import discord4j.core.event.domain.interaction.ButtonInteractionEvent;
import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.test.discord4j.mocks.CommonMocks;
import net.tomatentum.marinara.wrapper.LibraryWrapper;
import net.tomatentum.marinara.wrapper.discord4j.Discord4JWrapper;

@TestInstance(Lifecycle.PER_CLASS)
class ButtonTest {

    @Test
    void testButtonExecution() {
        ButtonInteractionEvent buttonEventMock = CommonMocks.getButtonEventMock("test");

        LibraryWrapper wrapper = new Discord4JWrapper(null); //null okay as we don't use the discord API in this test.
        Marinara marinara = Marinara.load(wrapper);
        marinara.getInteractionContainer().addAllMethods(new TestButton());
        wrapper.handleInteraction(buttonEventMock);
        assertTrue(TestButton.didRun);
    }
    
}
