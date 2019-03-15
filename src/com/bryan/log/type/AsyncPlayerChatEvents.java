package com.bryan.log.type;

import com.bryan.log.ServerLog;
import com.bryan.log.server_log_api.ServerLogEvent;
import com.bryan.log.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.IOException;

public class AsyncPlayerChatEvents implements Listener {
	
	private Methods methods;
	public AsyncPlayerChatEvents(ServerLog serverLog) {
		this.methods = new Methods(serverLog);
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) throws IOException {

		ServerLogEvent logEvent = new ServerLogEvent(methods.getConfigFile().getString("async-chat-event").replace("[player]", e.getPlayer().getName()).replace("[message]", ChatColor.stripColor(e.getMessage())), methods.getTime(), methods.getDate(), "plugins/ServerLog/Chat/", "AsyncPlayerChatEvent");
		Bukkit.getPluginManager().callEvent(logEvent);

		methods.appendString("/Chat/", methods.getConfigFile().getString("async-chat-event").replace("[player]", e.getPlayer().getName()).replace("[message]", ChatColor.stripColor(e.getMessage())));
		methods.appendString("/Compiled Log/", methods.getConfigFile().getString("async-chat-event").replace("[player]", e.getPlayer().getName()).replace("[message]", ChatColor.stripColor(e.getMessage())));
	}
	
}
