package com.bryan.log.players;

import java.io.IOException;

import com.bryan.log.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.bryan.log.ServerLog;

public class Respawn implements Listener {
	
	private ServerLog serverLog;
	private Methods methods;
	public Respawn(ServerLog serverLog) {
		this.serverLog = serverLog;
		this.methods = new Methods(serverLog);
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) throws IOException {
		if (methods.dateChanged("/Players/Respawn/")) {
			try {
				methods.moveToHistory();
			} catch (InvalidConfigurationException ex) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "There was a fatal error moving the files to the History...");
			}
		}
		methods.appendString("/Players/Respawn/", methods.getConfigFile().getString("respawn-event").replace("[player]", e.getPlayer().getName()));
	}
	
}
