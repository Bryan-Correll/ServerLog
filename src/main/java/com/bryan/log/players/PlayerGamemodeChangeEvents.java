package com.bryan.log.players;

import com.bryan.log.ServerLog;
import com.bryan.log.server_log_api.ServerLogEvent;
import com.bryan.log.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import java.io.IOException;

public class PlayerGamemodeChangeEvents implements Listener {

    private Methods methods;

    public PlayerGamemodeChangeEvents(ServerLog serverLog) {
        this.methods = new Methods(serverLog);
    }

    @EventHandler
    public void onGamemodeChange(PlayerGameModeChangeEvent e) throws IOException {

        ServerLogEvent logEvent = new ServerLogEvent(methods.getConfigFile().getString("gamemode-change-event").replace("[player]", e.getPlayer().getName()).replace("[previous]", e.getPlayer().getGameMode().name()).replace("[now]", e.getNewGameMode().name()), methods.getTime(), methods.getDate(), "plugins/ServerLog/Players/Gamemode Change/", "PlayerGameModeChangeEvent");
        Bukkit.getPluginManager().callEvent(logEvent);

        methods.appendString("/Players/Gamemode Change/", methods.getConfigFile().getString("gamemode-change-event").replace("[player]", e.getPlayer().getName()).replace("[previous]", e.getPlayer().getGameMode().name()).replace("[now]", e.getNewGameMode().name()));
        methods.appendString("/Compiled Log/", methods.getConfigFile().getString("gamemode-change-event").replace("[player]", e.getPlayer().getName()).replace("[previous]", e.getPlayer().getGameMode().name()).replace("[now]", e.getNewGameMode().name()));
    }
}