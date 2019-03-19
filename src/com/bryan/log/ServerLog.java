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
import org.bukkit.configuration.InvalidConfigurationException;
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

        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage(methods.color("&f[Server Log]: &aThank you for choosing Server Log to log your server's functions."));
        Bukkit.getConsoleSender().sendMessage(methods.color("&f[Server Log]: &aThe current version you are using is &2" + getDescription().getFullName()));
        Bukkit.getConsoleSender().sendMessage(methods.color("&f[Server Log]: &aThis is the full version of Server Log, so all features are enabled."));
        updater.checkForUpdate();
        Bukkit.getConsoleSender().sendMessage("");

        try {
            methods.initiateFolders();
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        serverInfo();

        Bukkit.getPluginManager().registerEvents(new PlayerJoinEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerKickEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new EmptyBucketEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new AsyncPlayerChatEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new CommandPreprocessCommandEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDropItemEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerPickupItemsEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerRespawnEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerGamemodeChangeEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerTeleportEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerEggSpawnEvents(this), this);

        if (Bukkit.getPluginManager().isPluginEnabled("Vouchers")) {
            if (getConfig().getBoolean("vouchers-api")) {
                Bukkit.getPluginManager().registerEvents(new VouchersRedemptionEvents(this), this);
                Bukkit.getConsoleSender().sendMessage(methods.color("&f[Server Log]: &6The plugin 'Vouchers' API has been enabled and is logging voucher redemption."));
            }
        }

        Metrics metrics = new Metrics(this);
        metrics.addCustomChart(new Metrics.SimplePie("used_language", () -> getConfig().getString("lang", "en")));

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
                Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "(Server Information) There was a fatal error saving server info..." + "ERROR:");
                e.printStackTrace();
            }
        }, 0, (long) getConfig().getDouble("server-info-delay") * 60L * 20L);
    }

}
