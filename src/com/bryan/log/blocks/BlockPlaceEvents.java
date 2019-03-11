package com.bryan.log.blocks;

import com.bryan.log.ServerLog;
import com.bryan.log.server_log_api.ServerLogEvent;
import com.bryan.log.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.io.IOException;

public class BlockPlaceEvents implements Listener{
	
	private ServerLog serverLog;
	private Methods methods;
	public BlockPlaceEvents(ServerLog serverLog) {
		this.serverLog = serverLog;
		this.methods = new Methods(serverLog);
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) throws IOException {

		Integer x = e.getBlock().getX();
		Integer y = e.getBlock().getY();
		Integer z = e.getBlock().getZ();

		String blockName;
		if (e.getItemInHand().hasItemMeta()) {
			if (e.getItemInHand().getItemMeta().hasDisplayName()) {
				blockName = ChatColor.stripColor(e.getItemInHand().getItemMeta().getDisplayName()) + " (" + e.getBlock().getType().name() + ")";
			} else {
				blockName = e.getBlock().getType().name();
			}
		} else {
			blockName = e.getBlock().getType().name();
		}

		ServerLogEvent logEvent = new ServerLogEvent(methods.getConfigFile().getString("block-place-event").replace("[time]: ", "").replace("[player]", e.getPlayer().getName()).replace("[block]", blockName).replace("[x]", x.toString()).replace("[y]", y.toString()).replace("[z]", z.toString()), methods.getTime(), methods.getDate(), "plugins/ServerLog/Blocks/Block Place/", "BlockPlaceEvent");
		Bukkit.getPluginManager().callEvent(logEvent);

		if (methods.dateChanged("/Blocks/Block Place/")) {
			try {
				methods.moveToHistory();
			} catch (InvalidConfigurationException ex) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "There was a fatal error moving the files to the History...");
			}
		}
		methods.appendString("/Blocks/Block Place/", methods.getConfigFile().getString("block-place-event").replace("[player]", e.getPlayer().getName()).replace("[block]", blockName).replace("[x]", x.toString()).replace("[y]", y.toString()).replace("[z]", z.toString()));
		methods.appendString("/Compiled Log/", methods.getConfigFile().getString("block-place-event").replace("[player]", e.getPlayer().getName()).replace("[block]", blockName).replace("[x]", x.toString()).replace("[y]", y.toString()).replace("[z]", z.toString()));
	}
	
}
