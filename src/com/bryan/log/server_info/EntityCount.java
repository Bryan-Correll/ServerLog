package com.bryan.log.server_info;

import com.bryan.log.ServerLog;
import com.bryan.log.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.IOException;
import java.util.List;

public class EntityCount {

    private Methods methods;
    public EntityCount(ServerLog serverLog) {
        this.methods = new Methods(serverLog);
    }

    public void appendEntityCount() throws IOException {

        List<World> worlds = Bukkit.getServer().getWorlds();
        for (World world : worlds) {
            Integer entityCount = Bukkit.getServer().getWorld(world.getName()).getEntities().size();
            methods.appendString("/Server Information/Entity Count/" + world.getName() + "/", methods.getConfigFile().getString("entity-count").replace("[count]", entityCount.toString()));
        }
    }

}
