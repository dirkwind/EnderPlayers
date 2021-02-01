package me.DirkWind.EnderPlayers.listeners;

import me.DirkWind.EnderPlayers.Main;
import me.DirkWind.EnderPlayers.globals.EnderHands;
import org.bukkit.GameMode;
//import org.bukkit.Material;
import org.bukkit.block.Block;
//import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
//import org.bukkit.inventory.meta.ItemMeta;

public class EnderHandListener implements Listener {

    private Main plugin;
    //private ItemStack fistEquivalent;

    public EnderHandListener(Main plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);

        /*
        fistEquivalent = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta meta = fistEquivalent.getItemMeta();
        meta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
        fistEquivalent.setItemMeta(meta);
        */
    }

    @EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent event) {
        Player p = event.getPlayer();
        Block b = event.getBlock();
        if (EnderHands.getPlayer(p) && p.getInventory().getItemInMainHand().getType().isAir()
                && p.getGameMode() != GameMode.CREATIVE) {
            //b.breakNaturally(fistEquivalent);
            if (b.getType().isSolid()) {
                event.setDropItems(false);
                b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(b.getType()));
            }
        }
    }

}
