package com.bryan.log.items;

import com.bryan.log.ServerLog;
import com.bryan.log.server_log_api.ServerLogEvent;
import com.bryan.log.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class PlayerDropItemEvents implements Listener {
	
	private ServerLog serverLog;
	private Methods methods;
	public PlayerDropItemEvents(ServerLog serverLog) {
		this.serverLog = serverLog;
		this.methods = new Methods(serverLog);
	}
	
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e) throws IOException {

		String blockName;
		ItemStack item = e.getItemDrop().getItemStack();
		if (item.hasItemMeta()) {
			if (item.getItemMeta().hasDisplayName()) {
				blockName = ChatColor.stripColor(item.getItemMeta().getDisplayName()) + " (" + item.getType().name() + ")";
			} else{
				blockName = item.getType().name();
			}
		} else {
			blockName = item.getType().name();
		}

		ServerLogEvent logEvent = new ServerLogEvent(methods.getConfigFile().getString("drop-item-event").replace("[time]: ", "").replace("[player]", e.getPlayer().getName()).replace("[name]", blockName), methods.getTime(), methods.getDate(), "plugins/ServerLog/Items/Item Drop/", "PlayerDropItemEvent");
		Bukkit.getPluginManager().callEvent(logEvent);

		if (methods.dateChanged("/Items/Item Drop/")) {
			try {
				methods.moveToHistory();
			} catch (InvalidConfigurationException ex) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "There was a fatal error moving the files to the History...");
			}
		}

		methods.appendString("/Items/Item Drop/", methods.getConfigFile().getString("drop-item-event").replace("[player]", e.getPlayer().getName()).replace("[name]", blockName));
		methods.appendString("/Compiled Log/", methods.getConfigFile().getString("drop-item-event").replace("[player]", e.getPlayer().getName()).replace("[name]", blockName));
	}
	
}
