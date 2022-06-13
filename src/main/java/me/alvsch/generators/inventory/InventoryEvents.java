package me.alvsch.generators.inventory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.alvsch.generators.Main;
import me.alvsch.generators.item.ItemHandler;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class InventoryEvents implements Listener {

    Main plugin = Main.getPlugin();
    Economy econ = Main.getEcon();

    @EventHandler
    public void inventoryInteract(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        Inventory inv = event.getInventory();
        String title = event.getView().getTitle();

        event.setCancelled(true);
        if(title.equals("§cGenerators")) {
            if(item == null || item.equals(ItemHandler.placeholder)) {
                return;
            }
            player.getInventory().addItem(item);
            return;
        }
        if(title.equals("§cCollector")) {
            JsonObject player_data = plugin.data.get("players").getAsJsonObject().get(player.getUniqueId().toString()).getAsJsonObject();
            if(item == null) {
                return;
            }
            if(item.getType().equals(Material.EMERALD)) {
                long money = 0;
                long amount_diff = 0;
                for(Map.Entry<String, JsonElement> data : player_data.get("collector").getAsJsonObject().entrySet()) {
                    amount_diff += data.getValue().getAsInt();
                    money += Long.parseLong(ItemHandler.genDropList.get(Material.getMaterial(data.getKey())).getItemMeta().getLore().get(0).split(": ")[1]) * data.getValue().getAsInt();
                    player_data.get("collector").getAsJsonObject().addProperty(data.getKey(), 0);
                }
                player.sendMessage("§4You Sold " + amount_diff + " items for a total of $" + money);
                econ.depositPlayer(player, money);
            }

            if(ItemHandler.genDropList.containsKey(item.getType())) {
                int amount = player_data.get("collector").getAsJsonObject().get(item.getType().toString()).getAsInt();
                int amount_diff = 0;
                if(event.isLeftClick()) {
                    amount_diff = 1;
                }
                if(event.isRightClick()) {
                    amount_diff = 64;
                }
                if(event.isShiftClick()) {
                    amount_diff = amount;
                }
                if(amount - amount_diff < 0 ) {
                    amount_diff = amount;
                }
                amount -= amount_diff;
                player_data.get("collector").getAsJsonObject()
                        .addProperty(item.getType().toString(), amount);

                long money = Long.parseLong(item.getItemMeta().getLore().get(0).split(": ")[1]) * amount_diff;
                player.sendMessage("§4You Sold " + amount_diff + " items for a total of $" + money);
                econ.depositPlayer(player, money);


                inv = InventoryHandler.viewCollector(player, inv);
                return;
            }

            return;
        }

        event.setCancelled(false);

    }

}
