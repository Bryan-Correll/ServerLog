package com.bryan.log.players;

import com.bryan.log.ServerLog;
import com.bryan.log.server_log_api.ServerLogEvent;
import com.bryan.log.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.io.IOException;

public class PlayerTeleportEvents implements Listener {
	
	private Methods methods;
	public PlayerTeleportEvents(ServerLog serverLog) {
		this.methods = new Methods(serverLog);
	}
	
	@EventHandler
	public void onTeleport(PlayerTeleportEvent e) throws IOException {

		Location toLoc = e.getTo();
		Integer x = toLoc.getBlockX();
		Integer y = toLoc.getBlockY();
		Integer z = toLoc.getBlockZ();
		String loc = x + ", " + y + ", " + z;

		ServerLogEvent logEvent = new ServerLogEvent(methods.getConfigFile().getString("teleport-event").replace("[player]", e.getPlayer().getName()).replace("[location]", loc), methods.getTime(), methods.getDate(), "plugins/ServerLog/Players/Teleport/", "PlayerTeleportEvent");
		Bukkit.getPluginManager().callEvent(logEvent);

		methods.appendString("/Players/Teleport/", methods.getConfigFile().getString("teleport-event").replace("[player]", e.getPlayer().getName()).replace("[location]", loc));
		methods.appendString("/Compiled Log/", methods.getConfigFile().getString("teleport-event").replace("[player]", e.getPlayer().getName()).replace("[location]", loc));
	}
	
}
