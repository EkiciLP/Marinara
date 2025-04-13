package net.tomatentum.marinara.test.javacord;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.javacord.api.interaction.AutocompleteInteraction;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.junit.jupiter.api.Test;

import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.wrapper.LibraryWrapper;
import net.tomatentum.marinara.wrapper.javacord.JavacordWrapper;

class AutoCompleteTest {
    
    @Test
    void testAutocomplete() {

        SlashCommandInteractionOption optionMock = mock();
        AutocompleteInteraction autocompleteInteractionMock = mock();

        when(optionMock.getName()).thenReturn("foo");
        when(optionMock.getStringValue()).thenReturn(Optional.of("test"));

        when(autocompleteInteractionMock.getCommandName()).thenReturn("test");
        when(autocompleteInteractionMock.getFocusedOption()).thenReturn(optionMock);
        when(autocompleteInteractionMock.getOptions()).thenReturn(Collections.emptyList());

        LibraryWrapper wrapper = new JavacordWrapper(null); //null okay as we don't use the discord API in this test.
        Marinara marinara = Marinara.load(wrapper);
        marinara.getInteractionContainer().addAllMethods(new TestAutocomplete());
        wrapper.handleInteraction(autocompleteInteractionMock);
        verify(autocompleteInteractionMock).respondWithChoices(any());
    }
}
