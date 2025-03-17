package net.tomatentum.marinara.wrapper.discord4j;

import java.util.ArrayList;
import java.util.Set;

import discord4j.core.GatewayDiscordClient;
import discord4j.discordjson.json.ApplicationCommandRequest;
import discord4j.rest.service.ApplicationService;
import net.tomatentum.marinara.wrapper.CommandRegisterer;
import net.tomatentum.marinara.wrapper.ServerCommandList;

public class Discord4JRegistererStrategy implements CommandRegisterer.Strategy<ApplicationCommandRequest> {
    
    private ApplicationService appService;
    private long applicationId;

    public Discord4JRegistererStrategy(GatewayDiscordClient api) {
        this.appService = api.getRestClient().getApplicationService();
        this.applicationId = api.getRestClient().getApplicationId().block();
    }

    @Override
    public void registerServer(ServerCommandList<ApplicationCommandRequest> commands) {
        appService.bulkOverwriteGuildApplicationCommand(applicationId, commands.serverId(), new ArrayList<>(commands));

    }

    @Override
    public void registerGlobal(Set<ApplicationCommandRequest> defs) {
        appService.bulkOverwriteGlobalApplicationCommand(applicationId, new ArrayList<>(defs));
    }
}
