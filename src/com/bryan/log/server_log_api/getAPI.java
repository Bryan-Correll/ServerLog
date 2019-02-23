package com.bryan.log.server_log_api;

import com.bryan.log.ServerLog;
import com.bryan.log.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.IOException;

public class getAPI {

    private Methods methods;
    private ServerLog serverLog;
    public getAPI(ServerLog serverLog) {
        this.serverLog = serverLog;
        this.methods = new Methods(serverLog);
    }

    public void appendString(String directory, String configString) throws IOException {
        methods.appendString(directory, configString);
    }

    public void initiateDirectories() {
        try {
            methods.initiateFolders();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "There has been a fatal error in initiating the directories...");
        }
    }

}
