package at.hitm.hitmplugin.commands;

import at.hitm.hitmplugin.items.ItemManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveCustomItemsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ((Player) sender).getInventory().addItem(ItemManager.boomerang.boomerang, ItemManager.explosiveBow.bow, ItemManager.teleportSword.sword);
        return true;
    }
}