package net.tomatentum.marinara.command;

public class GlobalDiscordCommand extends DiscordCommand{
    private boolean enabledInDms = false;

    public GlobalDiscordCommand(String name, String description, String... aliases) {
        super(name, description, aliases);
    }

    public boolean isEnabledInDms() {
        return enabledInDms;
    }

    public void setEnabledInDms(boolean enabledInDms) {
        this.enabledInDms = enabledInDms;
    }
}
