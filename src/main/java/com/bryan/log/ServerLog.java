package com.bryan.log;

import com.bryan.log.activity.PlayerJoinEvents;
import com.bryan.log.activity.PlayerKickEvents;
import com.bryan.log.activity.PlayerQuitEvents;
import com.bryan.log.apis.VouchersRedemptionEvents;
import com.bryan.log.blocks.BlockBreakEvents;
import com.bryan.log.blocks.BlockPlaceEvents;
import com.bryan.log.blocks.EmptyBucketEvents;
import com.bryan.log.items.PlayerDropItemEvents;
import com.bryan.log.items.PlayerPickupItemsEvents;
import com.bryan.log.players.*;
import com.bryan.log.server_info.*;
import com.bryan.log.server_log_api.getAPI;
import com.bryan.log.type.AsyncPlayerChatEvents;
import com.bryan.log.type.CommandPreprocessCommandEvents;
import com.bryan.log.utils.Methods;
import com.bryan.log.utils.Metrics;
import com.bryan.log.utils.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class ServerLog extends JavaPlugin {

    private ChunksLoaded chunksLoaded;
    private EntityCount entityCount;
    private PlayerCount playerCount;
    private TPS tps;
    private RamUsage ramUsage;
    private UpdateChecker updater;
    private Methods methods;

    public getAPI getAPI;

    private PluginManager pluginManager = Bukkit.getPluginManager();
    private ConsoleCommandSender commandSender = Bukkit.getConsoleSender();

    @Override
    public void onEnable() {

        getAPI = new getAPI(this);

        chunksLoaded = new ChunksLoaded(this);
        entityCount = new EntityCount(this);
        playerCount = new PlayerCount(this);
        tps = new TPS(this);
        ramUsage = new RamUsage(this);
        updater = new UpdateChecker(this);
        methods = new Methods(this);

        saveDefaultConfig();
        reloadConfig();

        Metrics metrics = new Metrics(this);
        metrics.addCustomChart(new Metrics.SimplePie("used_language", () -> getConfig().getString("lang", "en")));

        updater.checkForUpdate();

        commandSender.sendMessage(methods.color("&f[Server Log]: &aThank you for choosing Server Log to log your server's functions."));
        commandSender.sendMessage(methods.color("&f[Server Log]: &aThe current version you are using is &2" + getDescription().getFullName()));
        commandSender.sendMessage(methods.color("&f[Server Log]: &aThis is the full version of Server Log, so all features are enabled."));

        if (!methods.bStatsEnabled()) {
            commandSender.sendMessage(methods.color("&f[Server Log]: &4&lbStats is disabled!"));
            commandSender.sendMessage(methods.color("&cIn the bStats folder, the boolean ''enabled'' in the config.yml is false."));
            commandSender.sendMessage(methods.color("&cI ask that this boolean be set to ''true'' to help me and other developers"));
            commandSender.sendMessage(methods.color("&clearn helpful information about the community who uses the plugin. The only"));
            commandSender.sendMessage(methods.color("&cinformation we the developers learn about the server is what is on the bStats"));
            commandSender.sendMessage(methods.color("&cpage."));
            commandSender.sendMessage(methods.color("&cThis includes:"));
            commandSender.sendMessage(methods.color("&c   The amount of servers using the plugin (No IP's)."));
            commandSender.sendMessage(methods.color("&c   The amount of servers that are online the plugin is on."));
            commandSender.sendMessage(methods.color("&c   The amount of players on the servers using the plugin."));
            commandSender.sendMessage(methods.color("&c   The country the server is based out of (Not exact locations)."));
            commandSender.sendMessage(methods.color("&c   The used language (For my plugin to see the language file used)."));
            commandSender.sendMessage(methods.color("&c   The Minecraft version."));
            commandSender.sendMessage(methods.color("&c   The Software version (Spigot/Bukkit/Paper/etc)."));
            commandSender.sendMessage(methods.color("&c   Plugin version."));
            commandSender.sendMessage(methods.color("&c   The server Core count (The amount of cores the server is using)."));
            commandSender.sendMessage(methods.color("&c   The server Java version."));
            commandSender.sendMessage(methods.color("&c   The server Operating System (Windows/Linux/etc)."));
            commandSender.sendMessage(methods.color("&c   The server Arch."));
            commandSender.sendMessage(methods.color("&cNone of the information shared to bStats is confidential, dangerous or"));
            commandSender.sendMessage(methods.color("&cpersonal in any way. This just helps the developers of the plugins you"));
            commandSender.sendMessage(methods.color("&cuse to make updates to the plugin that will help with performance, user"));
            commandSender.sendMessage(methods.color("&ccapabilities, or other features to implement."));
            commandSender.sendMessage(methods.color("&cTL;DR: Set the ''enabled'' boolean in the bStats config to true to remove this message."));
        } else {
            commandSender.sendMessage(methods.color("&f[Server Log]: &2&lbStats is enabled!"));
        }

        try {
            methods.initiateFolders();
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        registerAPIS();

        registerEvents();

        serverInfo();

    }

    private void registerAPIS() {
        if (pluginManager.isPluginEnabled("Vouchers")) {
            if (getConfig().getBoolean("vouchers-api")) {
                pluginManager.registerEvents(new VouchersRedemptionEvents(this), this);
                commandSender.sendMessage(methods.color("&f[Server Log]: &6The plugin 'Vouchers' API has been enabled and is logging voucher redemption."));
            }
        }
    }

    private void registerEvents() {
        pluginManager.registerEvents(new PlayerJoinEvents(this), this);
        pluginManager.registerEvents(new PlayerQuitEvents(this), this);
        pluginManager.registerEvents(new PlayerKickEvents(this), this);
        pluginManager.registerEvents(new BlockPlaceEvents(this), this);
        pluginManager.registerEvents(new BlockBreakEvents(this), this);
        pluginManager.registerEvents(new EmptyBucketEvents(this), this);
        pluginManager.registerEvents(new AsyncPlayerChatEvents(this), this);
        pluginManager.registerEvents(new CommandPreprocessCommandEvents(this), this);
        pluginManager.registerEvents(new PlayerDropItemEvents(this), this);
        pluginManager.registerEvents(new PlayerPickupItemsEvents(this), this);
        pluginManager.registerEvents(new PlayerDeathEvents(this), this);
        pluginManager.registerEvents(new PlayerRespawnEvents(this), this);
        pluginManager.registerEvents(new PlayerGamemodeChangeEvents(this), this);
        pluginManager.registerEvents(new PlayerTeleportEvents(this), this);
        pluginManager.registerEvents(new PlayerEggSpawnEvents(this), this);
    }

    private void serverInfo() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            try {
                tps.appendTPS();
                ramUsage.appendRamUsage();
                chunksLoaded.appendChunksLoaded();
                entityCount.appendEntityCount();
                playerCount.appendPlayerCount();
            } catch (IOException e) {
                commandSender.sendMessage(ChatColor.DARK_RED + "(Server Information) There was a fatal error saving server info..." + "ERROR:");
                e.printStackTrace();
            }
        }, 0, (long) getConfig().getDouble("server-info-delay") * 60L * 20L);
    }

}
