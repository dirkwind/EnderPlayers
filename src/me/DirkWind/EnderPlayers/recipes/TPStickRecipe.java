package me.DirkWind.EnderPlayers.recipes;

import me.DirkWind.EnderPlayers.Main;
import me.DirkWind.EnderPlayers.items.TPStickItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;


public class TPStickRecipe {

    private Main plugin;

    public TPStickRecipe (Main plugin) {
        this.plugin = plugin;
        this.plugin.getServer().addRecipe(getRecipe());
    }

    public ShapedRecipe getRecipe() {
        ItemStack item = TPStickItem.getInstance().getItem();

        NamespacedKey key = new NamespacedKey(this.plugin, "tp_stick");

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("S", "E", "S");

        recipe.setIngredient('S', Material.STICK);
        recipe.setIngredient('E', Material.ENDER_PEARL);

        return recipe;
    }
}

