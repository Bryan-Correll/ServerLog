package com.bryan.log.type;

import com.bryan.log.ServerLog;
import com.bryan.log.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.IOException;

public class Chat implements Listener {
	
	private ServerLog serverLog;
	private Methods methods;
	public Chat(ServerLog serverLog) {
		this.serverLog = serverLog;
		this.methods = new Methods(serverLog);
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) throws IOException {
		if (methods.dateChanged("/Chat/")) {
			try {
				methods.moveToHistory();
			} catch (InvalidConfigurationException ex) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "There was a fatal error moving the files to the History...");
			}
		}
		methods.appendString("/Chat/", methods.getConfigFile().getString("async-chat-event").replace("[player]", e.getPlayer().getName()).replace("[message]", ChatColor.stripColor(e.getMessage())));
	}
	
}
