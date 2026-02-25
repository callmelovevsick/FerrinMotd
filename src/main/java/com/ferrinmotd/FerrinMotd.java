package com.ferrinmotd;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class FerrinMotd extends JavaPlugin {

    private MotdManager motdManager;
    private BukkitTask rotationTask;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        
        this.motdManager = new MotdManager(this);
        this.motdManager.loadMotds();

        getServer().getPluginManager().registerEvents(new MotdListener(motdManager), this);
        getCommand("ferrinmotd").setExecutor(new ReloadCommand(this));

        startRotationTask();

        getLogger().info("FerrinMotd enabled successfully");
    }

    @Override
    public void onDisable() {
        stopRotationTask();
        getLogger().info("FerrinMotd disabled.");
    }

    public void reloadPlugin() {
        reloadConfig();
        stopRotationTask();
        motdManager.loadMotds();
        startRotationTask();
    }

    private void startRotationTask() {
        int intervalSeconds = getConfig().getInt("interval", 60);
        if (intervalSeconds <= 0) return;

        long ticks = intervalSeconds * 20L;
        rotationTask = getServer().getScheduler().runTaskTimerAsynchronously(this, () -> {
            motdManager.rotate();
        }, ticks, ticks);
    }

    private void stopRotationTask() {
        if (rotationTask != null) {
            rotationTask.cancel();
            rotationTask = null;
        }
    }

    public MotdManager getMotdManager() {
        return motdManager;
    }
}