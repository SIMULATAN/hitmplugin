package at.hitm.hitmplugin.items;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeleportSword implements Listener {

    public ItemStack sword;

    public TeleportSword() {
        ItemStack itemStack = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta meta = itemStack.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.BLUE + "Teleport Sword");
        meta.setCustomModelData(1021);
        List<String> lore = new ArrayList<>();

        lore.add("Right click to teleport 5 blocks away!");

        meta.setLore(lore);
        itemStack.setItemMeta(meta);

        sword = itemStack;
    }

    private static HashMap<Player, Long> lastTime = new HashMap<>();

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getItem() != null && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && e.getItem().getType() == sword.getType() && e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasCustomModelData() && e.getItem().getItemMeta().getCustomModelData() == sword.getItemMeta().getCustomModelData()) {
            if (lastTime.containsKey(player)) {
                long time = lastTime.get(player);
                if (System.currentTimeMillis() - time < 250) {
                    player.sendMessage(ChatColor.RED + "This item is still on cooldown!");
                    return;
                }
            }
            Block block = e.getPlayer().getTargetBlock(null, 5);
            Location location = block.getLocation();
            float pitch = player.getEyeLocation().getPitch();
            float yaw = player.getEyeLocation().getYaw();
            location.add(0, 1, 0);
            location.setPitch(pitch);
            location.setYaw(yaw);
            player.setVelocity(new Vector(0, 0, 0));
            player.setFallDistance(0);
            player.teleport(location);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 5, 5);
            lastTime.put(player, System.currentTimeMillis());
        }
    }
}