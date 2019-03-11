package com.bryan.log.players;

import com.bryan.log.ServerLog;
import com.bryan.log.server_log_api.ServerLogEvent;
import com.bryan.log.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class Death implements Listener {
	
	private ServerLog serverLog;
	private Methods methods;
	public Death(ServerLog serverLog) {
		this.serverLog = serverLog;
		this.methods = new Methods(serverLog);
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) throws IOException {

		String reason = e.getEntity().getLastDamageCause().getCause().name();
		if (reason.equalsIgnoreCase("ENTITY_ATTACK")) {
			String weapon;
			ItemStack item = e.getEntity().getKiller().getItemInHand();
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

		if (methods.dateChanged("/Players/Death/")) {
			try {
				methods.moveToHistory();
			} catch (InvalidConfigurationException ex) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "There was a fatal error moving the files to the History...");
			}
		}
		methods.appendString("/Players/Death/", methods.getConfigFile().getString("death-event").replace("[player]", e.getEntity().getName()).replace("[reason]", reason));
	}
	
}
