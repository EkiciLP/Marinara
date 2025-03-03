package net.tomatentum.marinara.interaction.ident;

import net.tomatentum.marinara.interaction.commands.annotation.SlashCommandOption;

public class RootCommandIdentifier extends SlashCommandIdentifier {

    private long[] serverIds;

    public RootCommandIdentifier(
            InteractionIdentifier parent, 
            String name, 
            String description, 
            SlashCommandOption[] options, 
            boolean isAutocomplete,
            long[] serverIds) {
        super(parent, name, description, options, isAutocomplete);
        this.serverIds = serverIds;
    }

    public long[] serverIds() {
        return serverIds;
    }

    public static class Builder {
        private InteractionIdentifier parent;
        private String name;
        private String description;
        private SlashCommandOption[] options;
        private boolean isAutocomplete = false;
        private long[] serverIds;


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

        public long[] serverIds() {
            return this.serverIds;
        }

        public Builder serverIds(long[] serverIds) {
            this.serverIds = serverIds;
            return this;
        }

        public SlashCommandIdentifier build() {
            return new RootCommandIdentifier(parent, name, description, options, isAutocomplete, serverIds);
        }

    }
    
}
