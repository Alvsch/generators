package me.alvsch.generators.inventory;

import me.alvsch.generators.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;

public class InventoryUtils {


    public static Inventory createInventory(InventoryHolder owner, int rows, String name) {
        return Bukkit.createInventory(owner, rows * 9, Utils.color(name));
    }
    public static void fillRows(ItemStack item, Inventory inv, int start, int end) {
        for(int i = start - 1; i < end; i++) {
            inv.setItem(i, item);
        }

    }

    public static void addItems(Collection<ItemStack> items, Inventory inv) {

        for(ItemStack i : items) {
            inv.addItem(i);
        }

    }

}
