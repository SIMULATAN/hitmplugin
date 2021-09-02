package at.hitm.hitmplugin;

import at.hitm.hitmplugin.commands.DateCommand;
import at.hitm.hitmplugin.commands.GetOnlinePlayersCommand;
import at.hitm.hitmplugin.listeners.JoinListener;
import at.hitm.hitmplugin.listeners.QuitListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getLogger().fine("Plugin wird aktiviert");

        listenerRegistration();
        commandRegistration();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * Gets the prefix of the plugin
     * @return
     */
    public static String getPrefix() {
        return ChatColor.DARK_GRAY +  "[" + ChatColor.GOLD + "Manuels Plugin" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY;
    }

    private void listenerRegistration() {
        Bukkit.getLogger().fine("Plugin wird deaktiviert");
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new JoinListener(), this);
        pluginManager.registerEvents(new QuitListener(), this);
    }

    private void commandRegistration() {
        getCommand("date").setExecutor(new DateCommand());
        getCommand("getOnlinePlayers").setExecutor(new GetOnlinePlayersCommand());
    }
}
