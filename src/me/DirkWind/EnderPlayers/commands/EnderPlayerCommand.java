package me.DirkWind.EnderPlayers.commands;

import me.DirkWind.EnderPlayers.Main;
import me.DirkWind.EnderPlayers.globals.*;
import me.DirkWind.EnderPlayers.items.TPStickItem;
import me.DirkWind.EnderPlayers.utils.CommandUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.*;

public class EnderPlayerCommand implements CommandExecutor {

    private final Main plugin;
    private final String syntaxGuide = "/enderplayer <target> {true|false}";
    private final String incorrectSyntaxMessage = ChatColor.RED + "Invalid syntax; correct syntax is: " + syntaxGuide;
    private final Config config;
    private final CommandUtils cu;

    public EnderPlayerCommand(Main plugin) {
        this.plugin = plugin;
        this.plugin.getCommand("enderplayer").setExecutor(this);
        config = Config.getInstance();
        cu = new CommandUtils(plugin, "enderplayer");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender.hasPermission("enderteleport.use")) {
            if (args.length == 2) {

                String target = args[0];
                String action = args[1];
                Set<Player> targets = cu.getTargets(sender, target);

                if (!(action.equalsIgnoreCase("true") || action.equalsIgnoreCase("false"))) {
                    sender.sendMessage(incorrectSyntaxMessage);
                    return false;
                }

                boolean giveTPStick = Config.getInstance().getTPStickOnCommand();
                ItemStack tpStick = TPStickItem.getInstance().getItem();

                boolean includesEnderhands = config.enderplayerIncludesEnderHands();
                boolean includesEnderTP = config.enderplayerIncludesEnderTP();
                boolean includesEndereyes = config.enderplayerIncludesEndereyes();
                boolean includesEnderwater = config.enderplayerIncludesEnderwater();

                for (Player p : targets) {
                    UUID id = p.getUniqueId();
                    boolean value;
                    try {
                        if (action.equalsIgnoreCase("true")) {
                            if (includesEnderhands) EnderHands.setTrue(id);
                            if (includesEnderTP) EnderTeleport.setTrue(id);
                            if (includesEndereyes) EnderEyes.setTrue(id);
                            if (includesEnderwater) EnderWater.setTrue(id);
                            value = true;
                        } else {
                            if (includesEnderhands) EnderHands.setFalse(id);
                            if (includesEnderTP) EnderTeleport.setFalse(id);
                            if (includesEndereyes) EnderEyes.setFalse(id);
                            if (includesEnderwater) EnderWater.setFalse(id);
                            value = false;
                        }
                        if (value && giveTPStick && config.enderplayerIncludesEnderTP()) {
                            CommandUtils.givePlayerItem(p, tpStick);
                        }
                        if (!(target.equalsIgnoreCase("@a"))) {
                            sender.sendMessage(String.format("%s's enderplayer value was set to %b", p.getName(), value));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        sender.sendMessage(ChatColor.RED +
                                String.format("There was an error while updating %s's enderplayer value.", p.getName()));
                    }
                }

                if (target.equalsIgnoreCase("@a")) {
                    sender.sendMessage("All online players' enderplayer values were updated.");
                }

                return targets.size() > 0;

            } else {
                sender.sendMessage(incorrectSyntaxMessage);
            }
        }



        return false;
    }
}
