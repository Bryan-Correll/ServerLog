package com.bryan.log.server_info;

import com.bryan.log.ServerLog;
import com.bryan.log.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.IOException;
import java.util.List;

public class ChunksLoaded {

    private Methods methods;

    public ChunksLoaded(ServerLog serverLog) {
        this.methods = new Methods(serverLog);
    }

    public void appendChunksLoaded() throws IOException {

        List<World> worlds = Bukkit.getServer().getWorlds();
        for (World world : worlds) {
            Integer loadedChunkCount = Bukkit.getServer().getWorld(world.getName()).getLoadedChunks().length;
            methods.appendString("/Server Information/Chunks Loaded/" + world.getName() + "/", methods.getConfigFile().getString("chunks-loaded").replace("[count]", loadedChunkCount.toString()));
        }
    }

}
