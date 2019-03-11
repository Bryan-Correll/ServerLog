package com.bryan.log.activity;

import com.bryan.log.ServerLog;
import com.bryan.log.server_log_api.ServerLogEvent;
import com.bryan.log.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;

public class PlayerJoinEvents implements Listener {
	
	private ServerLog serverLog;
	private Methods methods;

	public PlayerJoinEvents(ServerLog serverLog) {
		this.serverLog = serverLog;
		this.methods = new Methods(serverLog);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) throws IOException {
		ServerLogEvent logEvent = new ServerLogEvent(methods.getConfigFile().getString("join-event").replace("[time]: ", "").replace("[player]", e.getPlayer().getName()).replace("[ip]", e.getPlayer().getAddress().getAddress().getHostAddress()), methods.getTime(), methods.getDate(), "plugins/ServerLog/Activity/Player Join/", "PlayerJoinEvent");
		Bukkit.getPluginManager().callEvent(logEvent);
		if (methods.dateChanged("/Activity/Player Join/")) {
			try {
				methods.moveToHistory();
			} catch (InvalidConfigurationException ex) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "(Player Join) There was a fatal error moving the files to the History... ERROR:");
				ex.printStackTrace();
			}
		}
		methods.appendString("/Activity/Player Join/", methods.getConfigFile().getString("join-event").replace("[player]", e.getPlayer().getName()).replace("[ip]", e.getPlayer().getAddress().getAddress().getHostAddress()));
		methods.appendString("/Compiled Log/", methods.getConfigFile().getString("join-event").replace("[player]", e.getPlayer().getName()).replace("[ip]", e.getPlayer().getAddress().getAddress().getHostAddress()));
	}

}
