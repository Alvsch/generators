package me.alvsch.generators.commands.economy;

import com.google.gson.JsonObject;
import me.alvsch.generators.Main;
import me.alvsch.generators.item.ItemHandler;
import me.alvsch.generators.utils.JsonUtils;
import me.alvsch.generators.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoinsCommand implements CommandExecutor {

    Main plugin = Main.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage(Utils.color("&cConsole Cant Execute This Command"));
            return true;
        }
        Player player = (Player) sender;


        if(!(args.length >= 1)) {
            return false;
        }

        if(args[0].equalsIgnoreCase("withdraw")) {

            if (!(args.length >= 2)) {
                return false;
            }

            double amount = 0;
            try {
                amount  = Math.abs(Double.parseDouble(args[1]));
            } catch (NumberFormatException e) {
                return false;
            }

            JsonObject player_data = JsonUtils.getProperty(plugin.data.get("players").getAsJsonObject(), player.getUniqueId().toString()).getAsJsonObject();

            int coins = player_data.get("coins").getAsInt();
            if(coins < amount) {
                player.sendMessage(Utils.color("&cYou do not have that many coins"));
                return true;
            }
            player_data.addProperty("coins", coins - amount);
            ItemHandler.giveMoneyNote(player, amount);
            return true;
        }
        if(args[0].equalsIgnoreCase("give")) {
            if(!player.hasPermission("generators.admin")) {
                return true;
            }

            if(!(args.length >= 2)) {
                return false;
            }
            JsonObject player_data = JsonUtils.getProperty(plugin.data.get("players").getAsJsonObject(), player.getUniqueId().toString()).getAsJsonObject();
            int amount_before = player_data.get("coins").getAsInt();

            player_data.addProperty("coins", amount_before + Math.abs(Integer.parseInt(args[1])));

        }
        if(args[0].equalsIgnoreCase("remove")) {
            if(!player.hasPermission("generators.admin")) {
                return true;
            }

            if(!(args.length >= 2)) {
                return false;
            }
            JsonObject player_data = JsonUtils.getProperty(plugin.data.get("players").getAsJsonObject(), player.getUniqueId().toString()).getAsJsonObject();
            int amount_before = player_data.get("coins").getAsInt();

            player_data.addProperty("coins", Math.abs(amount_before - Math.abs(Integer.parseInt(args[1]))));

        }


        if (args[0].equalsIgnoreCase("balance") || args[0].equalsIgnoreCase("bal")) {
            JsonObject player_data = JsonUtils.getProperty(plugin.data.get("players").getAsJsonObject(), player.getUniqueId().toString()).getAsJsonObject();

            player.sendMessage("§6You currently have " + player_data.get("coins").getAsInt() + " §6coins");
        }


        return true;
    }
}
