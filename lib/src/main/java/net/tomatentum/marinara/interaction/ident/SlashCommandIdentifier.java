package net.tomatentum.marinara.interaction.ident;

import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommandOption;

public class SlashCommandIdentifier extends InteractionIdentifier {

    private SlashCommandOption[] options;

    protected SlashCommandIdentifier(
            InteractionIdentifier parent, 
            String name, 
            String description,
            SlashCommandOption[] options,
            boolean isAutocomplete
            ) {
        super(parent, name, description, isAutocomplete ? InteractionType.AUTOCOMPLETE : InteractionType.COMMAND);
        this.options = options;
    }

    public SlashCommandOption[] options() {
        return this.options;
    }

    public static class Builder {
        private InteractionIdentifier parent;
        private String name;
        private String description;
        private SlashCommandOption[] options;
        private boolean isAutocomplete = false;

        public InteractionIdentifier parent() {
            return parent;
        }

        public Builder parent(InteractionIdentifier parent) {
            this.parent = parent;
            return this;
        }

        public String name() {
            return name;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public String description() {
            return this.description;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public SlashCommandOption[] options() {
            return this.options;
        }

        public Builder options(SlashCommandOption[] options) {
            this.options = options;
            return this;
        }

        public boolean autocomplete() {
            return this.isAutocomplete;
        }

        public Builder autocomplete(boolean isAutocomplete) {
            this.isAutocomplete = isAutocomplete;
            return this;
        }

        public SlashCommandIdentifier build() {
            return new SlashCommandIdentifier(parent, name, description, options, isAutocomplete);
        }

    }
    
}
