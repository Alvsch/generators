package me.alvsch.generators.commands.economy;

import me.alvsch.generators.Main;
import me.alvsch.generators.item.ItemHandler;
import me.alvsch.generators.utils.Utils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SellCommand implements CommandExecutor {

    Economy econ = Main.getEcon();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage(Utils.color("&cConsole Cant Execute This Command"));
            return true;
        }
        Player player = (Player) sender;
        Inventory inv = player.getInventory();

        long amount = 0;
        for(ItemStack item : inv.getContents()) {
            if(item == null) {
                continue;
            }
            ItemStack item_copy = item.clone();
            item_copy.setAmount(1);
            if(ItemHandler.genDrop.containsValue(item_copy)) {
                amount += Long.parseLong(item.getItemMeta().getLore().get(0).split(": ")[1].replaceAll("_", "")) * Long.parseLong(String.valueOf(item.getAmount()));
                item.setAmount(0);
            }

        }

        player.sendMessage(Utils.color("&aSold your inventory for " + amount + " money"));
        econ.depositPlayer(player, amount);

        return true;
    }
}
