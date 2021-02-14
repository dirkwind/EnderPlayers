package me.DirkWind.EnderPlayers.listeners;

import me.DirkWind.EnderPlayers.Main;
import me.DirkWind.EnderPlayers.globals.Config;
import me.DirkWind.EnderPlayers.globals.EnderWater;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.Arrays;

public class EnderWaterListener implements Runnable, Listener {

    private final Main plugin;
    private final Config config;

    public EnderWaterListener(Main plugin) {
        this.plugin = plugin;
        config = Config.getInstance();
        this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(this.plugin, this, 0L, 10L);
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    private boolean containsWater(Block b) {
        if (b.getType() == Material.WATER) {
            return true;
        } else if (b instanceof Waterlogged) {
            Waterlogged waterlogged = (Waterlogged) b.getBlockData();
            return waterlogged.isWaterlogged();
        }
        return false;
    }

    @Override
    public void run() {
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            if (EnderWater.getPlayer(p)) {
                World w = p.getWorld();
                if (w.getEnvironment() == World.Environment.NETHER) return;
                Location loc = p.getLocation();

                // check if player is being rained on
                if (w.hasStorm()) {
                    if (w.getHighestBlockYAt(loc.clone().add(0, 1, 0)) < loc.getY() && w.getBlockAt(loc.add(0, 1, 0)).isPassable()) {
                        double temp = w.getTemperature(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
                        if (temp >= 0.15 && temp <= 0.96)
                            if (!(config.getHelmetsBlockRain() || config.getFullArmorBlocksWater()) || p.getInventory().getHelmet() == null) {
                                p.damage(1);
                                return;
                            }
                    }
                }

                // check if player is touching water that isn't rain
                if (containsWater(w.getBlockAt(loc)) || containsWater(w.getBlockAt(loc.add(0, 1, 0)))) {
                    if (!config.getFullArmorBlocksWater() || Arrays.asList(p.getInventory().getArmorContents()).contains(null)) {
                        p.damage(1);
                    }
                }
            }
        }
    }

    @EventHandler
    public void playerHitByWater(final ProjectileHitEvent event) {
        if (event.getEntity() instanceof ThrownPotion) {
            if (((ThrownPotion) event.getEntity()).getEffects().isEmpty()) {
                for (Player p : event.getEntity().getWorld().getPlayers()) {
                    if (EnderWater.getPlayer(p))
                        if (p.getLocation().distance(event.getEntity().getLocation()) <= 8)
                            if (!config.getFullArmorBlocksWater() ||
                                    Arrays.asList(p.getInventory().getArmorContents()).contains(null)) {
                                p.damage(1);
                            }

                }
            }
        }
    }
}
