package me.DirkWind.EnderPlayers.items;

import java.util.ArrayList;
import java.util.List;

import me.DirkWind.EnderPlayers.globals.Config;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TPStickItem {


    private static TPStickItem instance = null;
    private final int itemCode;
    private Config config;

    public TPStickItem() {
        config = Config.getInstance();
        itemCode = 7837;
    }

    public static TPStickItem getInstance() {
        if (instance == null) {
            instance = new TPStickItem();
        }

        return instance;
    }

    public boolean equals(ItemStack item) {
        if (item != null)
            if (item.getItemMeta() != null)
                if (item.getItemMeta().hasCustomModelData()) {
                    return item.getItemMeta().getCustomModelData() == itemCode;
                }
        return false;
    }

    public ItemStack getItem() {

        ItemStack boots = new ItemStack(Material.STICK);
        ItemMeta meta = boots.getItemMeta();

        meta.setDisplayName(config.getTPStickName());

        List<String> lore = new ArrayList<>(config.getTPStickLore());
        meta.setLore(lore);

        meta.setCustomModelData(itemCode);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
        meta.setUnbreakable(true);

        boots.setItemMeta(meta);

        return boots;
    }


}
