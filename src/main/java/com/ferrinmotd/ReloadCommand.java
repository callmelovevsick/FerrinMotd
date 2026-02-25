package com.ferrinmotd;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    private final FerrinMotd plugin;

    public ReloadCommand(FerrinMotd plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("ferrinmotd.reload")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to execute this command.");
            return true;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            plugin.reloadPlugin();
            String prefix = plugin.getConfig().getString("prefix", "&8[&bFerrinMotd&8] &f");
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&aConfiguration and MOTDs reloaded!"));
            return true;
        }

        sender.sendMessage(ChatColor.RED + "Usage: /ferrinmotd reload");
        return true;
    }
}