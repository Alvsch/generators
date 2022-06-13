package me.alvsch.generators.inventory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.alvsch.generators.Main;
import me.alvsch.generators.item.ItemHandler;
import me.alvsch.generators.item.ItemUtils;
import me.alvsch.generators.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InventoryHandler {

    private static Main plugin = Main.getPlugin();

    public static Inventory gensInv;

    public static void init() {
        gensInv = InventoryUtils.createInventory(null, 6, "&cGenerators");
        InventoryUtils.fillRows(ItemHandler.placeholder, gensInv, 1, 9);
        InventoryUtils.fillRows(ItemHandler.placeholder, gensInv, 46, 54);

        InventoryUtils.addItems(ItemHandler.gensList.values(), gensInv);

        gensInv.addItem(ItemHandler.collector);

    }

    public static void viewGenerators(Player player) {
        player.closeInventory();
        player.openInventory(gensInv);
    }

    public static void viewCollector(Player player, int page) {
        Inventory inv = InventoryUtils.createInventory(null, 6, "&cCollector");
        InventoryUtils.fillRows(ItemHandler.placeholder, inv, 1, 9);
        InventoryUtils.fillRows(ItemHandler.placeholder, inv, 46, 54);

        inv.setItem(4, ItemUtils.createItem(Material.PAPER, 1, String.valueOf(page)));
        inv.setItem(45, ItemHandler.credits);
        inv.setItem(51, ItemUtils.createItem(Material.ARROW, 1, "&fNext Page"));

        if(page != 0) {
            inv.setItem(47, ItemUtils.createItem(Material.ARROW, 1, "&fPrevious Page"));
        }

        JsonObject player_data = plugin.data.get("players").getAsJsonObject().get(player.getUniqueId().toString()).getAsJsonObject();

        for(Map.Entry<String, JsonElement> elem : player_data.getAsJsonObject("collector").entrySet()) {
            Material type = Material.getMaterial(elem.getKey());
            ItemStack i = ItemHandler.genDropList.get(type);
            ItemMeta meta = i.getItemMeta();

            List<String> lore = new ArrayList<>();
            lore.add("&fAmount: " + elem.getValue().getAsString());
            lore.add("&fLeft Click Sell x1");
            lore.add("&fRight Click Sell x64");
            lore.add("&fShift Click Sell All");
            meta.setLore(lore);
            i.setItemMeta(meta);

            inv.addItem(i);
        }

        player.closeInventory();
        player.openInventory(inv);
    }

}
