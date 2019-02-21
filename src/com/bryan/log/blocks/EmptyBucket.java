package com.bryan.log.blocks;

import com.bryan.log.ServerLog;
import com.bryan.log.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import java.io.IOException;

public class EmptyBucket implements Listener {
	
	private ServerLog serverLog;
	private Methods methods;
	public EmptyBucket(ServerLog serverLog) {
		this.serverLog = serverLog;
		this.methods = new Methods(serverLog);
	}
	
	@EventHandler
	public void onEmptyBucket(PlayerBucketEmptyEvent e) throws IOException {
		if (methods.dateChanged("/Blocks/Empty Bucket/")) {
			try {
				methods.moveToHistory();
			} catch (InvalidConfigurationException ex) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "There was a fatal error moving the files to the History...");
			}
		}
		Integer x = e.getBlockClicked().getX();
		Integer y = e.getBlockClicked().getY();
		Integer z = e.getBlockClicked().getZ();

		String bucketName;
		if (e.getPlayer().getItemInHand().hasItemMeta()) {
			if (e.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) {
				bucketName = ChatColor.stripColor(e.getPlayer().getItemInHand().getItemMeta().getDisplayName()) + " (" + e.getPlayer().getItemInHand().getType().name() + ")";
			} else {
				bucketName = e.getPlayer().getItemInHand().getType().name();
			}
		} else {
			bucketName = e.getPlayer().getItemInHand().getType().name();
		}
		methods.appendString("/Blocks/Empty Bucket/", methods.getConfigFile().getString("empty-bucket-event").replace("[player]", e.getPlayer().getName()).replace("[bucket]", bucketName).replace("[x]", x.toString()).replace("[y]", y.toString()).replace("[z]", z.toString()));
	}
	
}
