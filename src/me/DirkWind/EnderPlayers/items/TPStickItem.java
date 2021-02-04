package me.DirkWind.EnderPlayers.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class TPStickItem {

    private static final String itemName = ChatColor.GOLD + "" + ChatColor.BOLD + "TP Stick";
    private static TPStickItem instance = null;

    public static TPStickItem getInstance() {
        if (instance == null) {
            instance = new TPStickItem();
        }

        return instance;
    }

    public boolean equals(ItemStack item) {
        if (item != null)
            if (item.getItemMeta() != null)
                if (item.getItemMeta().getDisplayName().equals(itemName)) {
                    return true;
                }
        return false;
    }

    public ItemStack getItem() {

        ItemStack boots = new ItemStack(Material.STICK);
        ItemMeta meta = boots.getItemMeta();

        meta.setDisplayName(itemName);

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Allows you to teleport if capable.");
        meta.setLore(lore);

        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
        meta.setUnbreakable(true);

        boots.setItemMeta(meta);

        return boots;
    }


}
