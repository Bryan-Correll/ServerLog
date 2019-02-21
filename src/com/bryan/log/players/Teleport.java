package com.bryan.log.players;

import com.bryan.log.ServerLog;
import com.bryan.log.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.io.IOException;

public class Teleport implements Listener {
	
	private ServerLog serverLog;
	private Methods methods;
	public Teleport(ServerLog serverLog) {
		this.serverLog = serverLog;
		this.methods = new Methods(serverLog);
	}
	
	@EventHandler
	public void onTeleport(PlayerTeleportEvent e) throws IOException {
		if (methods.dateChanged("/Players/Teleport/")) {
			try {
				methods.moveToHistory();
			} catch (InvalidConfigurationException ex) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "There was a fatal error moving the files to the History...");
			}
		}
		Location toLoc = e.getTo();
		Integer x = toLoc.getBlockX();
		Integer y = toLoc.getBlockY();
		Integer z = toLoc.getBlockZ();
		String loc = x + ", " + y + ", " + z;
		methods.appendString("/Players/Teleport/", methods.getConfigFile().getString("teleport-event").replace("[player]", e.getPlayer().getName()).replace("[location]", loc));
	}
	
}
