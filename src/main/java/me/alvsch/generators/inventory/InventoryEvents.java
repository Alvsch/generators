package me.alvsch.generators.inventory;

import me.alvsch.generators.Main;
import me.alvsch.generators.item.ItemHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryEvents implements Listener {

    Main plugin = Main.getPlugin();

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
            if(item == null) {
                return;
            }

            if(item.getType().equals(Material.ARROW)) {
                ItemMeta page_meta = event.getClickedInventory().getItem(4).getItemMeta();
                int page = Integer.parseInt(page_meta.getDisplayName());

                if(item.getItemMeta().getDisplayName().equals("§fNext Page")) {
                    InventoryHandler.viewCollector(player, page + 1);

                }
                if(item.getItemMeta().getDisplayName().equals("§fPrevious Page")) {
                    InventoryHandler.viewCollector(player, page - 1);

                }
                return;
            }

            return;
        }

        event.setCancelled(false);

    }

}
