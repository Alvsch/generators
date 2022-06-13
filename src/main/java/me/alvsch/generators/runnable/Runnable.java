package me.alvsch.generators.runnable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.alvsch.generators.Main;
import me.alvsch.generators.item.ItemHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class Runnable {

    Main plugin = Main.getPlugin();

    public void startGens() {
        new BukkitRunnable() {

            @Override
            public void run() {
                for(Map.Entry<String, JsonElement> entry : plugin.data.get("gens").getAsJsonObject().entrySet()) {
                    String[] xyz = entry.getKey().split(":");
                    World world = Bukkit.getWorld(plugin.getConfig().getString("gen-world"));
                    Location loc = new Location(world, Double.parseDouble(xyz[0]), Double.parseDouble(xyz[1]), Double.parseDouble(xyz[2]));
                    Block block = world.getBlockAt(loc);

                    world.dropItem(loc.add(0,1,0), ItemHandler.genDrop.get(ItemHandler.gensList.get(block.getType())));
                }


            }

        }.runTaskTimer(plugin, 1, 10*20);
    }

    public void startCollector() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for(Map.Entry<String, JsonElement> entry : plugin.data.get("gens").getAsJsonObject().entrySet()) {
                    String[] xyz = entry.getKey().split(":");
                    World world = Bukkit.getWorld(plugin.getConfig().getString("gen-world"));
                    Location loc = new Location(world, Double.parseDouble(xyz[0]), Double.parseDouble(xyz[1]), Double.parseDouble(xyz[2]));
                    Block block = world.getBlockAt(loc);
                    String uuid = entry.getValue().getAsJsonObject().get("uuid").getAsString();

                    ItemStack drop = ItemHandler.genDrop.get(ItemHandler.gensList.get(block.getType()));

                    JsonObject player_data = plugin.data.get("players").getAsJsonObject().get(uuid).getAsJsonObject();
                    player_data.get("collector").getAsJsonObject().addProperty(drop.getType().name(),
                            player_data.get("collector").getAsJsonObject().get(drop.getType().name()).getAsInt() + 1);

                }

            }


        }.runTaskTimer(plugin, 1, 10*20);
    }

}
