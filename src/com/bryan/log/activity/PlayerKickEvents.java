package com.bryan.log.activity;

import com.bryan.log.ServerLog;
import com.bryan.log.server_log_api.ServerLogEvent;
import com.bryan.log.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import java.io.IOException;

public class PlayerKickEvents implements Listener {

	private Methods methods;
	public PlayerKickEvents(ServerLog serverLog) {
		this.methods = new Methods(serverLog);
	}

	@EventHandler
	public void onKick(PlayerKickEvent e) throws IOException {
		ServerLogEvent logEvent = new ServerLogEvent(methods.getConfigFile().getString("kick-event").replace("[time]: ", "").replace("[player]", e.getPlayer().getName()).replace("[ip]", e.getPlayer().getAddress().getAddress().getHostAddress()).replace("[reason]", e.getReason()), methods.getTime(), methods.getDate(), "plugins/ServerLog/Activity/Player Kick/", "PlayerKickEvent");
		Bukkit.getPluginManager().callEvent(logEvent);

		methods.appendString("/Activity/Player Kick/", methods.getConfigFile().getString("kick-event").replace("[player]", e.getPlayer().getName()).replace("[ip]", e.getPlayer().getAddress().getAddress().getHostAddress()).replace("[reason]", e.getReason()));
		methods.appendString("/Compiled Log/", methods.getConfigFile().getString("kick-event").replace("[player]", e.getPlayer().getName()).replace("[ip]", e.getPlayer().getAddress().getAddress().getHostAddress()).replace("[reason]", e.getReason()));
	}
}