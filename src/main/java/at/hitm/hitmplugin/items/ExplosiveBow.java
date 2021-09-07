package at.hitm.hitmplugin.items;

import at.hitm.hitmplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ExplosiveBow implements Listener {

    public ItemStack bow;

    public ExplosiveBow() {
        ItemStack itemStack = new ItemStack(Material.BOW, 1);
        ItemMeta meta = itemStack.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.WHITE + "Explosive Bow");
        meta.setCustomModelData(1011);
        List<String> lore = new ArrayList<>();

        lore.add("This bow shoots exploding arrows!");

        meta.setLore(lore);
        itemStack.setItemMeta(meta);

        bow = itemStack;
    }

    @EventHandler
    public void onDamage(ProjectileHitEvent e) {
        Projectile pj = e.getEntity();
        if (pj.getShooter() instanceof Player && pj.getType() == EntityType.ARROW) {
            Player shooter = (Player) pj.getShooter();
            if (shooter.getInventory().getItemInMainHand().getType() == Material.BOW && shooter.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == bow.getItemMeta().getCustomModelData()) {
                pj.getWorld().createExplosion(pj.getLocation(), 5f, false, false, shooter);
            }
        }
    }
}