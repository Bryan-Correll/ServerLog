package com.bryan.log;

import com.bryan.log.activity.Join;
import com.bryan.log.activity.Kick;
import com.bryan.log.activity.Quit;
import com.bryan.log.apis.Vouchers;
import com.bryan.log.blocks.BlockBreak;
import com.bryan.log.blocks.BlockPlace;
import com.bryan.log.blocks.EmptyBucket;
import com.bryan.log.items.ItemDrop;
import com.bryan.log.items.ItemPickup;
import com.bryan.log.players.Death;
import com.bryan.log.players.GamemodeChange;
import com.bryan.log.players.Respawn;
import com.bryan.log.players.Teleport;
import com.bryan.log.server_info.*;
import com.bryan.log.type.Chat;
import com.bryan.log.type.Command;
import com.bryan.log.utils.DirectoryEnums;
import com.bryan.log.utils.FileEnums;
import com.bryan.log.utils.Metrics;
import com.bryan.log.utils.UpdateChecker;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerLog extends JavaPlugin {

    private ChunksLoaded chunksLoaded;
    private EntityCount entityCount;
    private PlayerCount playerCount;
    private TPS tps;
    private RamUsage ramUsage;
    private UpdateChecker updater;

    @Override
    public void onEnable() {

        chunksLoaded = new ChunksLoaded(this);
        entityCount = new EntityCount(this);
        playerCount = new PlayerCount(this);
        tps = new TPS(this);
        ramUsage = new RamUsage(this);
        updater = new UpdateChecker(this);

        saveDefaultConfig();
        reloadConfig();

        Bukkit.getConsoleSender().sendMessage(color(""));
        Bukkit.getConsoleSender().sendMessage(color("&f[Server Log]: &aThank you for choosing Server Log to log your server's functions."));
        Bukkit.getConsoleSender().sendMessage(color("&f[Server Log]: &aThe current version you are using is &2" + getDescription().getFullName()));
        Bukkit.getConsoleSender().sendMessage(color("&f[Server Log]: &aThis is the full version of Server Log, so all features are enabled."));
        Bukkit.getConsoleSender().sendMessage(color("&f[Server Log]: &aThank you so much for purchasing the full version!"));
        updater.checkForUpdate();
        Bukkit.getConsoleSender().sendMessage(color(""));

        try {
            initiateFolders();
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        serverInfo();

        Bukkit.getPluginManager().registerEvents(new Join(this), this);
        Bukkit.getPluginManager().registerEvents(new Quit(this), this);
        Bukkit.getPluginManager().registerEvents(new Kick(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlace(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreak(this), this);
        Bukkit.getPluginManager().registerEvents(new EmptyBucket(this), this);
        Bukkit.getPluginManager().registerEvents(new Chat(this), this);
        Bukkit.getPluginManager().registerEvents(new Command(this), this);
        Bukkit.getPluginManager().registerEvents(new ItemDrop(this), this);
        Bukkit.getPluginManager().registerEvents(new ItemPickup(this), this);
        Bukkit.getPluginManager().registerEvents(new Death(this), this);
        Bukkit.getPluginManager().registerEvents(new Respawn(this), this);
        Bukkit.getPluginManager().registerEvents(new GamemodeChange(this), this);
        Bukkit.getPluginManager().registerEvents(new Teleport(this), this);

        if (Bukkit.getPluginManager().isPluginEnabled("Vouchers")) {
            if (getConfig().getBoolean("vouchers-api")) {
                Bukkit.getPluginManager().registerEvents(new Vouchers(this), this);
                Bukkit.getConsoleSender().sendMessage(color("&7&l[SERVERLOG INFO] &6The plugin 'Vouchers' API has been enabled and is logging voucher redemption."));
            }
        }

        Metrics metrics = new Metrics(this);
        metrics.addCustomChart(new Metrics.SimplePie("used_language", () -> getConfig().getString("lang", "en")));

    }

    private String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public void initiateFolders() throws IOException, InvalidConfigurationException {

        File fileDirectory = getDataFolder();
        if (!fileDirectory.exists()) {
            Bukkit.getConsoleSender().sendMessage(color("&7&l[SERVERLOG INFO] &aThe folder '" + fileDirectory.getPath().replace("plugins\\", "") + "' has been created..."));
            fileDirectory.mkdirs();
        }

        List<File> directories = new ArrayList<>();
        for (DirectoryEnums dir : DirectoryEnums.values()) {
            if (!directories.contains(new File(getDataFolder() + dir.directory))) {
                directories.add(new File(getDataFolder() + dir.directory));
            }
        }

        List<File> files = new ArrayList<>();
        for (FileEnums file : FileEnums.values()) {
            if (!files.contains(new File(getDataFolder() + file.fileName))) {
                files.add(new File(getDataFolder() + file.fileName));
            }
        }

        for (File dir : directories) {
            if (!dir.exists()) {
                dir.mkdirs();
                Bukkit.getConsoleSender().sendMessage(color("&7&l[SERVERLOG INFO] &aThe folder '" + dir.getPath().replace("plugins\\", "") + "' has been created..."));
            }
            if (dir.equals(new File(getDataFolder() + "/Lang/"))) {
                for (File langFile : files) {
                    if (!langFile.exists()) {
                        saveResource(langFile.getName(), false);
                        FileUtils.moveFile(new File(getDataFolder() + "/" + langFile.getName()), new File(getDataFolder() + "/Lang/" + langFile.getName()));
                        FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(langFile);
                        fileConfig.load(langFile);
                        Bukkit.getConsoleSender().sendMessage(color("&7&l[SERVERLOG INFO] &eThe lang file '" + langFile.getName() + "' has been created..."));
                    }
                }
            } else if (dir.equals(new File(getDataFolder() + "/Server Information/Entity Count/"))) {
                for (World world : Bukkit.getServer().getWorlds()) {
                    File worldDir = new File(dir + "/" + world.getName() + "/");
                    if (!worldDir.exists()) {
                        worldDir.mkdirs();
                        Bukkit.getConsoleSender().sendMessage(color("&7&l[SERVERLOG INFO] &aThe folder '" + worldDir.getPath().replace("plugins\\", "") + "' has been created..."));
                    }
                }
            } else if (dir.equals(new File(getDataFolder() + "/Server Information/Chunks Loaded/"))) {
                for (World world : Bukkit.getServer().getWorlds()) {
                    File worldDir = new File(dir + "/" + world.getName() + "/");
                    if (!worldDir.exists()) {
                        worldDir.mkdirs();
                        Bukkit.getConsoleSender().sendMessage(color("&7&l[SERVERLOG INFO] &aThe folder '" + worldDir.getPath().replace("plugins\\", "") + "' has been created..."));
                    }
                }
            }
        }

        Bukkit.getConsoleSender().sendMessage(color("&7&l[SERVERLOG INFO] &2All files have been initiated..."));

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
                Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "There was a fatal error saving server info...");
            }
        }, 0, (long) getConfig().getDouble("server-info-delay") * 60L * 20L);
    }

}
