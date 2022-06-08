package me.alvsch.generators.events;

import com.google.gson.JsonObject;
import me.alvsch.generators.Main;
import me.alvsch.generators.item.ItemHandler;
import me.alvsch.generators.utils.JsonUtils;
import me.alvsch.generators.utils.Utils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;
import java.util.Map;

public class PlayerEvents implements Listener {

    Main plugin = Main.getPlugin();
    Economy econ = Main.getEcon();

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {

        if(!JsonUtils.exists(plugin.data.get("players").getAsJsonObject(), event.getPlayer().getUniqueId().toString())) {
            JsonObject object = new JsonObject();
            object.addProperty("coins", 0);

            JsonUtils.add(plugin.data.get("players").getAsJsonObject(), event.getPlayer().getUniqueId().toString(), object);
        }

    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {

        ItemStack item = event.getItemInHand().clone();
        item.setAmount(1);
        if(ItemHandler.gensList.containsValue(item)) {
            Block block = event.getBlock();
            String xyz = block.getLocation().getBlockX() + "-" + block.getLocation().getBlockY() + "-" + block.getLocation().getBlockZ();

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("uuid", event.getPlayer().getUniqueId().toString());

            plugin.data.get("gens").getAsJsonObject().add(xyz, jsonObject);
            event.getPlayer().sendMessage(Utils.color("&aSuccessfully Placed Down A Generator"));
        }

    }

    @EventHandler
    public void playerInteract(PlayerInteractEvent event){

        Block block = event.getClickedBlock();
        ItemStack item = event.getItem();
        Player player = event.getPlayer();
        Inventory inv = player.getInventory();

        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

            if(player.isSneaking()) {
                String xyz = block.getLocation().getBlockX()
                        + "-" + block.getLocation().getBlockY()
                        + "-" + block.getLocation().getBlockZ();

                if(JsonUtils.exists(plugin.data.get("gens").getAsJsonObject(), xyz)) {
                    int i = ItemHandler.gensListIndex.get(block.getType());
                    Material next_material;
                    try {
                        next_material = (Material) ItemHandler.gensList.keySet().toArray()[i + 1];
                    } catch (ArrayIndexOutOfBoundsException e) {
                        player.sendMessage(Utils.color("&cThere is no next level!"));
                        return;
                    }

                    if(JsonUtils.getProperty(plugin.data.get("gens").getAsJsonObject().get(xyz).getAsJsonObject(),
                            "uuid").getAsString().equals(player.getUniqueId().toString())) {

                        if(econ.getBalance(player) > 0) {
                            block.setType(next_material);
                        } else {
                            player.sendMessage(Utils.color("&cNot Enough Money!"));
                        }
                    }

                }

            }

            if(event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                if(item == null) {
                    return;
                }

                if(item.getType().equals(Material.PAPER) && item.getItemMeta().getDisplayName().equals("§aMoney Note")) {
                    String amount = item.getItemMeta().getLore().get(0);
                    double int_amount;
                    try {
                        int_amount = Double.parseDouble(amount.split(": ")[1]);
                    } catch (NumberFormatException e) {
                        return;
                    }
                    item.setAmount(item.getAmount() - 1);
                    player.sendMessage(Utils.color("Redeemed " + int_amount + " Coins"));
                    JsonObject player_data = JsonUtils.getProperty(plugin.data.get("players").getAsJsonObject(), player.getUniqueId().toString()).getAsJsonObject();
                    int coins = player_data.get("coins").getAsInt();

                    player_data.addProperty("coins", coins + int_amount);

                }
            }
        }

        if(event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            String xyz = block.getLocation().getBlockX()
                    + "-" + block.getLocation().getBlockY()
                    + "-" + block.getLocation().getBlockZ();

            if(JsonUtils.exists(plugin.data.get("gens").getAsJsonObject(), xyz)) {
                String uuid = JsonUtils.getProperty(plugin.data.get("gens").getAsJsonObject(), xyz).getAsJsonObject().get("uuid").getAsString();
                if(!uuid.equals(player.getUniqueId().toString())) {
                    event.setCancelled(true);
                    return;
                }
                plugin.data.get("gens").getAsJsonObject().remove(xyz);
                event.getPlayer().getInventory().addItem(ItemHandler.gensList.get(block.getType()));
                block.setType(Material.AIR);
            }

        }
    }

}
