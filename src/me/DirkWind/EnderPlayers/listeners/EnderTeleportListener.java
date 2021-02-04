package me.DirkWind.EnderPlayers.listeners;

import me.DirkWind.EnderPlayers.Main;
import me.DirkWind.EnderPlayers.globals.EnderTeleport;
import me.DirkWind.EnderPlayers.items.TPStickItem;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.RayTraceResult;

import java.util.*;

public class EnderTeleportListener implements Listener {

    private final Main plugin;
    private final Map<UUID, Long> cooldowns = new HashMap<>();
    private final double cooldownTime = 5;

    public EnderTeleportListener(Main plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    private void updateCooldown(Player p) {
        cooldowns.put(p.getUniqueId(), System.currentTimeMillis() + (long) (cooldownTime * 1000));
    }

    // This code was yoinked from this guy: https://www.spigotmc.org/members/clip.1001/
    private Block getTargetBlock(Player player, int range) {
        BlockIterator iter = new BlockIterator(player, range);
        Block lastBlock = iter.next();
        while (iter.hasNext()) {
            lastBlock = iter.next();
            if (!lastBlock.getType().isSolid()) {
                continue;
            }
            break;
        }
        return lastBlock;
    }

    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (EnderTeleport.getPlayer(p)) {
            Block b = getTargetBlock(event.getPlayer(), 30);
            UUID playerID = p.getUniqueId();
            World w = p.getWorld();


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
                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                                new ComponentBuilder(String.format("Cooldown: %.2fs", (cooldowns.get(playerID) -
                                        System.currentTimeMillis()) / (double) 1000)).bold(true).create());
                        return;
                    } else {
                        updateCooldown(p);
                    }
                }


                w.spawnParticle(Particle.PORTAL, p.getLocation().add(0, 0.5, 0), 60, 0.5, 0.5, 0.5, 0.5);

                Location loc;

                if (!w.getBlockAt(b.getLocation().add(0, 1, 0)).getType().isSolid()
                        && !w.getBlockAt(b.getLocation().add(0, 2, 0)).getType().isSolid()) {
                    loc = b.getLocation();
                    loc.setPitch(p.getLocation().getPitch());
                    loc.setYaw(p.getLocation().getYaw());
                    loc.add(0.5, 1, 0.5);
                } else {
                    RayTraceResult result = w.rayTraceBlocks(p.getLocation(), p.getLocation().getDirection(), 20d,
                            FluidCollisionMode.NEVER, true);
                    if (result == null) {
                        cooldowns.remove(p.getUniqueId());
                        return;
                    }
                    loc = w.getBlockAt(b.getLocation().add(result.getHitBlockFace().getDirection())).getLocation();
                    loc.setPitch(p.getLocation().getPitch());
                    loc.setYaw(p.getLocation().getYaw());
                    p.teleport(loc.add(0.5, 0, 0.5));
                }
                p.teleport(loc);
                w.spawnParticle(Particle.PORTAL, p.getLocation().add(0, 0.5, 0), 60, 0.5, 0.5, 0.5, 0.5);
                w.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.5f, 0.5f);
            }
        }

    }



}
