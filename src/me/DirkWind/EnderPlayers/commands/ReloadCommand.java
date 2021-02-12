package me.DirkWind.EnderPlayers.commands;

import me.DirkWind.EnderPlayers.Main;
import me.DirkWind.EnderPlayers.globals.Config;
import me.DirkWind.EnderPlayers.globals.EnderEyes;
import me.DirkWind.EnderPlayers.globals.EnderHands;
import me.DirkWind.EnderPlayers.globals.EnderTeleport;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.IOException;

public class ReloadCommand implements CommandExecutor {

    private Main plugin;

    public ReloadCommand(Main plugin) {
        this.plugin = plugin;
        this.plugin.getCommand("epreload").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender.hasPermission("epreload.use")) {
            try {
                Config.getInstance().reload();
                EnderTeleport.load();
                EnderHands.load();
                EnderEyes.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sender.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "EnderPlayers" + ChatColor.RESET + "" +
                    ChatColor.GOLD + " has been reloaded!");
            return true;
        }
        return false;
    }
}
