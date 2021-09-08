/**
 * @Author: Manuel Puchner
 * @Date: 04.09.21
 */

package at.hitm.hitmplugin.items;


import at.hitm.hitmplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {
    public static ItemStack HITMcoin;
    public static Boomerang boomerang = new Boomerang();
    public static ExplosiveBow explosiveBow = new ExplosiveBow();
    public static TeleportSword teleportSword = new TeleportSword();
    public static Hyperion hyperion = new Hyperion();

    public static void init() {
        createHITMcoin();

        NamespacedKey explosivebow = new NamespacedKey(Main.getInstance(), "explosive_bow");
        ShapedRecipe explosivebowrecipe = new ShapedRecipe(explosivebow, explosiveBow.bow);

        explosivebowrecipe.shape("DGD", "GBG", "DGD");

        explosivebowrecipe.setIngredient('G', Material.GUNPOWDER);
        explosivebowrecipe.setIngredient('B', Material.BOW);
        explosivebowrecipe.setIngredient('D', Material.DIAMOND);

        Bukkit.addRecipe(explosivebowrecipe);

        NamespacedKey boomerang = new NamespacedKey(Main.getInstance(), "boomerang");
        ShapedRecipe boomerangrecipe = new ShapedRecipe(boomerang, ItemManager.boomerang.boomerang);

        boomerangrecipe.shape("BBB", "BSB", "BBB");

        boomerangrecipe.setIngredient('S', Material.NETHERITE_SWORD);
        boomerangrecipe.setIngredient('B', Material.BONE_BLOCK);

        Bukkit.addRecipe(boomerangrecipe);

        NamespacedKey teleportsword = new NamespacedKey(Main.getInstance(), "teleport_sword");
        ShapedRecipe teleportswordrecipe = new ShapedRecipe(teleportsword, ItemManager.teleportSword.sword);

        teleportswordrecipe.shape("EEE", "ESE", "EEE");

        teleportswordrecipe.setIngredient('E', Material.ENDER_PEARL);
        teleportswordrecipe.setIngredient('S', Material.DIAMOND_SWORD);

        Bukkit.addRecipe(teleportswordrecipe);
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