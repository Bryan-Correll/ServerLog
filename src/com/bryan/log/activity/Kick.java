package com.bryan.log.activity;

import com.bryan.log.ServerLog;
import com.bryan.log.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import java.io.IOException;

public class Kick implements Listener {

	private ServerLog serverLog;
	private Methods methods;
	public Kick(ServerLog serverLog) {
		this.serverLog = serverLog;
		this.methods = new Methods(serverLog);
	}

	@EventHandler
	public void onKick(PlayerKickEvent e) throws IOException {
		if (methods.dateChanged("/Activity/Player Kick/")) {
			try {
				methods.moveToHistory();
			} catch (InvalidConfigurationException ex) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "There was a fatal error moving the files to the History...");
			}
		}
		methods.appendString("/Activity/Player Kick/", methods.getConfigFile().getString("kick-event").replace("[player]", e.getPlayer().getName()).replace("[ip]", e.getPlayer().getAddress().getAddress().getHostAddress()).replace("[reason]", e.getReason()));
	}
}