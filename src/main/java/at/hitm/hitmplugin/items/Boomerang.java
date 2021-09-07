package at.hitm.hitmplugin.items;

import at.hitm.hitmplugin.Main;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Boomerang implements Listener {

    public final ItemStack boomerang;

    public Boomerang() {
        ItemStack itemStack = new ItemStack(Material.BONE, 1);
        ItemMeta meta = itemStack.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.WHITE + "Boomerang");
        meta.setCustomModelData(1001);
        List<String> lore = new ArrayList<>();

        lore.add("This boomerang always comes back!");

        meta.setLore(lore);
        itemStack.setItemMeta(meta);

        boomerang = itemStack;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onThrow(PlayerInteractEvent e) {
        if (e.getItem() != null && e.getItem().getItemMeta() != null && e.getItem().hasItemMeta() && e.getItem().getType() == boomerang.getType() && e.getItem().getItemMeta().hasCustomModelData() && e.getItem().getItemMeta().getCustomModelData() == boomerang.getItemMeta().getCustomModelData()) {
            e.setCancelled(true);
            e.getPlayer().getItemInHand().setType(Material.GHAST_TEAR);
            ArmorStand as = (ArmorStand) e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation(), EntityType.ARMOR_STAND);
            final Location destination = e.getPlayer().getLocation().add(e.getPlayer().getLocation().getDirection().multiply(10));

            as.setArms(true);
            as.setGravity(false);
            as.setVisible(false);
            as.setItemInHand(new ItemStack(Material.BONE));
            as.setRightArmPose(new EulerAngle(Math.toRadians(0), Math.toRadians(120), Math.toRadians(0)));

            Vector vector = destination.subtract(e.getPlayer().getLocation()).toVector();

            new BukkitRunnable() {
                int distance = 14;
                int i = 0;

                @Override
                public void run() {
                    EulerAngle rot = as.getRightArmPose();
                    EulerAngle rotNew = rot.add(0, 20, 0);
                    as.setRightArmPose(rotNew);

                    if (i >= distance) {
                        as.teleport(as.getLocation().subtract(vector.normalize()));
                        if (i >= distance * 2) {
                            as.remove();
                            cancel();
                            for (ItemStack thisStack : e.getPlayer().getInventory().getContents()) {
                                if (thisStack != null && thisStack.hasItemMeta() && thisStack.getItemMeta().hasCustomModelData() && thisStack.getItemMeta().getCustomModelData() == boomerang.getItemMeta().getCustomModelData())
                                    thisStack.setType(Material.BONE);
                            }
                        }
                    } else {
                        as.teleport(as.getLocation().add(vector.normalize()));
                    }

                    i++;

                    for (Entity entity : as.getLocation().getChunk().getEntities()) {
                        if (entity instanceof LivingEntity && as.getLocation().distanceSquared(entity.getLocation()) < 2 && !(entity instanceof Player)) {
                            LivingEntity livingEntity = (LivingEntity) entity;
                            livingEntity.damage(i >= distance * 2 ? 30 : 20, e.getPlayer());
                        }
                    }
                }
            }.runTaskTimer(Main.getInstance(), 1L, 1L);
        }
    }
}