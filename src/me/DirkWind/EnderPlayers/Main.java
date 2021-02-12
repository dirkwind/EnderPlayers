package me.DirkWind.EnderPlayers;

import me.DirkWind.EnderPlayers.commands.*;
import me.DirkWind.EnderPlayers.globals.Config;
import me.DirkWind.EnderPlayers.globals.EnderHands;
import me.DirkWind.EnderPlayers.globals.EnderTeleport;
import me.DirkWind.EnderPlayers.listeners.EnderEyeListener;
import me.DirkWind.EnderPlayers.listeners.EnderHandListener;
import me.DirkWind.EnderPlayers.listeners.EnderTeleportListener;
import me.DirkWind.EnderPlayers.recipes.TPStickRecipe;
import me.DirkWind.EnderPlayers.tab_completers.EnderCommandsTabCompleter;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class Main extends JavaPlugin {

    public static Main instance;

    public static Main getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        Config config = new Config();
        try {
            EnderHands.save();
            EnderTeleport.save();
            config.loadDefaults();
            this.getServer().broadcastMessage(String.format("%s %s", ChatColor.GREEN + "Enderhands",
                    ChatColor.GOLD + "data loaded successfully."));
        } catch (IOException e) {
            e.printStackTrace();
        }

        new EnderPlayerCommand(this);

        // endereyes stuff
        new EnderEyeCommand(this);
        new EnderEyeListener(this);

        // enderhands stuff
        new EnderHandCommand(this);
        new EnderHandListener(this);

        // enderteleport stuff
        new EnderTeleportListener(this);
        new EnderTeleportCommand(this);
        new TPStickRecipe(this);
        new TPStickCommand(this);

        // reload command
        new ReloadCommand(this);

        // the tab completer for most commands
        new EnderCommandsTabCompleter(this);
    }

    public void onDisable() {

    }

}
