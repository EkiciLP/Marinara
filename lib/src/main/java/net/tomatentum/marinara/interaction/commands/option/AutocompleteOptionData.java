package net.tomatentum.marinara.interaction.commands.option;

import java.util.Arrays;

import net.tomatentum.marinara.interaction.commands.annotation.SlashCommandOption;

public record AutocompleteOptionData(String name, Object input) {
    
    public String[] getAutocompleteRefs(SlashCommandOption[] options) {
        return Arrays.stream(options)
            .filter(o -> o.name().equals(this.name()))
            .flatMap(o -> Arrays.stream(o.autocompletes()))
            .map(a -> a.value())
            .toArray(String[]::new);
    }
}
