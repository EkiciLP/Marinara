package net.tomatentum.marinara.command;

public class ServerDiscordCommand extends DiscordCommand {
    private long[] servers;

    public ServerDiscordCommand(String name, String description, String... aliases) {
        super(name, description, aliases);
    }

    public long[] getServers() {
        return servers;
    }
}