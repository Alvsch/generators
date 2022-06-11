package me.alvsch.generators.inventory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.alvsch.generators.Main;
import me.alvsch.generators.item.ItemHandler;
import me.alvsch.generators.item.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InventoryHandler {

    private static Main plugin = Main.getPlugin();

    public static Inventory gensInv;
    public static Inventory collectorsInv;

    public static void init() {
        gensInv = InventoryUtils.createInventory(null, 6, "&cGenerators");
        InventoryUtils.fillRows(ItemHandler.placeholder, gensInv, 1, 9);
        InventoryUtils.fillRows(ItemHandler.placeholder, gensInv, 46, 54);

        InventoryUtils.addItems(ItemHandler.gensList.values(), gensInv);

        gensInv.addItem(ItemHandler.collector);

        collectorsInv = InventoryUtils.createInventory(null, 6, "&cCollector");
        InventoryUtils.fillRows(ItemHandler.placeholder, collectorsInv, 1, 9);
        InventoryUtils.fillRows(ItemHandler.placeholder, collectorsInv, 46, 54);

    }

    public static void viewGenerators(Player player) {
        player.closeInventory();
        player.openInventory(gensInv);
    }

    public static void viewCollector(Player player) {
        JsonObject player_data = plugin.data.get("players").getAsJsonObject().get(player.getUniqueId().toString()).getAsJsonObject();

        List<ItemStack> items = new ArrayList<>();
        for(Map.Entry<String, JsonElement> elem : player_data.getAsJsonObject("").entrySet()) {
            if(elem.getKey().equals("xyz")) {
                continue;
            }
            Material type = Material.getMaterial(elem.getValue().getAsString());
            items.add(ItemHandler.genDropList.get(type));
        }


        player.closeInventory();
        player.openInventory(collectorsInv);
    }

}
