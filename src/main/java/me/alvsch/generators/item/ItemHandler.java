package me.alvsch.generators.item;

import me.alvsch.generators.inventory.InventoryUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ItemHandler {

    public static ItemStack placeholder;

    public static LinkedHashMap<Material, ItemStack> gensList = new LinkedHashMap<>();
    public static HashMap<Material, Integer> gensListIndex = new HashMap<>();

    public static HashMap<ItemStack, ItemStack> genDrop = new HashMap<>();

    public static ItemStack collector;


    public static void init() {

        placeholder = ItemUtils.createItem(Material.RED_STAINED_GLASS_PANE, 1, " ");

        init_gens();

    }

    private static void init_gens() {
        ItemStack gen1 = ItemUtils.createItem(Material.HAY_BLOCK, 1, "&6Hay Generator");
        ItemStack gen2 = ItemUtils.createItem(Material.WHITE_GLAZED_TERRACOTTA, 1, "&fWhite Generator");
        ItemStack gen3 = ItemUtils.createItem(Material.YELLOW_GLAZED_TERRACOTTA, 1, "&eYellow Generator");
        ItemStack gen4 = ItemUtils.createItem(Material.LIME_GLAZED_TERRACOTTA, 1, "&aLime Generator");
        ItemStack gen5 = ItemUtils.createItem(Material.GREEN_GLAZED_TERRACOTTA, 1, "&2Green Generator");
        ItemStack gen6 = ItemUtils.createItem(Material.ORANGE_GLAZED_TERRACOTTA, 1, "&fOrange Generator");
        ItemStack gen7 = ItemUtils.createItem(Material.LIGHT_BLUE_GLAZED_TERRACOTTA, 1, "&9Light Blue Generator");
        ItemStack gen8 = ItemUtils.createItem(Material.CYAN_GLAZED_TERRACOTTA, 1, "&3Cyan Generator");
        ItemStack gen9 = ItemUtils.createItem(Material.BLUE_GLAZED_TERRACOTTA, 1, "&1Blue Generator");
        ItemStack gen10 = ItemUtils.createItem(Material.PURPLE_GLAZED_TERRACOTTA, 1, "&5Purple Generator");
        ItemStack gen11 = ItemUtils.createItem(Material.MAGENTA_GLAZED_TERRACOTTA, 1, "&dMagenta Generator");
        ItemStack gen12 = ItemUtils.createItem(Material.PINK_GLAZED_TERRACOTTA, 1, "&dPink Generator");
        ItemStack gen13 = ItemUtils.createItem(Material.RED_GLAZED_TERRACOTTA, 1, "&cRed Generator");
        ItemStack gen15 = ItemUtils.createItem(Material.LIGHT_GRAY_GLAZED_TERRACOTTA, 1, "&7Light Gray Generator");
        ItemStack gen14 = ItemUtils.createItem(Material.GRAY_GLAZED_TERRACOTTA, 1, "&8Gray Generator");
        ItemStack gen16 = ItemUtils.createItem(Material.BLACK_GLAZED_TERRACOTTA, 1, "&0Black Generator");
        ItemStack gen17 = ItemUtils.createItem(Material.CRYING_OBSIDIAN, 1, "&0Obsidian Generator");

        collector = ItemUtils.createItem(Material.LODESTONE, 1, "&aCollector");

        gensList.put(gen1.getType(), gen1);
        gensList.put(gen2.getType(), gen2);
        gensList.put(gen3.getType(), gen3);
        gensList.put(gen4.getType(), gen4);
        gensList.put(gen5.getType(), gen5);
        gensList.put(gen6.getType(), gen6);
        gensList.put(gen7.getType(), gen7);
        gensList.put(gen8.getType(), gen8);
        gensList.put(gen9.getType(), gen9);
        gensList.put(gen10.getType(), gen10);
        gensList.put(gen11.getType(), gen11);
        gensList.put(gen12.getType(), gen12);
        gensList.put(gen13.getType(), gen13);
        gensList.put(gen14.getType(), gen14);
        gensList.put(gen15.getType(), gen15);
        gensList.put(gen16.getType(), gen16);
        gensList.put(gen17.getType(), gen17);

        genDrop.put(gen1, ItemUtils.createItem(Material.WHEAT, 1, "&6Hay"));
        genDrop.put(gen2, ItemUtils.createItem(Material.WHEAT, 1, "&6Hay"));
        genDrop.put(gen3, ItemUtils.createItem(Material.WHEAT, 1, "&6Hay"));
        genDrop.put(gen4, ItemUtils.createItem(Material.WHEAT, 1, "&6Hay"));
        genDrop.put(gen5, ItemUtils.createItem(Material.WHEAT, 1, "&6Hay"));
        genDrop.put(gen6, ItemUtils.createItem(Material.WHEAT, 1, "&6Hay"));
        genDrop.put(gen7, ItemUtils.createItem(Material.WHEAT, 1, "&6Hay"));
        genDrop.put(gen8, ItemUtils.createItem(Material.WHEAT, 1, "&6Hay"));
        genDrop.put(gen9, ItemUtils.createItem(Material.WHEAT, 1, "&6Hay"));
        genDrop.put(gen10, ItemUtils.createItem(Material.WHEAT, 1, "&6Hay"));
        genDrop.put(gen11, ItemUtils.createItem(Material.WHEAT, 1, "&6Hay"));
        genDrop.put(gen12, ItemUtils.createItem(Material.WHEAT, 1, "&6Hay"));
        genDrop.put(gen13, ItemUtils.createItem(Material.WHEAT, 1, "&6Hay"));
        genDrop.put(gen14, ItemUtils.createItem(Material.WHEAT, 1, "&6Hay"));
        genDrop.put(gen15, ItemUtils.createItem(Material.WHEAT, 1, "&6Hay"));
        genDrop.put(gen16, ItemUtils.createItem(Material.WHEAT, 1, "&6Hay"));
        genDrop.put(gen17, ItemUtils.createItem(Material.WHEAT, 1, "&6Hay"));

        int i = 0;
        for(Material material : gensList.keySet()) {
            gensListIndex.put(material, i);
            i += 1;

        }



    }

    public static void giveMoneyNote(Player player, double amount) {
        ItemStack item = ItemUtils.createItem(Material.PAPER, 1, "&aMoney Note",
                "§dValue: " + amount,
                "§dSigner: " + player.getName());

        player.getInventory().addItem(item);

    }
}
