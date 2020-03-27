package com.bryan.log.players;

import com.bryan.log.ServerLog;
import com.bryan.log.server_log_api.ServerLogEvent;
import com.bryan.log.utils.Methods;
import com.bryan.log.utils.UMaterial;
import org.bukkit.Bukkit;
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
                if (Bukkit.getServer().getBukkitVersion().contains("1.8")) {
                    if (e.getItem().getType().name().contains("MONSTER_EGG")) {
                        EntityType entityType = EntityType.fromId(e.getItem().getDurability());

                        ServerLogEvent logEvent = new ServerLogEvent(
                                methods.getConfigFile().getString("spawn-egg")
                                        .replace("[time]: ", "")
                                        .replace("[player]", e.getPlayer().getName())
                                        .replace("[entity]", entityType.name())
                                        .replace("[x]", "" + e.getClickedBlock().getLocation().getBlockX())
                                        .replace("[y]", "" + e.getClickedBlock().getLocation().getBlockY())
                                        .replace("[z]", "" + e.getClickedBlock().getLocation().getBlockZ()),
                                methods.getTime(),
                                methods.getDate(),
                                "plugins/ServerLog/Players/Spawn Mob Egg/",
                                "PlayerInteractEvent");
                        Bukkit.getPluginManager().callEvent(logEvent);

                        methods.appendString("/Players/Spawn Mob Egg/", methods.getConfigFile().getString("spawn-egg").replace("[player]", e.getPlayer().getName()).replace("[entity]", entityType.name()).replace("[x]", "" + e.getClickedBlock().getX()).replace("[y]", "" + e.getClickedBlock().getY()).replace("[z]", "" + e.getClickedBlock().getZ()));
                        methods.appendString("/Compiled Log/", methods.getConfigFile().getString("spawn-egg").replace("[player]", e.getPlayer().getName()).replace("[entity]", entityType.name()).replace("[x]", "" + e.getClickedBlock().getX()).replace("[y]", "" + e.getClickedBlock().getY()).replace("[z]", "" + e.getClickedBlock().getZ()));
                    }
                } else if (Bukkit.getServer().getBukkitVersion().contains("1.13")) {
                    if (e.getItem().getType().name().contains("SPAWN_EGG")) {
                        final UMaterial u = UMaterial.matchSpawnEgg(e.getItem());

                        String entityType = u.name().replace("_SPAWN_EGG", "");

                        ServerLogEvent logEvent = new ServerLogEvent(
                                methods.getConfigFile().getString("spawn-egg")
                                        .replace("[time]: ", "")
                                        .replace("[player]", e.getPlayer().getName())
                                        .replace("[entity]", entityType)
                                        .replace("[x]", "" + e.getClickedBlock().getLocation().getBlockX())
                                        .replace("[y]", "" + e.getClickedBlock().getLocation().getBlockY())
                                        .replace("[z]", "" + e.getClickedBlock().getLocation().getBlockZ()),
                                methods.getTime(),
                                methods.getDate(),
                                "plugins/ServerLog/Players/Spawn Mob Egg/",
                                "PlayerInteractEvent");
                        Bukkit.getPluginManager().callEvent(logEvent);

                        methods.appendString("/Players/Spawn Mob Egg/", methods.getConfigFile().getString("spawn-egg").replace("[player]", e.getPlayer().getName()).replace("[entity]", entityType).replace("[x]", "" + e.getClickedBlock().getX()).replace("[y]", "" + e.getClickedBlock().getY()).replace("[z]", "" + e.getClickedBlock().getZ()));
                        methods.appendString("/Compiled Log/", methods.getConfigFile().getString("spawn-egg").replace("[player]", e.getPlayer().getName()).replace("[entity]", entityType).replace("[x]", "" + e.getClickedBlock().getX()).replace("[y]", "" + e.getClickedBlock().getY()).replace("[z]", "" + e.getClickedBlock().getZ()));
                    }
                } else {
                    if (e.getItem().getType().name().contains("MONSTER_EGG")) {
                        final UMaterial u = UMaterial.matchSpawnEgg(e.getItem());
                        if (u.name().contains("SPAWN_EGG")) {
                            String entityType = u.name().replace("_SPAWN_EGG", "");
                            ServerLogEvent logEvent = new ServerLogEvent(
                                    methods.getConfigFile().getString("spawn-egg")
                                            .replace("[time]: ", "")
                                            .replace("[player]", e.getPlayer().getName())
                                            .replace("[entity]", entityType)
                                            .replace("[x]", "" + e.getClickedBlock().getLocation().getBlockX())
                                            .replace("[y]", "" + e.getClickedBlock().getLocation().getBlockY())
                                            .replace("[z]", "" + e.getClickedBlock().getLocation().getBlockZ()),
                                    methods.getTime(),
                                    methods.getDate(),
                                    "plugins/ServerLog/Players/Spawn Mob Egg/",
                                    "PlayerInteractEvent");
                            Bukkit.getPluginManager().callEvent(logEvent);

                            methods.appendString("/Players/Spawn Mob Egg/", methods.getConfigFile().getString("spawn-egg").replace("[player]", e.getPlayer().getName()).replace("[entity]", entityType).replace("[x]", "" + e.getClickedBlock().getX()).replace("[y]", "" + e.getClickedBlock().getY()).replace("[z]", "" + e.getClickedBlock().getZ()));
                            methods.appendString("/Compiled Log/", methods.getConfigFile().getString("spawn-egg").replace("[player]", e.getPlayer().getName()).replace("[entity]", entityType).replace("[x]", "" + e.getClickedBlock().getX()).replace("[y]", "" + e.getClickedBlock().getY()).replace("[z]", "" + e.getClickedBlock().getZ()));
                        }
                    }
                }
            }
        }
    }

}
