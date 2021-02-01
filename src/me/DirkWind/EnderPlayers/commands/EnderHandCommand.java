package me.DirkWind.EnderPlayers.commands;

import me.DirkWind.EnderPlayers.Main;
import me.DirkWind.EnderPlayers.globals.EnderHands;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.*;

public class EnderHandCommand implements CommandExecutor {

    private Main plugin;
    private final String syntaxGuide = "/enderhands <target> {true|false|toggle}";

    public EnderHandCommand(Main plugin) {
        this.plugin = plugin;
        this.plugin.getCommand("enderhands").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender.hasPermission("enderhands.use")) {
            if (args.length == 2) {

                Set<Player> targets = new HashSet<>();
                String target = args[0];
                String action = args[1];

                if (!(action.equalsIgnoreCase("true") || action.equalsIgnoreCase("false")
                        || action.equalsIgnoreCase("toggle"))) {
                    sender.sendMessage(ChatColor.RED + "Invalid syntax; correct syntax is: " + syntaxGuide);
                }

                if (target.equalsIgnoreCase("@a")) {
                    targets.addAll(plugin.getServer().getOnlinePlayers());
                } else if (target.equalsIgnoreCase("@p")) {
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
                        sender.sendMessage(ChatColor.RED + "You are unable to execute the enderhands command with the target '@p' from the console.");
                        return false;
                    }

                } else if (target.equalsIgnoreCase("@r")) {
                    int item = (int) (Math.random() * plugin.getServer().getOnlinePlayers().size());
                    int i = 0;
                    for (Player p : plugin.getServer().getOnlinePlayers()) {
                        if (i == item) {
                            targets.add(p);
                            break;
                        }
                        i++;
                    }
                } else if (target.equalsIgnoreCase("@s")) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage(ChatColor.RED + "Only players may use '@s' as a target");
                        return false;
                    }
                    targets.add((Player) sender);

                } else if (plugin.getServer().getPlayer(args[0]) != null) {
                    targets.add(plugin.getServer().getPlayer(args[0]));
                } else {
                    sender.sendMessage(ChatColor.RED + "Target not recognized.");
                    return false;
                }

                for (Player p : targets) {
                    UUID id = p.getUniqueId();
                    boolean value;
                    try {
                        if (action.equalsIgnoreCase("true")) {
                            EnderHands.setTrue(id);
                            value = true;
                        } else if (action.equalsIgnoreCase("false")) {
                            EnderHands.setFalse(id);
                            value = false;
                        } else {
                            value = EnderHands.toggle(id);
                        }
                        if (!(target.equalsIgnoreCase("@a"))) {
                            sender.sendMessage(String.format("%s's enderhands value was set to %b", p.getName(), value));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        sender.sendMessage(ChatColor.RED +
                                String.format("There was an error whil updating %s's enderhands value.", p.getName()));
                    }
                }

                if (target.equalsIgnoreCase("@a")) {
                    sender.sendMessage("All online players' enderhands values were updated.");
                }

                return targets.size() > 0;

            } else {
                sender.sendMessage(ChatColor.RED + "Incorrect syntax; correct syntax is: " + syntaxGuide);
            }
        }



        return false;
    }
}
