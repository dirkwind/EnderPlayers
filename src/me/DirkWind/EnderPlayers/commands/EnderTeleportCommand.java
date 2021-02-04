package me.DirkWind.EnderPlayers.commands;

import me.DirkWind.EnderPlayers.Main;
import me.DirkWind.EnderPlayers.globals.EnderTeleport;
import me.DirkWind.EnderPlayers.items.TPStickItem;
import me.DirkWind.EnderPlayers.utils.CommandUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.*;

public class EnderTeleportCommand implements CommandExecutor {

    private final Main plugin;
    private final String syntaxGuide = "/enderteleport <target> {true|false|toggle}";
    private final CommandUtils cu;

    public EnderTeleportCommand(Main plugin) {
        this.plugin = plugin;
        this.plugin.getCommand("enderteleport").setExecutor(this);
        cu = new CommandUtils(plugin, "enderteleport");
    }

    private void givePlayerItem(Player p, ItemStack item) {
        if (p.getInventory().firstEmpty() == -1) {
            Location loc = p.getLocation();
            World world = p.getWorld();

            world.dropItemNaturally(loc, item);

        } else {
            p.getInventory().addItem(item);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender.hasPermission("enderteleport.use")) {
            if (args.length == 2) {

                String target = args[0];
                String action = args[1];
                Set<Player> targets = cu.getTargets(sender, target);

                if (!(action.equalsIgnoreCase("true") || action.equalsIgnoreCase("false")
                        || action.equalsIgnoreCase("toggle"))) {
                    sender.sendMessage(ChatColor.RED + "Invalid syntax; correct syntax is: " + syntaxGuide);
                    return false;
                }

                for (Player p : targets) {
                    UUID id = p.getUniqueId();
                    boolean value;
                    try {
                        if (action.equalsIgnoreCase("true")) {
                            EnderTeleport.setTrue(id);
                            value = true;
                        } else if (action.equalsIgnoreCase("false")) {
                            EnderTeleport.setFalse(id);
                            value = false;
                        } else {
                            value = EnderTeleport.toggle(id);
                        }
                        if (value) {
                            givePlayerItem(p, TPStickItem.getInstance().getItem());
                        }
                        if (!(target.equalsIgnoreCase("@a"))) {
                            sender.sendMessage(String.format("%s's enderteleport value was set to %b", p.getName(), value));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        sender.sendMessage(ChatColor.RED +
                                String.format("There was an error while updating %s's enderteleport value.", p.getName()));
                    }
                }

                if (target.equalsIgnoreCase("@a")) {
                    sender.sendMessage("All online players' enderteleport values were updated.");
                }

                return targets.size() > 0;

            } else {
                sender.sendMessage(ChatColor.RED + "Incorrect syntax; correct syntax is: " + syntaxGuide);
            }
        }



        return false;
    }
}
