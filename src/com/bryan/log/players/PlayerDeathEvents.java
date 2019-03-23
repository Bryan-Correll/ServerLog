package com.bryan.log.players;

import com.bryan.log.ServerLog;
import com.bryan.log.server_log_api.ServerLogEvent;
import com.bryan.log.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class PlayerDeathEvents implements Listener {
	
	private Methods methods;
	public PlayerDeathEvents(ServerLog serverLog) {
		this.methods = new Methods(serverLog);
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) throws IOException {

		String bukkitVersion = Bukkit.getBukkitVersion();

		String reason = e.getEntity().getLastDamageCause().getCause().name();
		if (reason.equalsIgnoreCase("ENTITY_ATTACK")) {
			String weapon;
			ItemStack item;
			if (bukkitVersion.contains("1.9") || bukkitVersion.contains("1.10") || bukkitVersion.contains("1.11") || bukkitVersion.contains("1.12") || bukkitVersion.contains("1.13")) {
				item = e.getEntity().getKiller().getInventory().getItemInMainHand();
			} else {
				item = e.getEntity().getKiller().getItemInHand();
			}
			if (item.hasItemMeta()) {
				if (item.getItemMeta().hasDisplayName()) {
					weapon = ChatColor.stripColor(item.getItemMeta().getDisplayName()) + " (" + item.getType().name() + ")";
				} else{
					weapon = item.getType().name();
				}
			} else {
				weapon = item.getType().name();
			}
			reason = reason + " by " + e.getEntity().getKiller().getName() + " with " + weapon;
		}

		ServerLogEvent logEvent = new ServerLogEvent(methods.getConfigFile().getString("death-event").replace("[time]: ", "").replace("[player]", e.getEntity().getName()).replace("[reason]", reason), methods.getTime(), methods.getDate(), "plugins/ServerLog/Players/Death/", "PlayerDeathEvent");
		Bukkit.getPluginManager().callEvent(logEvent);

		methods.appendString("/Players/Death/", methods.getConfigFile().getString("death-event").replace("[player]", e.getEntity().getName()).replace("[reason]", reason));
		methods.appendString("/Compiled Log/", methods.getConfigFile().getString("death-event").replace("[player]", e.getEntity().getName()).replace("[reason]", reason));
	}
	
}
