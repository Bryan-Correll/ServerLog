package com.bryan.log.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class UpdateChecker {

    private final JavaPlugin javaPlugin;
    private final String localPluginVersion;
    private String spigotPluginVersion;

    //Constants. Customize to your liking.
    private static final int ID = 64978; //The ID of your resource. Can be found in the resource URL.
    private static final String UPDATE_MSG = "&f[Server Log]: &eA new update is available at: &6https://www.spigotmc.org/resources/" + ID + "/updates";
    private static final Permission UPDATE_PERM = new Permission("serverlog.update", PermissionDefault.TRUE);

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
//                        cancel();
                return;
            }

            if (localPluginVersion.equals(spigotPluginVersion)) { return; }
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', UPDATE_MSG));

            Bukkit.getScheduler().runTask(javaPlugin, () -> Bukkit.getPluginManager().registerEvents(new Listener() {
                @EventHandler(priority = EventPriority.MONITOR)
                public void onPlayerJoin(final PlayerJoinEvent event) {
                    final Player player = event.getPlayer();
                    if (player.hasPermission(UPDATE_PERM)) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', UPDATE_MSG));
                    }
                }
            }, javaPlugin));
        }));
    }
}