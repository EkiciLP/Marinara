package net.tomatentum.marinara.command;

import net.tomatentum.marinara.handler.InteractionHandler;

public abstract class DiscordCommand implements InteractionHandler {
    private String name;
    private String description;
    private String[] aliases;

    protected DiscordCommand(String name, String description, String... aliases) {
        this.name = name;
        this.description = description;
        this.aliases = aliases;
    }

    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }

    public String[] getAliases() {
        return aliases;
    }

}
