package me.DirkWind.EnderPlayers.commands;

import me.DirkWind.EnderPlayers.Main;
import me.DirkWind.EnderPlayers.items.TPStickItem;
import me.DirkWind.EnderPlayers.utils.CommandUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class TPStickCommand implements CommandExecutor {

    private Main plugin;
    private final String syntaxGuide = "/tpstick <targets>";
    private CommandUtils cu;

    public TPStickCommand(Main plugin) {
        this.plugin = plugin;
        this.plugin.getCommand("tpstick").setExecutor(this);
        cu = new CommandUtils(plugin, "tpstick");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender.hasPermission("tpstick.use")) {
            ItemStack tpStick = TPStickItem.getInstance().getItem();
            if (args.length == 0) {
                if (sender instanceof Player) {
                    CommandUtils.givePlayerItem((Player) sender, tpStick);
                    sender.sendMessage("You received a TP Stick!");
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "Invalid syntax; correct syntax is: " + syntaxGuide);
                }
            } else if (args.length == 1) {
                String target = args[0];
                Set<Player> targets = cu.getTargets(sender, target);

                for (Player p : targets) {
                    CommandUtils.givePlayerItem(p, tpStick);
                    if (!target.equalsIgnoreCase("@a")) {
                        sender.sendMessage("Gave a TP Stick to " + p.getName() + "!");
                    }
                }
                if (target.equalsIgnoreCase("@a")) {
                    sender.sendMessage("Gave a TP Stick to all online players!");
                }
                return true;
            }
        }

        return false;
    }
}
