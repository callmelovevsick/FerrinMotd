package com.ferrinmotd;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class MotdListener implements Listener {

    private final MotdManager motdManager;

    public MotdListener(MotdManager motdManager) {
        this.motdManager = motdManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPing(ServerListPingEvent event) {
        event.setMotd(motdManager.getCurrentMotd());
    }
}