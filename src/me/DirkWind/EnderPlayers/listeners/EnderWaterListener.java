package me.DirkWind.EnderPlayers.listeners;

import me.DirkWind.EnderPlayers.Main;
import me.DirkWind.EnderPlayers.globals.Config;
import me.DirkWind.EnderPlayers.globals.EnderWater;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class EnderWaterListener implements Runnable {

    private final Main plugin;
    private final Config config;

    public EnderWaterListener(Main plugin) {
        this.plugin = plugin;
        config = Config.getInstance();
        this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(this.plugin, this, 0L, 10L);
    }

    @Override
    public void run() {
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            if (EnderWater.getPlayer(p)) {
                World w = p.getWorld();
                if (w.hasStorm()) {
                    if (w.getHighestBlockYAt(p.getLocation()) < p.getLocation().getY())
                        if (!(config.getHelmetsBlockRain() || config.getFullArmorBlocksWater()) || p.getInventory().getHelmet() == null) {
                            p.damage(1);
                            return;
                        }
                }
                if (w.getBlockAt(p.getLocation()).getType() == Material.WATER ||
                        w.getBlockAt(p.getLocation().add(0, 1, 0)).getType() == Material.WATER) {
                    if (!config.getFullArmorBlocksWater() || Arrays.asList(p.getInventory().getArmorContents()).contains(null)) {
                        p.damage(1);
                    }
                }
            }
        }
    }
}
