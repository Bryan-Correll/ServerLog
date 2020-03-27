package com.bryan.log.server_info;

import com.bryan.log.ServerLog;
import com.bryan.log.server_log_api.ServerLogEvent;
import com.bryan.log.utils.Methods;
import org.bukkit.Bukkit;

import java.io.IOException;

public class PlayerCount {

    private Methods methods;

    public PlayerCount(ServerLog serverLog) {
        this.methods = new Methods(serverLog);
    }

    public void appendPlayerCount() throws IOException {

        Integer playerCount = Bukkit.getServer().getOnlinePlayers().size();

        ServerLogEvent logEvent = new ServerLogEvent(methods.getConfigFile().getString("player-count").replace("[count]", playerCount.toString()), methods.getTime(), methods.getDate(), "plugins/ServerLog/Server Information/Player Count/", "");
        Bukkit.getPluginManager().callEvent(logEvent);

        methods.appendString("/Server Information/Player Count/", methods.getConfigFile().getString("player-count").replace("[count]", playerCount.toString()));
        methods.appendString("/Compiled Log/", methods.getConfigFile().getString("player-count").replace("[count]", playerCount.toString()));
    }

}
