package me.DirkWind.EnderPlayers.listeners;

import me.DirkWind.EnderPlayers.Main;
import me.DirkWind.EnderPlayers.globals.Config;
import me.DirkWind.EnderPlayers.globals.EnderTeleport;
import me.DirkWind.EnderPlayers.items.TPStickItem;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.RayTraceResult;

import java.util.*;

public class EnderTeleportListener implements Listener {

    private Main plugin;
    private Map<UUID, Long> cooldowns = new HashMap<>();
    private Config config;

    public EnderTeleportListener(Main plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
        config = Config.getInstance();
    }

    private void updateCooldown(Player p) {
        cooldowns.put(p.getUniqueId(), System.currentTimeMillis() + (long) (config.getTPCooldown() * 1000));
    }

    private RayTraceResult getTargetBlock(Player p) {
        return p.getWorld().rayTraceBlocks(p.getLocation().add(0.5, 1.6875, 0.5), p.getLocation().getDirection(),
                config.getMaxTpDistance(), FluidCollisionMode.NEVER, true);
    }

    private void teleportParticleEffectAt(Player p) {
        if (config.getShowTPParticles()) p.getWorld().spawnParticle(Particle.PORTAL,
                p.getLocation().add(0, 1, 0), 150, 0.2, 0.5, 0.2, 0.25);
    }

    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (EnderTeleport.getPlayer(p)) {
            RayTraceResult targetedBlock = getTargetBlock(event.getPlayer());
            Block b = targetedBlock.getHitBlock();
            UUID playerID = p.getUniqueId();
            World w = p.getWorld();

            if (b == null) return;
            if (event.getClickedBlock() != null)
                if (event.getClickedBlock().getType().isInteractable()) {
                    return;
                }
            if (b.getType() != Material.AIR && TPStickItem.getInstance().equals(p.getInventory().getItemInMainHand()) &&
                    (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {

                if (!cooldowns.containsKey(playerID)) {
                    updateCooldown(p);
                } else {
                    if (cooldowns.get(p.getUniqueId()) - System.currentTimeMillis() >= 0) {
                        String message = config.getTPCooldownMessage();
                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                                new ComponentBuilder(String.format(message, (cooldowns.get(playerID) -
                                        System.currentTimeMillis()) / (double) 1000)).bold(true).create());
                        return;
                    } else {
                        updateCooldown(p);
                    }
                }


                teleportParticleEffectAt(p);

                Location loc;

                if (!w.getBlockAt(b.getLocation().add(0, 1, 0)).getType().isSolid()
                        && !w.getBlockAt(b.getLocation().add(0, 2, 0)).getType().isSolid()) {
                    loc = b.getLocation();
                    loc.add(0.5, 1, 0.5);
                } else {
                    loc = w.getBlockAt(b.getLocation().add(targetedBlock.getHitBlockFace().getDirection())).getLocation();
                    loc.add(0.5, 0, 0.5);
                }
                loc.setPitch(p.getLocation().getPitch());
                loc.setYaw(p.getLocation().getYaw());
                if (!config.getKeepFallDamageOnTP()) p.setFallDistance(0);
                p.teleport(loc);
                teleportParticleEffectAt(p);

                if (config.getPlayTPSounds()) w.playSound(p.getLocation().add(0.5, 1, 0.5),
                        Sound.ENTITY_ENDERMAN_TELEPORT, 0.7f, 0.5f);
            }
        }

    }



}
