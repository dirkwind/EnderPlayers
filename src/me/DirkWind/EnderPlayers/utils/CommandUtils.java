package me.DirkWind.EnderPlayers.utils;

import me.DirkWind.EnderPlayers.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommandUtils {

    private Main plugin;
    private String commandName;

    public CommandUtils(Main plugin, String commandName) {
        this.commandName = commandName;
        this.plugin = plugin;
    }

    public static void givePlayerItem(Player p, ItemStack item) {
        if (p.getInventory().firstEmpty() == -1) {
            Location loc = p.getLocation();
            World world = p.getWorld();

            world.dropItemNaturally(loc, item);

        } else {
            p.getInventory().addItem(item);
        }
    }

    public Set<Player> getTargets(CommandSender sender, String target) {
        Set<Player> targets = new HashSet<>();


        if (target.equalsIgnoreCase("@a")) { // All player target
            targets.addAll(plugin.getServer().getOnlinePlayers());

        } else if (target.equalsIgnoreCase("@p")) { // Closest player target
            if (sender instanceof Player) {
                targets.add((Player) sender);
            } else if (sender instanceof BlockCommandSender) {
                BlockCommandSender cb = (BlockCommandSender) sender;
                Location blockLoc = cb.getBlock().getLocation();

                Player closest = null;
                double prevDistance = Double.MAX_VALUE;
                for (Player p : cb.getBlock().getWorld().getPlayers()) {
                    double distance = p.getLocation().distance(blockLoc);
                    if (distance < prevDistance) {
                        closest = p;
                    }
                }

                if (closest != null) {
                    targets.add(closest);
                }
            } else {
                sender.sendMessage(ChatColor.RED +
                        String.format("You are unable to execute the %s command with the target '@p' from the console.",
                                commandName));
            }

        } else if (target.equalsIgnoreCase("@r")) { // Random player target
            ArrayList<Player> onlinePlayers = new ArrayList<>(plugin.getServer().getOnlinePlayers());
            int item = (int) (Math.random() * onlinePlayers.size());
            targets.add(onlinePlayers.get(item));

        } else if (target.equalsIgnoreCase("@s")) { // Self target
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Only players may use '@s' as a target");
            } else {
                targets.add((Player) sender);
            }

        } else if (plugin.getServer().getPlayer(target) != null) { // player target via player name
            targets.add(plugin.getServer().getPlayer(target));
        } else {
            sender.sendMessage(ChatColor.RED + "Target not recognized.");
        }

        return targets;
    }

}
