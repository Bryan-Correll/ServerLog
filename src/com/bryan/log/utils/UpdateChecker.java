package com.bryan.log.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class UpdateChecker {

    private final JavaPlugin javaPlugin;
    private final String localPluginVersion;
    private String spigotPluginVersion;

    private static final int ID = 65510; //The ID of your resource. Can be found in the resource URL.
    public static final String UPDATE_MSG = "&f[Server Log]: &eA new update is available at: &6https://www.spigotmc.org/resources/" + ID + "/updates";

    public UpdateChecker(final JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
        this.localPluginVersion = javaPlugin.getDescription().getVersion();
    }

    public void checkForUpdate() {
        Bukkit.getScheduler().runTaskAsynchronously(javaPlugin, () -> Bukkit.getScheduler().runTask(javaPlugin, () -> {
            try {
                final HttpsURLConnection connection = (HttpsURLConnection) new URL("https://api.spigotmc.org/legacy/update.php?resource=" + ID).openConnection();
                connection.setRequestMethod("GET");
                spigotPluginVersion = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
            } catch (final IOException e) {
                e.printStackTrace();
                return;
            }

            if (localPluginVersion.equals(spigotPluginVersion)) { return; }
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', UPDATE_MSG));
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&f[Server Log]: &eThe current version is " + localPluginVersion + " and the new version is " + spigotPluginVersion + "!"));

            Bukkit.getScheduler().runTask(javaPlugin, () -> Bukkit.getPluginManager().registerEvents(new Listener() {
                @EventHandler(priority = EventPriority.MONITOR)
                public void onPlayerJoin(final PlayerJoinEvent event) {
                    final Player player = event.getPlayer();
                    if (player.isOp()) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', UPDATE_MSG));
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f[Server Log]: &eThe current version is " + localPluginVersion + " and the new version is " + spigotPluginVersion + "!"));
                    }
                }
            }, javaPlugin));
        }));
    }
}