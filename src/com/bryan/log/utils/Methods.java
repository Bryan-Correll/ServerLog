package com.bryan.log.utils;

import com.bryan.log.ServerLog;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

public class Methods {

    private ServerLog serverLog;
    public Methods(ServerLog serverLog) {
        this.serverLog = serverLog;
    }

    public String color(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public String getTime() {
        Date dateNow = new Date();
        SimpleDateFormat txtFormat = new SimpleDateFormat("HH:mm:ss");
        return txtFormat.format(dateNow);
    }

    public String getDate() {
        Date dateNow = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        return dateFormat.format(dateNow);
    }

    public void appendString(String folder, String configString) throws IOException {

        Date dateNow = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(getConfigFile().getString("time"));
        SimpleDateFormat txtFormat = new SimpleDateFormat(getConfigFile().getString("log-time"));

        File folderDir = new File(serverLog.getDataFolder() + folder);

        if (!folderDir.exists()) {
            folderDir.mkdirs();
        }

        File dateFile = new File(serverLog.getDataFolder() + folder + dateFormat.format(dateNow) + ".txt");

        FileWriter fileWriter = new FileWriter(dateFile, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        if (dateFile.exists()) {
            bufferedWriter.append(configString.replace("[time]", "[" + txtFormat.format(dateNow) + "]"));
            bufferedWriter.newLine();
            bufferedWriter.close();
        } else {
            dateFile.createNewFile();
            bufferedWriter.append(configString.replace("[time]", "[" + txtFormat.format(dateNow) + "]"));
            bufferedWriter.newLine();
            bufferedWriter.close();
        }
    }

    public FileConfiguration getConfigFile() {
        String langSelection = serverLog.getConfig().getString("lang");
        File langFile = new File(serverLog.getDataFolder() + "//Lang//" + langSelection + ".yml");
        FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(langFile);

        return  fileConfig;
    }

    public boolean dateChanged(String directory) {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(getConfigFile().getString("time"));
        Date dateToday = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).getTime();

        List<File> files = Arrays.asList(new File(serverLog.getDataFolder() + directory).listFiles());

        if (new File(serverLog.getDataFolder() + directory).length() == -1) {
            return false;
        } else {
            if (files.contains(new File(serverLog.getDataFolder() + directory + dateFormat.format(dateToday) + ".txt"))) {
                return false;
            } else {
                return true;
            }
        }
    }

    public void moveToHistory() throws IOException, InvalidConfigurationException {

        Calendar cal = Calendar.getInstance();
        Date date = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH) - 1).getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(getConfigFile().getString("time"));

        File dateHistoryDir = new File(serverLog.getDataFolder() + "/History/" + dateFormat.format(date) + "/");
        if (!dateHistoryDir.exists()) {
            dateHistoryDir.mkdirs();
        }

        Files.move(new File(serverLog.getDataFolder() + "/Activity/").toPath(), new File(serverLog.getDataFolder() + "/History/" + dateFormat.format(date) + "/Activity/").toPath());
        Files.move(new File(serverLog.getDataFolder() + "/Blocks/").toPath(), new File(serverLog.getDataFolder() + "/History/" + dateFormat.format(date) + "/Blocks/").toPath());
        Files.move(new File(serverLog.getDataFolder() + "/Chat/").toPath(), new File(serverLog.getDataFolder() + "/History/" + dateFormat.format(date) + "/Chat/").toPath());
        Files.move(new File(serverLog.getDataFolder() + "/Commands/").toPath(), new File(serverLog.getDataFolder() + "/History/" + dateFormat.format(date) + "/Commands/").toPath());
        Files.move(new File(serverLog.getDataFolder() + "/Compiled Log/").toPath(), new File(serverLog.getDataFolder() + "/History/" + dateFormat.format(date) + "/Compiled Log/").toPath());
        Files.move(new File(serverLog.getDataFolder() + "/Custom APIs/").toPath(), new File(serverLog.getDataFolder() + "/History/" + dateFormat.format(date) + "/Custom APIs/").toPath());
        Files.move(new File(serverLog.getDataFolder() + "/Items/").toPath(), new File(serverLog.getDataFolder() + "/History/" + dateFormat.format(date) + "/Items/").toPath());
        Files.move(new File(serverLog.getDataFolder() + "/Players/").toPath(), new File(serverLog.getDataFolder() + "/History/" + dateFormat.format(date) + "/Players/").toPath());
        Files.move(new File(serverLog.getDataFolder() + "/Server Information/").toPath(), new File(serverLog.getDataFolder() + "/History/" + dateFormat.format(date) + "/Server Information/").toPath());

        initiateFolders();
    }

    public boolean bStatsEnabled() {
        File bStatsFolder = new File(serverLog.getDataFolder().getParentFile(), "bStats");
        File configFile = new File(bStatsFolder, "config.yml");
        YamlConfiguration bStatsConfig = YamlConfiguration.loadConfiguration(configFile);
        return bStatsConfig.getBoolean("enabled");
    }

    public void initiateFolders() throws IOException, InvalidConfigurationException {

        File fileDirectory = serverLog.getDataFolder();
        if (!fileDirectory.exists()) {
            Bukkit.getConsoleSender().sendMessage(color("&f[Server Log]: &aThe folder '" + fileDirectory.getPath().replace("plugins\\", "") + "' has been created..."));
            fileDirectory.mkdirs();
        }

        List<File> directories = new ArrayList<>();
        for (DirectoryEnums dir : DirectoryEnums.values()) {
            if (!directories.contains(new File(serverLog.getDataFolder() + dir.directory))) {
                directories.add(new File(serverLog.getDataFolder() + dir.directory));
            }
        }

        List<File> files = new ArrayList<>();
        for (FileEnums file : FileEnums.values()) {
            if (!files.contains(new File(serverLog.getDataFolder() + file.fileName))) {
                files.add(new File(serverLog.getDataFolder() + file.fileName));
            }
        }

        for (File dir : directories) {
            if (!dir.exists()) {
                dir.mkdirs();
                Bukkit.getConsoleSender().sendMessage(color("&f[Server Log]: &aThe folder '" + dir.getPath().replace("plugins\\", "") + "' has been created..."));
            }
            if (dir.equals(new File(serverLog.getDataFolder() + "/Lang/"))) {
                for (File langFile : files) {
                    if (!langFile.exists()) {
                        serverLog.saveResource(langFile.getName(), false);
                        FileUtils.moveFile(new File(serverLog.getDataFolder() + "/" + langFile.getName()), new File(serverLog.getDataFolder() + "/Lang/" + langFile.getName()));
                        FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(langFile);
                        fileConfig.load(langFile);
                        Bukkit.getConsoleSender().sendMessage(color("&f[Server Log]: &eThe lang file '" + langFile.getName() + "' has been created..."));
                    }
                }
            } else if (dir.equals(new File(serverLog.getDataFolder() + "/Server Information/Entity Count/"))) {
                for (World world : Bukkit.getServer().getWorlds()) {
                    File worldDir = new File(dir + "/" + world.getName() + "/");
                    if (!worldDir.exists()) {
                        worldDir.mkdirs();
                        Bukkit.getConsoleSender().sendMessage(color("&f[Server Log]: &aThe folder '" + worldDir.getPath().replace("plugins\\", "") + "' has been created..."));
                    }
                }
            } else if (dir.equals(new File(serverLog.getDataFolder() + "/Server Information/Chunks Loaded/"))) {
                for (World world : Bukkit.getServer().getWorlds()) {
                    File worldDir = new File(dir + "/" + world.getName() + "/");
                    if (!worldDir.exists()) {
                        worldDir.mkdirs();
                        Bukkit.getConsoleSender().sendMessage(color("&f[Server Log]: &aThe folder '" + worldDir.getPath().replace("plugins\\", "") + "' has been created..."));
                    }
                }
            }
        }

        Bukkit.getConsoleSender().sendMessage(color("&f[Server Log]: &2All files have been initiated..."));

    }

}
