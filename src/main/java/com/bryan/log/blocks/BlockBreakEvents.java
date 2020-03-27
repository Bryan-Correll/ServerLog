package com.bryan.log.blocks;

import com.bryan.log.ServerLog;
import com.bryan.log.server_log_api.ServerLogEvent;
import com.bryan.log.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.io.IOException;

public class BlockBreakEvents implements Listener {

    private Methods methods;

    public BlockBreakEvents(ServerLog serverLog) {
        this.methods = new Methods(serverLog);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) throws IOException {
        Integer x = e.getBlock().getX();
        Integer z = e.getBlock().getZ();
        Integer y = e.getBlock().getY();
        String blockName = e.getBlock().getType().name();

        ServerLogEvent logEvent = new ServerLogEvent(methods.getConfigFile().getString("block-break-event").replace("[time]: ", "").replace("[player]", e.getPlayer().getName()).replace("[block]", blockName).replace("[x]", x.toString()).replace("[y]", y.toString()).replace("[z]", z.toString()), methods.getTime(), methods.getDate(), "plugins/ServerLog/Blocks/Block Break/", "BlockBreakEvent");
        Bukkit.getPluginManager().callEvent(logEvent);

        methods.appendString("/Blocks/Block Break/", methods.getConfigFile().getString("block-break-event").replace("[player]", e.getPlayer().getName()).replace("[block]", blockName).replace("[x]", x.toString()).replace("[y]", y.toString()).replace("[z]", z.toString()));
        methods.appendString("/Compiled Log/", methods.getConfigFile().getString("block-break-event").replace("[player]", e.getPlayer().getName()).replace("[block]", blockName).replace("[x]", x.toString()).replace("[y]", y.toString()).replace("[z]", z.toString()));
    }

}
