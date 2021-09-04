/**
 * @Author: Manuel Puchner
 * @Date: 04.09.21
 */

package at.hitm.hitmplugin.listeners;

import at.hitm.hitmplugin.items.ItemManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;



public class PlayerCraftListener implements Listener {

    private static final int NUMBER_OF_PLACES = 9;
    @EventHandler
    public void onCraftingEvent(PrepareItemCraftEvent event) {

        CraftingInventory inventory = event.getInventory();
        ItemStack[] items = inventory.getMatrix();

        if(containsItem(ItemManager.HITMcoin, items)) {
            event.getInventory().setResult(new ItemStack(Material.AIR, 0));
        }
    }

    private static boolean containsItem(ItemStack toCheckFor, ItemStack[] toCompare) {
        for (int i = 0; i < NUMBER_OF_PLACES; i++) {
            if(toCompare[i].isSimilar(toCheckFor)) {
                return true;
            }
        }
        return false;
    }
}
