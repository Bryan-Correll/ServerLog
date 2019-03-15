package com.bryan.log.players;

import com.bryan.log.ServerLog;
import com.bryan.log.server_log_api.ServerLogEvent;
import com.bryan.log.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.IOException;

public class PlayerEggSpawnEvents implements Listener {

    private Methods methods;

    public PlayerEggSpawnEvents(ServerLog serverLog) {
        this.methods = new Methods(serverLog);
    }

    @EventHandler
    public void onEggSpawn(PlayerInteractEvent e) throws IOException {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.hasItem()) {
                if (e.getItem().getType().equals(Material.MONSTER_EGG)) {
                    EntityType entityType = EntityType.fromId(e.getItem().getDurability());

                    ServerLogEvent logEvent = new ServerLogEvent(methods.getConfigFile().getString("spawn-egg").replace("[time]: ", "").replace("[player]", e.getPlayer().getName()).replace("[entity]", entityType.name()).replace("[x]", "" + e.getClickedBlock().getX()).replace("[y]", "" + e.getClickedBlock().getY()).replace("[z]", "" + e.getClickedBlock().getZ()), methods.getTime(), methods.getDate(), "plugins/ServerLog/Players/Spawn Egg/", "PlayerInteractEvent");
                    Bukkit.getPluginManager().callEvent(logEvent);

                    methods.appendString("/Players/Spawn Mob Egg/", methods.getConfigFile().getString("spawn-egg").replace("[player]", e.getPlayer().getName()).replace("[entity]", entityType.name()).replace("[x]", "" + e.getClickedBlock().getX()).replace("[y]", "" + e.getClickedBlock().getY()).replace("[z]", "" + e.getClickedBlock().getZ()));
                    methods.appendString("/Compiled Log/", methods.getConfigFile().getString("spawn-egg").replace("[player]", e.getPlayer().getName()).replace("[entity]", entityType.name()).replace("[x]", "" + e.getClickedBlock().getX()).replace("[y]", "" + e.getClickedBlock().getY()).replace("[z]", "" + e.getClickedBlock().getZ()));
                }
            }
        }
    }

}
