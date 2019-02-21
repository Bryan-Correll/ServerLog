package com.bryan.log.server_info;

import com.bryan.log.ServerLog;
import com.bryan.log.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.IOException;
import java.util.List;

public class EntityCount {

    private ServerLog serverLog;
    private Methods methods;
    public EntityCount(ServerLog serverLog) {
        this.serverLog = serverLog;
        this.methods = new Methods(serverLog);
    }

    public void appendEntityCount() throws IOException {
        if (methods.dateChanged("/Server Information/Entity Count/")) {
            try {
                methods.moveToHistory();
            } catch (InvalidConfigurationException ex) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "There was a fatal error moving the files to the History...");
            }
        }
        List<World> worlds = Bukkit.getServer().getWorlds();
        for (World world : worlds) {
            Integer entityCount = Bukkit.getServer().getWorld(world.getName()).getEntities().size();
            methods.appendString("/Server Information/Entity Count/" + world.getName() + "/", methods.getConfigFile().getString("entity-count").replace("[count]", entityCount.toString()));
        }
    }

}
