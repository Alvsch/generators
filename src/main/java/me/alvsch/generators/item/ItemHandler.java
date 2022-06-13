package me.alvsch.generators.item;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ItemHandler {

    public static ItemStack placeholder;
    public static ItemStack credits;

    public static LinkedHashMap<Material, ItemStack> gensList = new LinkedHashMap<>();
    public static HashMap<ItemStack, Integer> gensPrices = new HashMap<>();
    public static HashMap<Material, Integer> gensListIndex = new HashMap<>();

    public static LinkedHashMap<ItemStack, ItemStack> genDrop = new LinkedHashMap<>();
    public static HashMap<Material, ItemStack> genDropList = new HashMap<>();

    public static ItemStack collector;


    public static void init() {

        //region Initialize Credits
        placeholder = ItemUtils.createItem(Material.RED_STAINED_GLASS_PANE, 1, " ");
        credits = ItemUtils.createItem(Material.SUNFLOWER, 1, "&eCREDITS", "§eMade By Alvsch1");
        credits.addUnsafeEnchantment(Enchantment.LUCK, 1);
        ItemMeta credit_meta = credits.getItemMeta();
        assert credit_meta != null;
        credit_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        credits.setItemMeta(credit_meta);
        //endregion

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

        genDrop.put(gen1, ItemUtils.createItem(Material.WHEAT, 1, "&6Hay", "§fSell: 2"));
        genDrop.put(gen2, ItemUtils.createItem(Material.WHITE_DYE, 1, "&fWhite Material", "§fSell: 4"));
        genDrop.put(gen3, ItemUtils.createItem(Material.YELLOW_DYE, 1, "&eYellow Material", "§fSell: 6"));
        genDrop.put(gen4, ItemUtils.createItem(Material.LIME_DYE, 1, "&aLime Material", "§fSell: 10"));
        genDrop.put(gen5, ItemUtils.createItem(Material.GREEN_DYE, 1, "&2Green Material", "§fSell: 15"));
        genDrop.put(gen6, ItemUtils.createItem(Material.ORANGE_DYE, 1, "&fOrange Material", "§fSell: 25"));
        genDrop.put(gen7, ItemUtils.createItem(Material.LIGHT_BLUE_DYE, 1, "&9Light Blue Material", "§fSell: 40"));
        genDrop.put(gen8, ItemUtils.createItem(Material.CYAN_DYE, 1, "&3Cyan Material", "§fSell: 80"));
        genDrop.put(gen9, ItemUtils.createItem(Material.BLUE_DYE, 1, "&1Blue Material", "§fSell: 160"));
        genDrop.put(gen10, ItemUtils.createItem(Material.PURPLE_DYE, 1, "&5Purple Material", "§fSell: 320"));
        genDrop.put(gen11, ItemUtils.createItem(Material.MAGENTA_DYE, 1, "&dMagenta Material", "§fSell: 780"));
        genDrop.put(gen12, ItemUtils.createItem(Material.PINK_DYE, 1, "&dPink Material", "§fSell: 1560"));
        genDrop.put(gen13, ItemUtils.createItem(Material.RED_DYE, 1, "&cRed Material", "§fSell: 3125"));
        genDrop.put(gen14, ItemUtils.createItem(Material.LIGHT_GRAY_DYE, 1, "&7Light Gray Material", "§fSell: 6250"));
        genDrop.put(gen15, ItemUtils.createItem(Material.GRAY_DYE, 1, "&8Gray Material", "§fSell: 12500"));
        genDrop.put(gen16, ItemUtils.createItem(Material.BLACK_DYE, 1, "&0Black Material", "§fSell: 25000"));
        genDrop.put(gen17, ItemUtils.createItem(Material.BLACK_CONCRETE, 1, "&4Dark Matter", "§fSell: 50000"));

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

        int[] prices = {2500,6100,12200,24405,48825,97625,195250,390500,781000,
                        1562500,3125000,6250000,12500000,25000000,50000000,100000000, 0};
        for(int i = 0; i < gensList.size(); i++) {
            gensPrices.put((ItemStack) gensList.values().toArray()[i], prices[i]);
        }
        for(ItemStack item : genDrop.values()) {
            genDropList.put(item.getType(), item);
        }
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
