package com.bryan.log.type;

import com.bryan.log.ServerLog;
import com.bryan.log.server_log_api.ServerLogEvent;
import com.bryan.log.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.IOException;

public class AsyncPlayerChatEvents implements Listener {
	
	private ServerLog serverLog;
	private Methods methods;
	public AsyncPlayerChatEvents(ServerLog serverLog) {
		this.serverLog = serverLog;
		this.methods = new Methods(serverLog);
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) throws IOException {

		ServerLogEvent logEvent = new ServerLogEvent(methods.getConfigFile().getString("async-chat-event").replace("[player]", e.getPlayer().getName()).replace("[message]", ChatColor.stripColor(e.getMessage())), methods.getTime(), methods.getDate(), "plugins/ServerLog/Chat/", "AsyncPlayerChatEvent");
		Bukkit.getPluginManager().callEvent(logEvent);

		if (methods.dateChanged("/Chat/")) {
			try {
				methods.moveToHistory();
			} catch (InvalidConfigurationException ex) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "(Chat) There was a fatal error moving the files to the History... ERROR:");
				ex.printStackTrace();
			}
		}
		methods.appendString("/Chat/", methods.getConfigFile().getString("async-chat-event").replace("[player]", e.getPlayer().getName()).replace("[message]", ChatColor.stripColor(e.getMessage())));
		methods.appendString("/Compiled Log/", methods.getConfigFile().getString("async-chat-event").replace("[player]", e.getPlayer().getName()).replace("[message]", ChatColor.stripColor(e.getMessage())));
	}
	
}
