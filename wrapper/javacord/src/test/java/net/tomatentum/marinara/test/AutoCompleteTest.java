package net.tomatentum.marinara.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.test.mocks.AutocompleteInteractionMock;
import net.tomatentum.marinara.test.mocks.DiscordApiMock;
import net.tomatentum.marinara.wrapper.LibraryWrapper;
import net.tomatentum.marinara.wrapper.javacord.JavacordWrapper;

public class AutoCompleteTest {
    
    @Test
    public void testAutocomplete() {
        LibraryWrapper wrapper = new JavacordWrapper(new DiscordApiMock()); //null okay as we don't use the discord API in this test.
        Marinara marinara = Marinara.load(wrapper);
        marinara.getRegistry().addInteractions(new TestAutocomplete());
        wrapper.handleInteraction(new AutocompleteInteractionMock());
        assertTrue(AutocompleteInteractionMock.didAutocompleteRun);
    }
}
