package me.DirkWind.EnderPlayers.globals;

import com.sun.istack.internal.NotNull;
import me.DirkWind.EnderPlayers.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.UUID;

public class EnderHands {

    private static File enderHandsFile = new File(Main.getInstance().getDataFolder(), "enderhands.yml");
    private static FileConfiguration enderHands = YamlConfiguration.loadConfiguration(enderHandsFile);

    public static boolean toggle(@NotNull UUID id) throws IOException {
        String playerID = id.toString();
        boolean returnValue;
        if (!enderHands.contains(playerID)) {
            enderHands.set(playerID, true);
            returnValue = false;
        }
        if (enderHands.getBoolean(playerID)) {
            enderHands.set(playerID, false);
            returnValue = false;
        } else {
            enderHands.set(playerID, true);
            returnValue = true;
        }
        save();
        return returnValue;

    }

    public static void setFalse(@NotNull UUID id) throws IOException {
        enderHands.set(id.toString(), false);
        save();
    }

    public static void setTrue(@NotNull UUID id) throws IOException {
        enderHands.set(id.toString(), true);
        save();
    }

    public static boolean getPlayer(@NotNull Player player) {
        String playerID = player.getUniqueId().toString();
        if (!enderHands.contains(playerID)) {
            enderHands.set(playerID, false);
            return false;
        } else {
            return enderHands.getBoolean(playerID);
        }
    }

    public static void save() throws IOException {
        enderHands.save(enderHandsFile);
    }

    public static void load() throws IOException {
        if (enderHandsFile == null) {
            enderHandsFile = new File(Main.getInstance().getDataFolder(), "enderhands.yml");
        }
        enderHands = YamlConfiguration.loadConfiguration(enderHandsFile);
        save();
    }

}
