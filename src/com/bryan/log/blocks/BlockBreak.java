package com.bryan.log.blocks;

import com.bryan.log.ServerLog;
import com.bryan.log.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.io.IOException;

public class BlockBreak implements Listener {
	
	private ServerLog serverLog;
	private Methods methods;
	public BlockBreak(ServerLog serverLog) {
		this.serverLog = serverLog;
		this.methods = new Methods(serverLog);
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) throws IOException {
		if (methods.dateChanged("/Blocks/Block Break/")) {
			try {
				methods.moveToHistory();
			} catch (InvalidConfigurationException ex) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "There was a fatal error moving the files to the History...");
			}
		}
		String blockName = e.getBlock().getType().name();
		Integer x = e.getBlock().getX();
		Integer z = e.getBlock().getZ();
		Integer y = e.getBlock().getY();
		methods.appendString("/Blocks/Block Break/", methods.getConfigFile().getString("block-break-event").replace("[player]", e.getPlayer().getName()).replace("[block]", blockName).replace("[x]", x.toString()).replace("[y]", y.toString()).replace("[z]", z.toString()));
	}
	
}
