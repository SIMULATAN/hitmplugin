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

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage(Main.getPrefix() + ChatColor.GOLD + "Servus auf dem server der coolsten HITM");
        event.setJoinMessage(Main.getPrefix() + ChatColor.RED + player.getName() + ChatColor.WHITE + "Hat den Server betreten");
    }
}