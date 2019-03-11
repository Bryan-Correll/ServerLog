package com.bryan.log.server_info;

import com.bryan.log.ServerLog;
import com.bryan.log.server_log_api.ServerLogEvent;
import com.bryan.log.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;

public class TPS {

    private ServerLog serverLog;
    private Methods methods;
    public TPS(ServerLog serverLog) {
        this.serverLog = serverLog;
        this.methods = new Methods(serverLog);
    }

    private final String name = Bukkit.getServer().getClass().getPackage().getName();
    private final String version = name.substring(name.lastIndexOf('.') + 1);
    private final DecimalFormat format = new DecimalFormat("##.##");
    private Object serverInstance;
    private Field tpsField;

    private Class<?> getNMSClass(String className) {
        try {
            return Class.forName("net.minecraft.server." + version + "." + className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String getTPS(int time) {
        try {
            double[] tps = ((double[]) tpsField.get(serverInstance));
            return format.format(tps[time]);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void appendTPS() throws IOException {

        try {
            serverInstance = getNMSClass("MinecraftServer").getMethod("getServer").invoke(null);
            tpsField = serverInstance.getClass().getField("recentTps");
        } catch (NoSuchFieldException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        ServerLogEvent logEvent = new ServerLogEvent(methods.getConfigFile().getString("tps").replace("[tps]", getTPS(0)), methods.getTime(), methods.getDate(), "plugins/ServerLog/Server Information/TPS/", "");
        Bukkit.getPluginManager().callEvent(logEvent);

        if (methods.dateChanged("/Server Information/TPS/")) {
            try {
                methods.moveToHistory();
            } catch (InvalidConfigurationException ex) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "(TPS) There was a fatal error moving the files to the History... ERROR:");
                ex.printStackTrace();
            }
        }
        methods.appendString("/Server Information/TPS/", methods.getConfigFile().getString("tps").replace("[tps]", getTPS(0)));
        methods.appendString("/Compiled Log/", methods.getConfigFile().getString("tps").replace("[tps]", getTPS(0)));
    }

}
