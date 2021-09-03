/**
 * @Author: Manuel Puchner
 * @Date: 02.09.21
 */

package at.hitm.hitmplugin.listeners;

import at.hitm.hitmplugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage(Main.getPrefix() + ChatColor.GOLD + "Servus auf dem Server der coolsten HITM :^)");
        event.setJoinMessage("[" + ChatColor.GREEN + "+" + ChatColor.WHITE + "] " + ChatColor.GRAY + player.getName());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage("[" + ChatColor.RED + "-" + ChatColor.WHITE + "] " + ChatColor.GRAY + player.getName());
    }
}