package me.alvsch.generators.events;

import com.google.gson.JsonObject;
import me.alvsch.generators.Main;
import me.alvsch.generators.inventory.InventoryHandler;
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

import java.util.*;

public class PlayerEvents implements Listener {

    Main plugin = Main.getPlugin();
    Economy econ = Main.getEcon();

    private HashMap<Player, Long> cooldown = new HashMap<>();

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if(!JsonUtils.exists(plugin.data.get("players").getAsJsonObject(), event.getPlayer().getUniqueId().toString())) {
            JsonObject player_data = new JsonObject();
            player_data.addProperty("coins", 0);
            player_data.addProperty("gens", 0);
            player_data.addProperty("max_gens", 10);
            player_data.addProperty("has_collector", false);

            JsonObject collector = new JsonObject();
            for(ItemStack item : ItemHandler.genDrop.values()) {
                collector.addProperty(item.getType().name(), 0);
            }
            player_data.add("collector", collector);

            JsonUtils.add(plugin.data.get("players").getAsJsonObject(), event.getPlayer().getUniqueId().toString(), player_data);
        }

    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {

        ItemStack item = event.getItemInHand().clone();
        item.setAmount(1);
        if(item.equals(ItemHandler.collector)) {
            JsonObject player_data = plugin.data.get("players").getAsJsonObject().get(event.getPlayer().getUniqueId().toString()).getAsJsonObject();
            if(Objects.equals(player_data.get("has_collector").getAsBoolean(), true)) {
                event.getPlayer().sendMessage(Utils.color("&cYou cant have that many collectors"));
                event.setCancelled(true);
                return;
            }

            Block block = event.getBlock();
            String xyz = block.getLocation().getBlockX() +
                    ":" + block.getLocation().getBlockY() +
                    ":" + block.getLocation().getBlockZ();

            player_data.addProperty("has_collector", true);
            JsonObject object = new JsonObject();
            object.addProperty("uuid", event.getPlayer().getUniqueId().toString());

            plugin.data.get("collectors").getAsJsonObject().add(xyz, object);
            event.getPlayer().sendMessage(Utils.color("&aSuccessfully Placed Down A Collector"));

        }

        if(ItemHandler.gensList.containsValue(item)) {
            JsonObject player_data = plugin.data.get("players").getAsJsonObject().get(event.getPlayer().getUniqueId().toString()).getAsJsonObject();

            if(player_data.get("max_gens").getAsInt() <= player_data.get("gens").getAsInt()) {
                event.getPlayer().sendMessage(Utils.color("&cYou cant have that many generators"));
                event.setCancelled(true);
                return;
            }
            player_data.addProperty("gens", player_data.get("gens").getAsInt() + 1);

            Block block = event.getBlock();
            String xyz = block.getLocation().getBlockX() +
                    ":" + block.getLocation().getBlockY() +
                    ":" + block.getLocation().getBlockZ();

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("uuid", event.getPlayer().getUniqueId().toString());

            plugin.data.get("gens").getAsJsonObject().add(xyz, jsonObject);
            event.getPlayer().sendMessage(Utils.color("&aSuccessfully Placed Down A Generator"));
        }

    }

    @EventHandler
    public void playerInteract(PlayerInteractEvent event) {


        Block block = event.getClickedBlock();
        ItemStack item = event.getItem();
        Player player = event.getPlayer();

        JsonObject player_data = plugin.data.get("players").getAsJsonObject().get(player.getUniqueId().toString()).getAsJsonObject();
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

            if(block.getType().equals(ItemHandler.collector.getType())) {
                if (!JsonUtils.exists(plugin.data.get("collectors").getAsJsonObject(),
                        block.getLocation().getBlockX() +
                                ":" + block.getLocation().getBlockY() +
                                ":" + block.getLocation().getBlockZ())) {
                    return;
                }
                Inventory inv = null;
                inv = InventoryHandler.viewCollector(player, inv);

                player.closeInventory();
                player.openInventory(inv);
                return;
            }

            if(player.isSneaking()) {
                if(cooldown.containsKey(player)) {
                    if(cooldown.get(player) > System.currentTimeMillis()) {
                        cooldown.remove(player);
                        return;
                    }
                }

                String xyz = block.getLocation().getBlockX()
                        + ":" + block.getLocation().getBlockY()
                        + ":" + block.getLocation().getBlockZ();

                if(JsonUtils.exists(plugin.data.get("gens").getAsJsonObject(), xyz)) {
                    int i = ItemHandler.gensListIndex.get(block.getType());
                    Material next_material;
                    try {
                        next_material = (Material) ItemHandler.gensList.keySet().toArray()[i + 1];
                        System.out.println(next_material);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        player.sendMessage(Utils.color("&cThere is no next level!"));
                        return;
                    }

                    if(JsonUtils.getProperty(plugin.data.get("gens").getAsJsonObject().get(xyz).getAsJsonObject(),
                            "uuid").getAsString().equals(player.getUniqueId().toString())) {

                        int price = ItemHandler.gensPrices.get(ItemHandler.gensList.get(block.getType()));

                        if(econ.getBalance(player) >= price) {
                            block.setType(next_material);
                            econ.withdrawPlayer(player, price);
                            cooldown.put(player, System.currentTimeMillis() + (1*1000));
                        } else {
                            player.sendMessage(Utils.color("&cYou need $" + price + " to upgrade this generator"));
                        }
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
                int coins = player_data.get("coins").getAsInt();

                player_data.addProperty("coins", coins + int_amount);

            }
        }

        if(event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            String xyz = block.getLocation().getBlockX()
                    + ":" + block.getLocation().getBlockY()
                    + ":" + block.getLocation().getBlockZ();

            if(JsonUtils.exists(plugin.data.get("collectors").getAsJsonObject(), xyz)) {
                event.setCancelled(true);
                JsonObject object = plugin.data.get("collectors").getAsJsonObject().get(xyz).getAsJsonObject();

                if(Objects.equals(object.get("uuid").getAsString(), player.getUniqueId().toString())) {
                    player_data.addProperty("has_collector", false);
                    plugin.data.get("collectors").getAsJsonObject().remove(xyz);
                    event.getPlayer().getInventory().addItem(ItemHandler.collector);
                    block.setType(Material.AIR);

                    player.sendMessage(Utils.color("&aSuccessfully Removed A Collector"));
                }
            }

            if(JsonUtils.exists(plugin.data.get("gens").getAsJsonObject(), xyz)) {
                String uuid = JsonUtils.getProperty(plugin.data.get("gens").getAsJsonObject(), xyz).getAsJsonObject().get("uuid").getAsString();
                if(!uuid.equals(player.getUniqueId().toString())) {
                    event.setCancelled(true);
                    return;
                }
                plugin.data.get("gens").getAsJsonObject().remove(xyz);
                int amount = player_data.get("gens").getAsInt() - 1;
                if (amount < 0) amount = 0;

                player_data.addProperty("gens", amount);
                event.getPlayer().getInventory().addItem(ItemHandler.gensList.get(block.getType()));
                block.setType(Material.AIR);

                player.sendMessage(Utils.color("&aSuccessfully Removed A Generator"));
            }

        }
    }

}
