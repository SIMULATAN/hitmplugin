/**
 * @Author: Manuel Puchner
 * @Date: 04.09.21
 */

package at.hitm.hitmplugin.items;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {
    public static ItemStack HITMcoin;

    public static void init() {
        createHITMcoin();
    }

    private static void createHITMcoin() {
        ItemStack itemStack = new ItemStack(Material.EMERALD, 1);
        ItemMeta meta = itemStack.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "HITMcoin");
        meta.setCustomModelData(1);
        List<String> lore = new ArrayList<>();

        lore.add("This is the official currency of the hitm server");
        lore.add(ChatColor.GREEN + "Value: 1HIT (HITMcoin)");

        meta.setLore(lore);
        itemStack.setItemMeta(meta);

        HITMcoin = itemStack;
    }

}
