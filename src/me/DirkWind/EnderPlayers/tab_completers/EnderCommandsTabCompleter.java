package me.DirkWind.EnderPlayers.tab_completers;

import me.DirkWind.EnderPlayers.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnderCommandsTabCompleter implements TabCompleter {

    private Main plugin;

    public EnderCommandsTabCompleter(Main plugin) {
        this.plugin = plugin;
        this.plugin.getCommand("enderhands").setTabCompleter(this);
        this.plugin.getCommand("enderteleport").setTabCompleter(this);
        this.plugin.getCommand("tpstick").setTabCompleter(this);
        this.plugin.getCommand("enderplayer").setTabCompleter(this);
        this.plugin.getCommand("endereyes").setTabCompleter(this);
        this.plugin.getCommand("enderwater").setTabCompleter(this);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> arguments = new ArrayList<>();
        List<String> result = new ArrayList<>();
        int argIndex = 0;

        if (args.length == 1) {
            for (Player p : plugin.getServer().getOnlinePlayers()) {
                arguments.add(p.getName());
            }
            arguments.addAll(Arrays.asList("@a", "@r", "@s", "@p"));
        } else if (args.length == 2) {
            arguments.addAll(Arrays.asList("true", "false"));
            if (!label.equals("enderplayer")) arguments.add("toggle");
            argIndex = 1;
        }

        for (String a : arguments) {
            if (a.toLowerCase().startsWith(args[argIndex].toLowerCase())) {
                result.add(a);
            }
        }


        return result;
    }
}
