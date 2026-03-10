package com.ferrinmotd;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class MotdListener implements Listener {

    private final FerrinMotd plugin;
    private final MotdManager motdManager;

    public MotdListener(FerrinMotd plugin, MotdManager motdManager) {
        this.plugin = plugin;
        this.motdManager = motdManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPing(ServerListPingEvent event) {
        event.setMotd(motdManager.getCurrentMotd());
        
        if (plugin.getConfig().getBoolean("just-one-more-slot", true)) {
            int onlinePlayers = event.getNumPlayers();
            
            if (onlinePlayers == 0) {
                event.setMaxPlayers(0);
            } else {
                event.setMaxPlayers(onlinePlayers + 1);
            }
        }
    }
}