package me.alvsch.generators.item;

import me.alvsch.generators.inventory.InventoryHandler;
import me.alvsch.generators.utils.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemUtils {

    public static ItemStack createItem(Material material, int amount, String display_name, String... lore) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.color(display_name));
        List<String> loreList = new ArrayList<>();
        for(String s : lore) {
            loreList.add(Utils.color(s));
        }
        meta.setLore(loreList);
        item.setItemMeta(meta);
        return item;

    }
    public static ItemStack removeLore(ItemStack item, int... lore_number) {
        if(lore_number.length < 1) {
            return null;
        }

        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for(int i : lore_number) {
            lore.remove(i);
        }

        return item;
    }
    public static void setLore(ItemStack item, int line, String newLore) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        lore.set(line, Utils.color(newLore));
    }



}
