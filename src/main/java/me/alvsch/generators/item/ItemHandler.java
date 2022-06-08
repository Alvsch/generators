package me.alvsch.generators.item;

import me.alvsch.generators.inventory.InventoryUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemHandler {

    public static ItemStack placeholder;

    public static HashMap<Material, ItemStack> gensList = new HashMap<>();

    public static ItemStack gen1;
    public static ItemStack gen2;
    public static ItemStack gen3;
    public static ItemStack gen4;
    public static ItemStack gen5;
    public static ItemStack gen6;
    public static ItemStack gen7;
    public static ItemStack gen8;
    public static ItemStack gen9;
    public static ItemStack gen10;
    public static ItemStack gen11;
    public static ItemStack gen12;
    public static ItemStack gen13;
    public static ItemStack gen14;
    public static ItemStack gen15;
    public static ItemStack gen16;
    public static ItemStack gen17;

    public static ItemStack collector;


    public static void init() {

        placeholder = ItemUtils.createItem(Material.RED_STAINED_GLASS_PANE, 1, " ");

        init_gens();

    }

    private static void init_gens() {
        gen1 = ItemUtils.createItem(Material.HAY_BLOCK, 1, "&6Hay Generator");
        gen2 = ItemUtils.createItem(Material.WHITE_GLAZED_TERRACOTTA, 1, "&fWhite Generator");
        gen3 = ItemUtils.createItem(Material.YELLOW_GLAZED_TERRACOTTA, 1, "&eYellow Generator");
        gen4 = ItemUtils.createItem(Material.LIME_GLAZED_TERRACOTTA, 1, "&aLime Generator");
        gen5 = ItemUtils.createItem(Material.GREEN_GLAZED_TERRACOTTA, 1, "&2Green Generator");
        gen6 = ItemUtils.createItem(Material.ORANGE_GLAZED_TERRACOTTA, 1, "&fOrange Generator");
        gen7 = ItemUtils.createItem(Material.LIGHT_BLUE_GLAZED_TERRACOTTA, 1, "&9Light Blue Generator");
        gen8 = ItemUtils.createItem(Material.CYAN_GLAZED_TERRACOTTA, 1, "&3Cyan Generator");
        gen9 = ItemUtils.createItem(Material.BLUE_GLAZED_TERRACOTTA, 1, "&1Blue Generator");
        gen10 = ItemUtils.createItem(Material.PURPLE_GLAZED_TERRACOTTA, 1, "&5Purple Generator");
        gen11 = ItemUtils.createItem(Material.MAGENTA_GLAZED_TERRACOTTA, 1, "&dMagenta Generator");
        gen12 = ItemUtils.createItem(Material.PINK_GLAZED_TERRACOTTA, 1, "&dPink Generator");
        gen13 = ItemUtils.createItem(Material.RED_GLAZED_TERRACOTTA, 1, "&cRed Generator");
        gen15 = ItemUtils.createItem(Material.LIGHT_GRAY_GLAZED_TERRACOTTA, 1, "&7Light Gray Generator");
        gen14 = ItemUtils.createItem(Material.GRAY_GLAZED_TERRACOTTA, 1, "&8Gray Generator");
        gen16 = ItemUtils.createItem(Material.BLACK_GLAZED_TERRACOTTA, 1, "&0Black Generator");
        gen17 = ItemUtils.createItem(Material.CRYING_OBSIDIAN, 1, "&0Obsidian Generator");

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



    }

    public static void giveMoneyNote(Player player, double amount) {
        ItemStack item = ItemUtils.createItem(Material.PAPER, 1, "&aMoney Note",
                "§dValue: " + amount,
                "§dSigner: " + player.getName());

        player.getInventory().addItem(item);

    }
}
