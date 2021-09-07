package at.hitm.hitmplugin.warps;

import at.hitm.hitmplugin.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            Main.send(sender, "Valid Warps: " + WarpManager.warps.keySet());
            return true;
        } else {
            if (WarpManager.warps.containsKey(args[0].toLowerCase())) {
                // warp the player to the location
                Warp warp = WarpManager.warps.get(args[0].toLowerCase());
                ((Player) sender).teleport(warp.getLocation());
                Main.send(sender, "Warped you to the location " + ChatColor.YELLOW + warp.getName());
            } else {
                Main.send(sender, "Invalid Warp.");
                return true;
            }
        }
        return true;
    }
}