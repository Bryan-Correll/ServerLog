package com.bryan.log.utils;

import com.bryan.log.ServerLog;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Methods {

    private ServerLog serverLog;
    public Methods(ServerLog serverLog) {
        this.serverLog = serverLog;
    }

    public void appendString(String folder, String configString) throws IOException {
        Date dateNow = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(getConfigFile().getString("time"));
        SimpleDateFormat txtFormat = new SimpleDateFormat(getConfigFile().getString("log-time"));

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
        Date dateYesterday = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH) - 1).getTime();

        File yesterdayFile = new File(serverLog.getDataFolder() + directory + dateFormat.format(dateYesterday) + ".txt");

        if (yesterdayFile.exists()) {
            return true;
        } else {
            return false;
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
        Files.move(new File(serverLog.getDataFolder() + "/Custom APIs/").toPath(), new File(serverLog.getDataFolder() + "/History/" + dateFormat.format(date) + "/Custom APIs/").toPath());
        Files.move(new File(serverLog.getDataFolder() + "/Items/").toPath(), new File(serverLog.getDataFolder() + "/History/" + dateFormat.format(date) + "/Items/").toPath());
        Files.move(new File(serverLog.getDataFolder() + "/Players/").toPath(), new File(serverLog.getDataFolder() + "/History/" + dateFormat.format(date) + "/Players/").toPath());
        Files.move(new File(serverLog.getDataFolder() + "/Server Information/").toPath(), new File(serverLog.getDataFolder() + "/History/" + dateFormat.format(date) + "/Server Information/").toPath());

        serverLog.initiateFolders();
    }

}
