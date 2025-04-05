package net.tomatentum.marinara.test.discord4j;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import discord4j.core.event.domain.interaction.ChatInputAutoCompleteEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import discord4j.core.object.command.ApplicationCommandOption.Type;
import net.tomatentum.marinara.Marinara;
import net.tomatentum.marinara.wrapper.LibraryWrapper;
import net.tomatentum.marinara.wrapper.discord4j.Discord4JWrapper;

class AutoCompleteTest {
    
    @Test
    void testAutocomplete() {
        ApplicationCommandInteractionOption optionMock = mock();
        ChatInputAutoCompleteEvent autoCompleteEventMock = mock();

        when(optionMock.getName()).thenReturn("foo");
        when(optionMock.getType()).thenReturn(Type.STRING);
        when(optionMock.getValue()).thenReturn(
            Optional.of(
                new ApplicationCommandInteractionOptionValue(null, null, Type.STRING.getValue(), "test", null)
                ));

        when(autoCompleteEventMock.getCommandName()).thenReturn("test");
        when(autoCompleteEventMock.getOptions()).thenReturn(new ArrayList<>());
        when(autoCompleteEventMock.getFocusedOption()).thenReturn(optionMock);

        LibraryWrapper wrapper = new Discord4JWrapper(null); //null okay as we don't use the discord API in this test.
        Marinara marinara = Marinara.load(wrapper);
        marinara.getRegistry().addInteractions(new TestAutocomplete());
        wrapper.handleInteraction(autoCompleteEventMock);
        verify(autoCompleteEventMock).respondWithSuggestions(any());
    }
}
