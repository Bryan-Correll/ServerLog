package com.bryan.log.utils;

public enum DirectoryEnums {

    COMPILED_LOG("/Compiled Log/"),
    HISTORY("/History/"),
    SERVER_INFORMATION("/Server Information/"),
    TPS("/Server Information/TPS/"),
    RAM("/Server Information/Ram Usage/"),
    PLAYER_COUNT("/Server Information/Player Count/"),
    ENTITY_COUNT("/Server Information/Entity Count/"),
    CHUNKS_LOADED("/Server Information/Chunks Loaded/"),
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
    SPAWN_EGG("/Players/Spawn Mob Egg/"),
    GAMEMODE_CHANGE("/Players/Gamemode Change/"),
    RESPAWN("/Players/Respawn/"),
    TELEPORT("/Players/Teleport/"),
    CUSTOM_APIS("/Custom APIs/"),
    VOUCHERS("/Custom APIs/Vouchers/");

    public String directory;

    DirectoryEnums(String directory) {
        this.directory = directory;
    }

}
