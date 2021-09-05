package at.hitm.hitmplugin;

import at.hitm.hitmplugin.api.API;
import at.hitm.hitmplugin.commands.DateCommand;
import at.hitm.hitmplugin.commands.GetOnlinePlayersCommand;
import at.hitm.hitmplugin.commands.GiveMoneyCommand;
import at.hitm.hitmplugin.listeners.APIListener;
import at.hitm.hitmplugin.listeners.JoinLeaveListener;
import at.hitm.hitmplugin.utils.PerformanceMonitor;
import at.hitm.hitmplugin.utils.TPSRunnable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getLogger().fine("Plugin wird aktiviert...");

        listenerRegistration();
        commandRegistration();
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new TPSRunnable(this), 0, 40);
        // start the webserver
        API.init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        API.shutdown();
    }

    /**
     * Gets the prefix of the plugin
     * @return the prefix
     */
    public static String getPrefix() {
        return ChatColor.DARK_GRAY +  "[" + ChatColor.GOLD + "HITM" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY;
    }

    private void listenerRegistration() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new JoinLeaveListener(), this);
        pluginManager.registerEvents(new APIListener(), this);
    }

    private void commandRegistration() {
        getCommand("date").setExecutor(new DateCommand());
        getCommand("getOnlinePlayers").setExecutor(new GetOnlinePlayersCommand());
        getCommand("giveMoney").setExecutor(new GiveMoneyCommand());
    }

    public static Main getInstance() {
        return instance;
    }
}
