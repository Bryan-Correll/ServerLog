package com.bryan.log.server_log_api;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ServerLogEvent extends Event {

    private String logMessage;
    private String logTime;
    private String event;
    private String logDate;
    private String directory;

    public ServerLogEvent(String message, String time, String date, String dir, String eventLogged) {
        logMessage = message;
        logTime = time;
        logDate = date;
        event = eventLogged;
        directory = dir;
    }

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public String getLogTime() {
        return logTime;
    }

    public String getLogDate() {
        return logDate;
    }

    public String getEventLogged() {
        return event;
    }

    public String getDirectory() {
        return directory.replace("plugins/", "");
    }

}
