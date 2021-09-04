/**
 * @Author: Manuel Puchner
 * @Date: 02.09.21
 */

package at.hitm.hitmplugin.commands;

import at.hitm.hitmplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetOnlinePlayersCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        int counter = 0;
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            ++counter;
            sender.sendMessage(Main.getPrefix() + "Player " + ChatColor.DARK_GRAY + "( " + ChatColor.GRAY + counter + ChatColor.DARK_GRAY + " ): " + ChatColor.AQUA + player.getName());
        }
        return false;
    }
}
