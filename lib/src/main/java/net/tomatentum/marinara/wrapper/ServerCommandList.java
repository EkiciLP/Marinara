package net.tomatentum.marinara.wrapper;

import java.util.HashSet;

public class ServerCommandList<A> extends HashSet<A>{

    private long serverId;
    
    public ServerCommandList(long serverId) {
        this.serverId = serverId;
    }

    public long serverId() {
        return serverId;
    }
}
