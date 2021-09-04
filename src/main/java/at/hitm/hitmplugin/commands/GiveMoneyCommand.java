/**
 * @Author: Manuel Puchner
 * @Date: 02.09.21
 */

package at.hitm.hitmplugin.commands;

import at.hitm.hitmplugin.Main;
import at.hitm.hitmplugin.items.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;



public class GiveMoneyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if( !(sender instanceof Player) ) {
            sender.sendMessage("Only players can use this command");
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 1 && !isNumeric(args[0])) {
            sender.sendMessage(Main.getPrefix() + ChatColor.DARK_RED + "Error: " + ChatColor.GRAY + "bitte eine Zahl als Parameter angeben");
        } else if (!player.isOp()) {
            sender.sendMessage(Main.getPrefix() + ChatColor.DARK_RED + "Error: " + ChatColor.GRAY + "Du musst Operator Status besitzen");
        } else {
            int amount = 1;
            if(args.length == 1 && isNumeric(args[0])) {
                amount = Integer.parseInt(args[0]);
            }


            PlayerInventory inv =  player.getInventory();

            for (int i = 0; i < amount; i++) {
                inv.addItem(ItemManager.HITMcoin);
            }
        }
        return true;
    }

    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
