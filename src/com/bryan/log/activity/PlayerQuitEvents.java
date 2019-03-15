package com.bryan.log.activity;

import com.bryan.log.ServerLog;
import com.bryan.log.server_log_api.ServerLogEvent;
import com.bryan.log.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;

public class PlayerQuitEvents implements Listener {
	
	private Methods methods;
	public PlayerQuitEvents(ServerLog serverLog) {
		this.methods = new Methods(serverLog);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) throws IOException {
		Integer x = e.getPlayer().getLocation().getBlockX();
		Integer y = e.getPlayer().getLocation().getBlockY();
		Integer z = e.getPlayer().getLocation().getBlockZ();

		ServerLogEvent logEvent = new ServerLogEvent(methods.getConfigFile().getString("quit-event").replace("[time]: ", "").replace("[player]", e.getPlayer().getName()).replace("[ip]", e.getPlayer().getAddress().getAddress().getHostAddress()).replace("[x]", x.toString()).replace("[y]", y.toString()).replace("[z]", z.toString()), methods.getTime(), methods.getDate(), "plugins/ServerLog/Activity/Player Quit/", "PlayerQuitEvent");
		Bukkit.getPluginManager().callEvent(logEvent);

		methods.appendString("/Activity/Player Quit/", methods.getConfigFile().getString("quit-event").replace("[player]", e.getPlayer().getName()).replace("[ip]", e.getPlayer().getAddress().getAddress().getHostAddress()).replace("[x]", x.toString()).replace("[y]", y.toString()).replace("[z]", z.toString()));
		methods.appendString("/Compiled Log/", methods.getConfigFile().getString("quit-event").replace("[player]", e.getPlayer().getName()).replace("[ip]", e.getPlayer().getAddress().getAddress().getHostAddress()).replace("[x]", x.toString()).replace("[y]", y.toString()).replace("[z]", z.toString()));
	}
}
