package net.tomatentum.marinara.interaction.ident;

import net.tomatentum.marinara.interaction.InteractionType;
import net.tomatentum.marinara.interaction.commands.annotation.SlashCommandOption;

public class RootCommandIdentifier extends SlashCommandIdentifier {

    private long[] serverIds;

    public RootCommandIdentifier(
            InteractionIdentifier parent, 
            String name, 
            String description, 
            InteractionType type,
            SlashCommandOption[] options, 
            long[] serverIds,
            String[] autocompleteRef) {
        super(parent, name, description, type, options, autocompleteRef);
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
        private long[] serverIds;
        private String[] autocompleteRef;

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

        public long[] serverIds() {
            return this.serverIds;
        }

        public Builder serverIds(long[] serverIds) {
            this.serverIds = serverIds;
            return this;
        }

        public String[] autocompleteRef() {
            return this.autocompleteRef;
        }

        public Builder autocompleteRef(String[] autocompleteRef) {
            this.autocompleteRef = autocompleteRef;
            return this;
        }

        public SlashCommandIdentifier build() {
            return new RootCommandIdentifier(
                parent, 
                name, 
                description, 
                InteractionType.COMMAND, 
                options, 
                serverIds,
                autocompleteRef);
        }

    }
    
}
