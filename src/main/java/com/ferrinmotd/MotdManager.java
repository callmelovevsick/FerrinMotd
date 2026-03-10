package com.ferrinmotd;

import org.bukkit.ChatColor;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

public class MotdManager {

    private final FerrinMotd plugin;
    private final File motdFile;
    private final List<String> motdList = new ArrayList<>();
    private final Random random = new Random();
    
    private volatile String currentMotd = "A Minecraft Server";
    private int currentIndex = 0;

    public MotdManager(FerrinMotd plugin) {
        this.plugin = plugin;
        this.motdFile = new File(plugin.getDataFolder(), "motd.txt");
    }

    public void loadMotds() {
        if (!motdFile.exists()) {
            plugin.saveResource("motd.txt", false);
        }

        motdList.clear();
        try {
            List<String> lines = Files.readAllLines(motdFile.toPath());
            for (String line : lines) {
                if (line.trim().isEmpty()) continue;
                motdList.add(line);
            }
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not read motd.txt", e);
        }

        if (motdList.isEmpty()) {
            motdList.add("&cSet your MOTD in motd.txt!");
        }
        
        rotate();
    }

    public void rotate() {
        if (motdList.isEmpty()) return;

        boolean isRandom = plugin.getConfig().getBoolean("random", true);
        String rawMotd;

        if (isRandom) {
            rawMotd = motdList.get(random.nextInt(motdList.size()));
        } else {
            if (currentIndex >= motdList.size()) currentIndex = 0;
            rawMotd = motdList.get(currentIndex);
            currentIndex++;
        }

        this.currentMotd = format(rawMotd);
    }

    private String format(String input) {
        String processed = input.replace("\\n", "\n");
        return ChatColor.translateAlternateColorCodes('&', processed);
    }

    public String getCurrentMotd() {
        return currentMotd;
    }
}