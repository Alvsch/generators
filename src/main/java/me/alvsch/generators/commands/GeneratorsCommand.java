package me.alvsch.generators.commands;

import me.alvsch.generators.inventory.InventoryHandler;
import me.alvsch.generators.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GeneratorsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage(Utils.color("&cConsole Cant Execute This Command"));
            return true;
        }
        Player player = (Player) sender;

        InventoryHandler.viewGenerators(player);

        return true;
    }
}
