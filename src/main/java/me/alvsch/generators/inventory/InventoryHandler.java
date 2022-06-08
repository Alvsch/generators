package me.alvsch.generators.inventory;

import me.alvsch.generators.item.ItemHandler;
import me.alvsch.generators.item.ItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class InventoryHandler {

    public static Inventory gensInv;

    public static void init() {
        gensInv = InventoryUtils.createInventory(null, 6, "&cGenerators");
        InventoryUtils.fillRows(ItemHandler.placeholder, gensInv, 1, 9);
        InventoryUtils.fillRows(ItemHandler.placeholder, gensInv, 44, 54);

        InventoryUtils.addItems(ItemHandler.gensList.values(), gensInv);

        gensInv.addItem(ItemHandler.collector);

    }

    public static void viewGenerators(Player player) {
        player.closeInventory();
        player.openInventory(gensInv);
    }

}
