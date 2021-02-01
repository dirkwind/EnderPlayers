package me.DirkWind.EnderPlayers;

import me.DirkWind.EnderPlayers.commands.EnderHandCommand;
import me.DirkWind.EnderPlayers.globals.EnderHands;
import me.DirkWind.EnderPlayers.listeners.EnderHandListener;
import me.DirkWind.EnderPlayers.tab_completers.EnderHandTabCompleter;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {

    public static Main instance;

    public static Main getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        try {
            EnderHands.load();
            this.getServer().broadcastMessage(String.format("%s %s", ChatColor.GREEN + "Enderhands",
                    ChatColor.GOLD + "data loaded successfully."));
        } catch (IOException e) {
            this.getServer().broadcastMessage(ChatColor.RED + "Error while loading enderHands data.");
        }
        new EnderHandCommand(this);
        new EnderHandListener(this);
        new EnderHandTabCompleter(this);
    }

    public void onDisable() {

    }

}
