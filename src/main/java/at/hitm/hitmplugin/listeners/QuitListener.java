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
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(Main.getPrefix() + ChatColor.RED + player.getName() + ChatColor.WHITE + "hat den Server verlassen");
    }
}
