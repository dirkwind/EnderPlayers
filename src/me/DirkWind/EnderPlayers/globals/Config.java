package me.DirkWind.EnderPlayers.globals;

import me.DirkWind.EnderPlayers.Main;
import me.DirkWind.EnderPlayers.utils.CMFile;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.*;

public class Config extends CMFile {

    private static Config instance;

    public static Config getInstance() {
        return instance;
    }

    public Config() {
        super(Main.getInstance(), "config");
        setDefaultTitleWidth(60);
        setDescription("Based off of Ranboo's Dream SMP charater, EnderPlayers attempts to turn players into Endermen.");
        addLink("Source Code", "https://github.com/dirkwind/EnderPlayers");
        instance = this;
        load();
    }

    public void loadDefaults() {
        /* ---------- VALUES ---------- */

        // enderplayer

        addDefault("enderplayer.includes-endertp", true);
        addDefault("enderplayer.includes-enderhands", true);
        addDefault("enderplayer.includes-endereyes", true);

        // enderteleport

        addDefault("endertp.cooldown", 5.00);
        addDefault("endertp.cooldown-message", "Cooldown: {cooldown}s");
        addDefault("endertp.max-tp-distance", 24);
        addDefault("endertp.play-sounds", true);
        addDefault("endertp.show-particles", true);
        addDefault("endertp.fall-damage", false);
        addDefault("endertp.tp-stick.give-on-command", true);
        addDefault("endertp.tp-stick.name", "&6&lTP Stick");
        addDefault("endertp.tp-stick.lore", "&o&5Allows you to teleport provided you have the enderteleport power.");

        // enderhands

        addDefault("enderhands.whitelist-blocks", false);
        addDefault("enderhands.block-blacklist", new ArrayList<>(Arrays.asList("BEDROCK", "BARRIER")));

        /* ---------- COMMENTS ---------- */

        // enderplayer

        addComment("enderplayer", "Settings for the EnderPlayer command");

        addComment("enderplayer.includes-endertp", "The following values determine whether or not a power"
                + " is granted to the targets when the /enderplayer command is executed.\nSee the endertp section to edit related settings.");
        addComment("enderplayer.includes-enderhands", "See the enderhands section to edit related settings.");
        addComment("enderplayer.includes-endereyes", "This power has no settings.");

        // enderteleport

        addComment("endertp", "Settings related to EnderTeleportation");

        addComment("endertp.cooldown", "The cooldown (in seconds) for teleporting.");
        addComment("endertp.cooldown-message", "The message displayed that informs the player of their current cooldown."
                + "\nMake sure to include {cooldown} in the message because that is the where the cooldown time will appear in the message.");
        addComment("endertp.max-tp-distance", "The maximum number of blocks a player can teleport.");
        addComment("endertp.play-sounds", "Determines whether or not a sound is play upon a player teleporting");
        addComment("endertp.show-particles", "Determines whether or not particles appear when players teleport.");
        addComment("endertp.fall-damage", "Determines whether or not a player will take fall damage when teleporting"
                + "\nIf set to true, players will take the amount of fall damage relative to the number of blocks they've fallen before teleporting.");
        addComment("endertp.tp-stick", "Settings for the TP Stick item.");
        addComment("endertp.tp-stick.give-on-command", "Determines whether or not the enderteleport command will give targeted players a TP Stick"
                +"\nIf false, players will have to craft the TP Stick on their own or receive it by other means.");
        addComment("endertp.tp-stick.name", "The name of the TP Stick given to players when they receive the enderteleport power.");
        addComment("endertp.tp-stick.lore", "The lore of the TP Stick given to players when they receive the enderteleport power."
                + "\nUse newline characters (\\n) to create a new line in the lore.");

        // enderhands

        addComment("enderhands", "Settings related to EnderHands");

        addComment("enderhands.whitelist-blocks", "If true, the block-blacklist will instead be interpreted as a whitelist.");
        addComment("enderhands.block-blacklist",
                "If whitelist-blocks is false, the listed blocks will not be affected by the enderhands power."
                + "\nIf whitelist-blocks is true, the listed blocks will be the only blocks affected by the enderhands power."
                + "\nGo to https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html for a list of block names.");
    }

    public double getTPCooldown() {
        return getConfig().getDouble("endertp.cooldown");
    }

    public int getMaxTpDistance() {
        return getConfig().getInt("endertp.max-tp-distance");
    }

    public boolean getPlayTPSounds() {
        return getConfig().getBoolean("endertp.play-sounds");
    }

    public boolean getKeepFallDamageOnTP() {
        return getConfig().getBoolean("endertp.fall-damage");
    }

    public boolean getShowTPParticles() {
        return getConfig().getBoolean("endertp.show-particles");
    }

    public boolean getTPStickOnCommand() {
        return getConfig().getBoolean("endertp.tp-stick.give-on-command");
    }

    public boolean enderplayerIncludesEnderTP() {
        return getConfig().getBoolean("enderplayer.includes-endertp");
    }

    public boolean enderplayerIncludesEnderHands() {
        return getConfig().getBoolean("enderplayer.includes-enderhands");
    }

    public boolean enderplayerIncludesEndereyes() {
        return getConfig().getBoolean("enderplayer.includes-endereyes");
    }

    public String getTPCooldownMessage() {
        String rawText = getConfig().getString("endertp.cooldown-message");
        if (rawText == null) return "";
        rawText = rawText.replaceAll("[{]cooldown[}]", "%.2f");
        return ChatColor.translateAlternateColorCodes('&', rawText);
    }

    public String getTPStickName() {
        String rawText = getConfig().getString("endertp.tp-stick.name");
        if (rawText == null) return "";
        return ChatColor.translateAlternateColorCodes('&', rawText);
    }

    public List<String> getTPStickLore() {
        String text = getConfig().getString("endertp.tp-stick.lore");
        if (text == null) return Arrays.asList("");
        text = ChatColor.translateAlternateColorCodes('&', text);
        return Arrays.asList(text.split("\n"));
    }

    /**
     * Returns whether or not the block blacklist should instead be interpreted as a whitelist
     * */
    public boolean getEnderhandsBlacklistIsWhitelist() {
        return getConfig().getBoolean("enderhands.whitelist-blocks");
    }

    public Set<Material> getEnderhandsBlockBlacklist() {
        List<String> rawList = getConfig().getStringList("enderhands.block-blacklist");
        Set<Material> blacklist = new HashSet<>();
        for (String blockName : rawList) {
            try {
                Material mat = Material.valueOf(blockName);
                blacklist.add(mat);
            } catch (IllegalArgumentException e) {
                // do nothing
            }
        }
        return blacklist;
    }

}
