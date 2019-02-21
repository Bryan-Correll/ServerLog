package com.bryan.log.utils;

public enum DirectoryEnums {

    HISTORY("/History/"),
    SERVERINFORMATION("/Server Information/"),
    TPSVALUE("/Server Information/TPS/"),
    RAMUSAGE("/Server Information/Ram Usage/"),
    PLAYERCOUNT("/Server Information/Player Count/"),
    ENTITYCOUNT("/Server Information/Entity Count/"),
    CUNKSLOADED("/Server Information/Chunks Loaded/"),
    LANG("/Lang/"),
    ACTIVITY("/Activity/"),
    JOIN("/Activity/Player Join/"),
    QUIT("/Activity/Player Quit/"),
    KICK("/Activity/Player Kick/"),
    BLOCKS("/Blocks/"),
    PLACE("/Blocks/Block Place/"),
    BREAK("/Blocks/Block Break/"),
    BUCKET("/Blocks/Empty Bucket/"),
    CHAT("/Chat/"),
    COMMANDS("/Commands/"),
    ITEMS("/Items/"),
    PICKUP("/Items/Item Pickup/"),
    DROP("/Items/Item Drop/"),
    PLAYERS("/Players/"),
    DEATH("/Players/Death/"),
    GAMEMODECHANGE("/Players/Gamemode Change/"),
    RESPAWN("/Players/Respawn/"),
    TELEPORT("/Players/Teleport/"),
    CUSTOMAPIS("/Custom APIs/"),
    VOUCHERS("/Custom APIs/Vouchers/");

    public String directory;

    DirectoryEnums(String directory) {
        this.directory = directory;
    }

}
