/**
 * @Author: Jakob Hofer, Manuel Puchner
 * @Date: 4.9.21
 */

package at.hitm.hitmplugin;

import at.hitm.hitmplugin.api.API;
import at.hitm.hitmplugin.commands.DateCommand;
import at.hitm.hitmplugin.commands.GetOnlinePlayersCommand;
import at.hitm.hitmplugin.commands.GiveCustomItemsCommand;
import at.hitm.hitmplugin.commands.GiveMoneyCommand;
import at.hitm.hitmplugin.listeners.APIListener;
import at.hitm.hitmplugin.listeners.JoinLeaveListener;
import at.hitm.hitmplugin.utils.TPSRunnable;
import at.hitm.hitmplugin.items.ItemManager;
import at.hitm.hitmplugin.listeners.PlayerCraftListener;
import at.hitm.hitmplugin.utils.Utils;
import at.hitm.hitmplugin.warps.WarpCommand;
import at.hitm.hitmplugin.warps.WarpManager;
import at.hitm.hitmplugin.warps.WarpsCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public final class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getLogger().fine("Plugin wird aktiviert...");
        FileConfiguration config = this.getConfig();
        config.addDefault("webserver", false);
        config.options().copyDefaults(true);
        saveConfig();

        listenerRegistration();
        commandRegistration();
        ItemManager.init();
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new TPSRunnable(this), 0, 40);
        WarpManager.loadFromFile();
        // start the webserver
        if (config.getBoolean("webserver"))
            API.init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        WarpManager.saveToFile();
        // properly shutdown the webserver
        if (getConfig().getBoolean("webserver"))
            API.shutdown();
    }

    /**
     * Gets the prefix of the plugin
     *
     * @return the prefix
     */
    public static String getPrefix() {
        return ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "HITM" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY;
    }

    private void listenerRegistration() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerCraftListener(), this);
        pluginManager.registerEvents(new JoinLeaveListener(), this);
        pluginManager.registerEvents(new APIListener(), this);
        pluginManager.registerEvents(ItemManager.boomerang, this);
        pluginManager.registerEvents(ItemManager.explosiveBow, this);
        pluginManager.registerEvents(ItemManager.teleportSword, this);
        pluginManager.registerEvents(ItemManager.hyperion, this);
    }

    private void commandRegistration() {
        getCommand("date").setExecutor(new DateCommand());
        getCommand("getOnlinePlayers").setExecutor(new GetOnlinePlayersCommand());
        getCommand("giveMoney").setExecutor(new GiveMoneyCommand());
        getCommand("getOnlinePlayers").setExecutor(new GetOnlinePlayersCommand());
        getCommand("giveMoney").setExecutor(new GiveMoneyCommand());
        getCommand("givecustomitems").setExecutor(new GiveCustomItemsCommand());
        // used to setup warps
        PluginCommand warps = getCommand("warps");
        warps.setExecutor(new WarpsCommand());
        warps.setTabCompleter((sender, command, alias, args) -> {
            if (args[0].isEmpty())
                return Arrays.asList("create", "delete", "info");
            else if (args.length == 1)
                return Utils.getElementsStartingWith(Arrays.asList("create", "delete", "info"), args[0].toLowerCase());
            else if (args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("info")) return Utils.getElementsStartingWith(new ArrayList<>(WarpManager.warps.keySet()), args[1].toLowerCase());
            return Collections.singletonList("");
        });
        // used just to teleport to a specific warp
        PluginCommand warp = getCommand("warp");
        warp.setExecutor(new WarpCommand());
        warp.setTabCompleter((sender, command, alias, args) -> {
            if (args.length <= 1) return Utils.getElementsStartingWith(new ArrayList<>(WarpManager.warps.keySet()), args[0].toLowerCase());
            return null;
        });
    }

    public static void send(CommandSender sender, String message) {
        send((Player) sender, message);
    }

    public static void send(Player player, String message) {
        player.sendMessage(getPrefix() + message);
    }

    public static Main getInstance() {
        return instance;
    }
}
