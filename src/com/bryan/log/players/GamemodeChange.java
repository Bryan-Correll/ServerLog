package com.bryan.log.players;

import com.bryan.log.ServerLog;
import com.bryan.log.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import java.io.IOException;

public class GamemodeChange implements Listener {
	
	private ServerLog serverLog;
	private Methods methods;
	public GamemodeChange(ServerLog serverLog) {
		this.serverLog = serverLog;
		this.methods = new Methods(serverLog);
	}
	
	@EventHandler
	public void onGamemodeChange(PlayerGameModeChangeEvent e) throws IOException {
		if (methods.dateChanged("/Players/Gamemode Change/")) {
			try {
				methods.moveToHistory();
			} catch (InvalidConfigurationException ex) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "There was a fatal error moving the files to the History...");
			}
		}
		methods.appendString("/Players/Gamemode Change/", methods.getConfigFile().getString("gamemode-change-event").replace("[player]", e.getPlayer().getName()).replace("[previous]", e.getPlayer().getGameMode().name()).replace("[now]", e.getNewGameMode().name()));
	}
}
