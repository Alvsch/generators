package me.alvsch.generators.inventory;

import me.alvsch.generators.item.ItemHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryEvents implements Listener {

    @EventHandler
    public void inventoryInteract(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        Inventory inv = event.getInventory();
        String title = event.getView().getTitle();

        event.setCancelled(true);
        if(title.equals("§cGenerators")) {
            if(item == null || item == ItemHandler.placeholder) {
                return;
            }
            player.getInventory().addItem(item);
            return;
        }

        event.setCancelled(false);

    }

}
