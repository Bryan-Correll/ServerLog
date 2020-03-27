package com.bryan.log.type;

import com.bryan.log.ServerLog;
import com.bryan.log.server_log_api.ServerLogEvent;
import com.bryan.log.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.io.IOException;

public class CommandPreprocessCommandEvents implements Listener {

    private Methods methods;
    private ServerLog serverLog;

    public CommandPreprocessCommandEvents(ServerLog serverLog) {
        this.methods = new Methods(serverLog);
        this.serverLog = serverLog;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) throws IOException {
        String command;
        if(e.getMessage().contains(" ")) {
            command = e.getMessage().substring(0, e.getMessage().indexOf(" "));
        } else {
            command = e.getMessage();
        }

        if(!serverLog.getConfig().getStringList("blocked-commands").contains(command)) {
            System.out.println(command);
            ServerLogEvent logEvent = new ServerLogEvent(methods.getConfigFile().getString("command-preprocess-event").replace("[player]", e.getPlayer().getName()).replace("[command]", ChatColor.stripColor(e.getMessage())), methods.getTime(), methods.getDate(), "plugins/ServerLog/Commands/", "PlayerCommandPreprocessEvent");
            Bukkit.getPluginManager().callEvent(logEvent);

            methods.appendString("/Commands/", methods.getConfigFile().getString("command-preprocess-event").replace("[player]", e.getPlayer().getName()).replace("[command]", ChatColor.stripColor(e.getMessage())));
            methods.appendString("/Compiled Log/", methods.getConfigFile().getString("command-preprocess-event").replace("[player]", e.getPlayer().getName()).replace("[command]", ChatColor.stripColor(e.getMessage())));
        }
    }

}
