package me.alvsch.generators.commands;

import com.google.gson.JsonObject;
import me.alvsch.generators.Main;
import me.alvsch.generators.utils.JsonUtils;
import me.alvsch.generators.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GenSlotsCommand implements CommandExecutor {

    Main plugin = Main.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(args.length >= 2)) {
            return false;
        }
        Player target = Bukkit.getPlayer(args[1]);
        if(target == null) {
            return true;
        }
        JsonObject player_data = plugin.data.get("players").getAsJsonObject().get(target.getUniqueId().toString()).getAsJsonObject();

        if(args[0].equalsIgnoreCase("amount")) {
            sender.sendMessage(Utils.color("&a" + target.getName() + " Max Slots is " + player_data.get("max_gens").getAsString()));
            return true;
        }

        if(args.length >= 3) {
            int amount;
            try {
                amount = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                return false;
            }

            if(args[0].equalsIgnoreCase("set")) {
                JsonUtils.addProperty(player_data, "max_gens", amount);
            }
            if(args[0].equalsIgnoreCase("add")) {
                amount += player_data.get("max_gens").getAsInt();
                JsonUtils.addProperty(player_data, "max_gens", amount);
            }
            if(args[0].equalsIgnoreCase("remove")) {
                amount -= player_data.get("max_gens").getAsInt();
                if(amount < 0) amount = 0;

                JsonUtils.addProperty(player_data, "max_gens",  amount);
            }
            sender.sendMessage(Utils.color("&a" + target.getName() + " now has " + JsonUtils.getProperty(player_data, "max_gens") + " generator slots"));

        }

        return true;
    }
}
