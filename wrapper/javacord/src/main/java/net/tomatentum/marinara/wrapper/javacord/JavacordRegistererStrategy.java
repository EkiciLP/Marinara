package net.tomatentum.marinara.wrapper.javacord;

import java.util.Set;

import org.javacord.api.DiscordApi;
import org.javacord.api.interaction.SlashCommandBuilder;

import net.tomatentum.marinara.wrapper.CommandRegisterer;
import net.tomatentum.marinara.wrapper.ServerCommandList;

public class JavacordRegistererStrategy implements CommandRegisterer.Strategy<SlashCommandBuilder> {

    private DiscordApi api;

    public JavacordRegistererStrategy(DiscordApi api) {
        this.api = api;
    }

    @Override
    public void registerServer(ServerCommandList<SlashCommandBuilder> commands) {
        api.bulkOverwriteServerApplicationCommands(commands.serverId(), commands);
    }

    @Override
    public void registerGlobal(Set<SlashCommandBuilder> defs) {
        api.bulkOverwriteGlobalApplicationCommands(defs);
    }

}
