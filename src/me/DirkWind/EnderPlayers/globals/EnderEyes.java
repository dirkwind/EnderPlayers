package me.DirkWind.EnderPlayers.globals;

import com.sun.istack.internal.NotNull;
import me.DirkWind.EnderPlayers.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.UUID;

public class EnderEyes {

    private static String fileName = "endereyes.yml";
    private static File enderTeleportFile = new File(Main.getInstance().getDataFolder(), fileName);
    private static FileConfiguration enderTeleport = YamlConfiguration.loadConfiguration(enderTeleportFile);

    public static boolean toggle(@NotNull UUID id) throws IOException {
        String playerID = id.toString();
        boolean returnValue;
        if (!enderTeleport.contains(playerID)) {
            enderTeleport.set(playerID, true);
            returnValue = false;
        }
        if (enderTeleport.getBoolean(playerID)) {
            enderTeleport.set(playerID, false);
            returnValue = false;
        } else {
            enderTeleport.set(playerID, true);
            returnValue = true;
        }
        save();
        return returnValue;

    }

    public static void setFalse(@NotNull UUID id) throws IOException {
        enderTeleport.set(id.toString(), false);
        save();
    }

    public static void setTrue(@NotNull UUID id) throws IOException {
        enderTeleport.set(id.toString(), true);
        save();
    }

    public static boolean getPlayer(@NotNull Player player) {
        String playerID = player.getUniqueId().toString();
        if (!enderTeleport.contains(playerID)) {
            enderTeleport.set(playerID, false);
            return false;
        } else {
            return enderTeleport.getBoolean(playerID);
        }
    }

    public static void save() throws IOException {
        enderTeleport.save(enderTeleportFile);
    }

    public static void load() throws IOException {
        if (enderTeleportFile == null) {
            enderTeleportFile = new File(Main.getInstance().getDataFolder(), fileName);
        }
        enderTeleport = YamlConfiguration.loadConfiguration(enderTeleportFile);
        save();
    }

}
