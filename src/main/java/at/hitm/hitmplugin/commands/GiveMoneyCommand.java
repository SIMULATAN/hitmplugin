/**
 * @Author: Manuel Puchner
 * @Date: 02.09.21
 */

package at.hitm.hitmplugin.commands;

import at.hitm.hitmplugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;


public class GiveMoneyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(args.length != 1) {
            sender.sendMessage(ChatColor.DARK_RED + "Error: " + ChatColor.GRAY + "bitte gültige Anzahl angeben");
        } else if (!isNumeric(args[0])) {
            sender.sendMessage(Main.getPrefix() + ChatColor.DARK_RED + "Error: " + ChatColor.GRAY + "bitte eine Zahl als Parameter angeben");
        } else if (!player.isOp()) {
            sender.sendMessage(Main.getPrefix() + ChatColor.DARK_RED + "Error: " + ChatColor.GRAY + "Du musst Operator Status besitzen");
        } else {
            int amount = Integer.parseInt(args[0]);

            ItemStack itemStack = new ItemStack(Material.EMERALD, amount);
            ItemMeta meta = itemStack.getItemMeta();
            assert meta != null;
            meta.setDisplayName(ChatColor.AQUA + "HITMcoin");
            meta.addEnchant(Enchantment.LOYALTY, 10, true);
            meta.setCustomModelData(1);
            List<String> lore = new ArrayList<>();

            lore.add("This is the official currency of the hitm server");
            lore.add(ChatColor.GREEN + "Value: 1HIT (HITMcoin)");

            meta.setLore(lore);
            itemStack.setItemMeta(meta);

            player.getInventory().addItem(itemStack);
        }
        return false;
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
