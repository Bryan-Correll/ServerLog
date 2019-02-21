package com.bryan.log.items;

import com.bryan.log.ServerLog;
import com.bryan.log.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class ItemPickup implements Listener {
	
	private ServerLog serverLog;
	private Methods methods;
	public ItemPickup(ServerLog serverLog) {
		this.serverLog = serverLog;
		this.methods = new Methods(serverLog);
	}
	
	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent e) throws IOException {
		if (methods.dateChanged("/Items/Item Pickup/")) {
			try {
				methods.moveToHistory();
			} catch (InvalidConfigurationException ex) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "There was a fatal error moving the files to the History...");
			}
		}
		String blockName;
		ItemStack item = e.getItem().getItemStack();
		if (item.hasItemMeta()) {
			if (item.getItemMeta().hasDisplayName()) {
				blockName = ChatColor.stripColor(item.getItemMeta().getDisplayName()) + " (" + item.getType().name() + ")";
			} else{
				blockName = item.getType().name();
			}
		} else {
			blockName = item.getType().name();
		}
		methods.appendString("/Items/Item Pickup/", methods.getConfigFile().getString("pickup-item-event").replace("[player]", e.getPlayer().getName()).replace("[name]", blockName));
	}
	
}
