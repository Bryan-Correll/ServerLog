package com.bryan.log.activity;

import com.bryan.log.ServerLog;
import com.bryan.log.server_log_api.ServerLogEvent;
import com.bryan.log.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import java.io.IOException;

public class PlayerKickEvents implements Listener {

	private ServerLog serverLog;
	private Methods methods;
	public PlayerKickEvents(ServerLog serverLog) {
		this.serverLog = serverLog;
		this.methods = new Methods(serverLog);
	}

	@EventHandler
	public void onKick(PlayerKickEvent e) throws IOException {
		ServerLogEvent logEvent = new ServerLogEvent(methods.getConfigFile().getString("kick-event").replace("[time]: ", "").replace("[player]", e.getPlayer().getName()).replace("[ip]", e.getPlayer().getAddress().getAddress().getHostAddress()).replace("[reason]", e.getReason()), methods.getTime(), methods.getDate(), "plugins/ServerLog/Activity/Player Kick/", "PlayerKickEvent");
		Bukkit.getPluginManager().callEvent(logEvent);
		if (methods.dateChanged("/Activity/Player Kick/")) {
			try {
				methods.moveToHistory();
			} catch (InvalidConfigurationException ex) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "(Player Kick) There was a fatal error moving the files to the History... ERROR:");
				ex.printStackTrace();
			}
		}
		methods.appendString("/Activity/Player Kick/", methods.getConfigFile().getString("kick-event").replace("[player]", e.getPlayer().getName()).replace("[ip]", e.getPlayer().getAddress().getAddress().getHostAddress()).replace("[reason]", e.getReason()));
		methods.appendString("/Compiled Log/", methods.getConfigFile().getString("kick-event").replace("[player]", e.getPlayer().getName()).replace("[ip]", e.getPlayer().getAddress().getAddress().getHostAddress()).replace("[reason]", e.getReason()));
	}
}