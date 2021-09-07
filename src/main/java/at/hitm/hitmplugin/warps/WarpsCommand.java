package at.hitm.hitmplugin.warps;

import at.hitm.hitmplugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            return false;
        }
        // required cause you cant use the same variable name twice in a switch case
        Warp warp;
        switch (args[0].toLowerCase()) {
            case "create":
                WarpManager.warps.put(args[1].toLowerCase(), new Warp(args[1], ((Player) sender).getLocation()));
                Main.send(sender, ChatColor.GREEN + "Warp " + ChatColor.YELLOW + args[1] + ChatColor.GREEN + " created.");
                WarpManager.saveToFile();
                break;
            case "delete":
                if (!WarpManager.warps.containsKey(args[1].toLowerCase())) {
                    Main.send(sender, ChatColor.RED + "no Warp with this name found.");
                    break;
                }
                warp = WarpManager.warps.get(args[1].toLowerCase());
                WarpManager.warps.remove(args[1].toLowerCase());
                Main.send(sender, ChatColor.RED + "Warp " + ChatColor.YELLOW + warp.getName() + ChatColor.RED + " deleted.");
                WarpManager.saveToFile();
                break;
            case "info":
                if (!WarpManager.warps.containsKey(args[1].toLowerCase())) {
                    Main.send(sender, ChatColor.RED + "no Warp with this name found.");
                    break;
                }
                warp = WarpManager.warps.get(args[1].toLowerCase());
                Location loc = warp.getLocation();
                Main.send(sender, ChatColor.GOLD + warp.getName() + " @ " + String.format("%.2f / %.2f / %.2f", loc.getX(), loc.getY(), loc.getZ()) + " | " + loc.getWorld().getName() + " (" + loc.getWorld().getEnvironment().name() + ")");
                break;
            default:
                return false;
        }
        return true;
    }
}