package com.bryan.log.players;

import com.bryan.log.ServerLog;
import com.bryan.log.server_log_api.ServerLogEvent;
import com.bryan.log.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.io.IOException;

public class PlayerRespawnEvents implements Listener {
	
	private Methods methods;
	public PlayerRespawnEvents(ServerLog serverLog) {
		this.methods = new Methods(serverLog);
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) throws IOException {

		ServerLogEvent logEvent = new ServerLogEvent(methods.getConfigFile().getString("respawn-event").replace("[player]", e.getPlayer().getName()), methods.getTime(), methods.getDate(), "plugins/ServerLog/Players/Respawn/", "PlayerRespawnEvent");
		Bukkit.getPluginManager().callEvent(logEvent);

		methods.appendString("/Players/Respawn/", methods.getConfigFile().getString("respawn-event").replace("[player]", e.getPlayer().getName()));
		methods.appendString("/Compiled Log/", methods.getConfigFile().getString("respawn-event").replace("[player]", e.getPlayer().getName()));
	}
	
}
