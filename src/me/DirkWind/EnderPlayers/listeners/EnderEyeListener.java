package me.DirkWind.EnderPlayers.listeners;

import me.DirkWind.EnderPlayers.Main;
import me.DirkWind.EnderPlayers.globals.EnderEyes;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public class EnderEyeListener implements Listener {

    private Main plugin;

    public EnderEyeListener(Main plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onEndermanAgro(final EntityTargetEvent event) {
        if (event.getTarget() instanceof Player && event.getEntityType() == EntityType.ENDERMAN
                && event.getReason() == EntityTargetEvent.TargetReason.CLOSEST_PLAYER) {
            if (EnderEyes.getPlayer((Player) event.getTarget())) {
                event.setCancelled(true);
            }
        }
    }
}
