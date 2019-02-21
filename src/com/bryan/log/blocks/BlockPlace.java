package com.bryan.log.blocks;

import com.bryan.log.ServerLog;
import com.bryan.log.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.io.IOException;

public class BlockPlace implements Listener{
	
	private ServerLog serverLog;
	private Methods methods;
	public BlockPlace(ServerLog serverLog) {
		this.serverLog = serverLog;
		this.methods = new Methods(serverLog);
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) throws IOException {
		if (methods.dateChanged("/Blocks/Block Place/")) {
			try {
				methods.moveToHistory();
			} catch (InvalidConfigurationException ex) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "There was a fatal error moving the files to the History...");
			}
		}
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
		methods.appendString("/Blocks/Block Place/", methods.getConfigFile().getString("block-place-event").replace("[player]", e.getPlayer().getName()).replace("[block]", blockName).replace("[x]", x.toString()).replace("[y]", y.toString()).replace("[z]", z.toString()));
	}
	
}
