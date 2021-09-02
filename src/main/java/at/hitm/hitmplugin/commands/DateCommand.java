/**
 * @Author: Manuel Puchner
 * @Date: 02.09.21
 */

package at.hitm.hitmplugin.commands;

import at.hitm.hitmplugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Date;

public class DateCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Date date = new Date();
        sender.sendMessage(Main.getPrefix() + "Es ist gerade " + ChatColor.GOLD + date.toString());
        return false;
    }
}
