package com.bryan.log.server_info;

import com.bryan.log.ServerLog;
import com.bryan.log.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.IOException;
import java.util.List;

public class ChunksLoaded {

    private ServerLog serverLog;
    private Methods methods;
    public ChunksLoaded(ServerLog serverLog) {
        this.serverLog = serverLog;
        this.methods = new Methods(serverLog);
    }

    public void appendChunksLoaded() throws IOException {
        if (methods.dateChanged("/Server Information/Chunks Loaded/")) {
            try {
                methods.moveToHistory();
            } catch (InvalidConfigurationException ex) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "(Chunks Loaded) There was a fatal error moving the files to the History... ERROR:");
                ex.printStackTrace();
            }
        }
        List<World> worlds = Bukkit.getServer().getWorlds();
        for (World world : worlds) {
            Integer loadedChunkCount = Bukkit.getServer().getWorld(world.getName()).getLoadedChunks().length;
            methods.appendString("/Server Information/Chunks Loaded/" + world.getName() + "/", methods.getConfigFile().getString("chunks-loaded").replace("[count]", loadedChunkCount.toString()));
        }
    }

}
