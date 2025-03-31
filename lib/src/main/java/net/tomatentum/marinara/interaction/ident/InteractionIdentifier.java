package net.tomatentum.marinara.interaction.ident;

import java.util.Objects;

import net.tomatentum.marinara.interaction.InteractionType;

public class InteractionIdentifier {

    public static InteractionIdentifier.Builder builder() {
        return new InteractionIdentifier.Builder();
    }

    public static RootCommandIdentifier.Builder rootBuilder() {
        return new RootCommandIdentifier.Builder();
    }

    public static SlashCommandIdentifier.Builder slashBuilder() {
        return new SlashCommandIdentifier.Builder();
    }

    public static InteractionIdentifier createHierarchy(InteractionType type, String... names) {
        InteractionIdentifier last = null;
        for (String string : names) {
            last = builder().name(string).type(type).parent(last).build();
        }
        return last;
    }

    public static void tryAddDescriptions(InteractionIdentifier receiver, InteractionIdentifier provider) {
        if (receiver == null || provider == null)
            return;

        if (receiver.description().isBlank())
            receiver.description = provider.description();
        tryAddDescriptions(receiver.parent(), provider.parent());
    }

    private InteractionIdentifier parent;
    private String name;
    private String description;
    private InteractionType type;

    InteractionIdentifier(InteractionIdentifier parent, String name, String description, InteractionType type) {
        this.parent = parent;
        this.name = name;
        this.description = description;
        this.type = type;
    }

    public InteractionIdentifier rootNode() { return rootNode(this); }

    private InteractionIdentifier rootNode(InteractionIdentifier identifier) {
        if (identifier.parent() == null)
            return identifier;
        return rootNode(identifier.parent());
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public InteractionIdentifier parent() {
        return parent;
    }

    public InteractionType type() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof InteractionIdentifier))
            return false;
        InteractionIdentifier ident = (InteractionIdentifier) obj;
        if (!type().equals(ident.type()))
            return false;
        if (!name().equals(ident.name()))
            return false;
        return Objects.equals(parent(), ident.parent());
    }

    @Override
    public String toString() {
        if (parent() == null)
            return name();
        return "{}.{}".formatted(name(), parent().toString());
    }

    public static class Builder {
        private InteractionIdentifier parent;
        private String name;
        private String description;
        private InteractionType type;

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
            return description;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public InteractionType type() {
            return type;
        }

        public Builder type(InteractionType type) {
            this.type = type;
            return this;
        }

        public InteractionIdentifier build() {
            return new InteractionIdentifier(parent, name, description, type);
        }

    }
    
}
