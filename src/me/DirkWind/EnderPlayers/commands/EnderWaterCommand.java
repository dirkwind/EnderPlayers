package me.DirkWind.EnderPlayers.commands;

import me.DirkWind.EnderPlayers.Main;
import me.DirkWind.EnderPlayers.globals.EnderEyes;
import me.DirkWind.EnderPlayers.globals.EnderWater;
import me.DirkWind.EnderPlayers.utils.CommandUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.*;

public class EnderWaterCommand implements CommandExecutor {

    private final Main plugin;
    private final String syntaxGuide = "/enderwater <target> {true|false|toggle}";
    private final CommandUtils cu;

    public EnderWaterCommand(Main plugin) {
        this.plugin = plugin;
        this.plugin.getCommand("enderwater").setExecutor(this);
        cu = new CommandUtils(plugin, "enderwater");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender.hasPermission("enderwater.use")) {
            if (args.length == 2) {

                String target = args[0];
                String action = args[1];
                Set<Player> targets = cu.getTargets(sender, target);

                if (!(action.equalsIgnoreCase("true") || action.equalsIgnoreCase("false")
                        || action.equalsIgnoreCase("toggle"))) {
                    sender.sendMessage(ChatColor.RED + "Invalid syntax; correct syntax is: " + syntaxGuide);
                    return true;
                }

                for (Player p : targets) {
                    UUID id = p.getUniqueId();
                    boolean value;
                    try {
                        if (action.equalsIgnoreCase("true")) {
                            EnderWater.setTrue(id);
                            value = true;
                        } else if (action.equalsIgnoreCase("false")) {
                            EnderWater.setFalse(id);
                            value = false;
                        } else {
                            value = EnderWater.toggle(id);
                        }
                        if (!(target.equalsIgnoreCase("@a"))) {
                            sender.sendMessage(String.format("%s's enderwater value was set to %b", p.getName(), value));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        sender.sendMessage(ChatColor.RED +
                                String.format("There was an error while updating %s's enderwater value.", p.getName()));
                    }
                }

                if (target.equalsIgnoreCase("@a")) {
                    sender.sendMessage("All online players' enderwater values were updated.");
                }

                return targets.size() > 0;

            } else {
                sender.sendMessage(ChatColor.RED + "Incorrect syntax; correct syntax is: " + syntaxGuide);
            }
        }



        return false;
    }
}
