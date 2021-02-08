package me.DirkWind.EnderPlayers.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.DirkWind.EnderPlayers.globals.Config;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.Hash;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TPStickItem {


    private static TPStickItem instance = null;
    private Config config;

    public TPStickItem() {
        config = Config.getInstance();
    }

    public static TPStickItem getInstance() {
        if (instance == null) {
            instance = new TPStickItem();
        }

        return instance;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ItemStack)) {
            return false;
        }
        ItemStack item = (ItemStack) obj;
        if (item.getItemMeta() != null) {
            if (item.getItemMeta().hasCustomModelData()) {
                return item.getItemMeta().getCustomModelData() == 7837;
            }
        }
        return false;
    }

    public ItemStack getItem() {

        ItemStack boots = new ItemStack(Material.STICK);
        ItemMeta meta = boots.getItemMeta();

        meta.setDisplayName(config.getTPStickName());

        List<String> lore = new ArrayList<>(config.getTPStickLore());
        meta.setLore(lore);

        meta.setCustomModelData(7837);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
        meta.setUnbreakable(true);

        boots.setItemMeta(meta);

        return boots;
    }


}