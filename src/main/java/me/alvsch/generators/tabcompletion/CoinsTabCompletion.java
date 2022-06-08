package me.alvsch.generators.tabcompletion;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class CoinsTabCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> complete = new ArrayList<>();

        if(args.length == 1) {
            complete.add("withdraw");
            complete.add("balance");
            complete.add("bal");
            if(sender.hasPermission("generators.admin")) {
                complete.add("give");
                complete.add("remove");


            }

        }
        if(args.length == 2) {

            if(args[0].equalsIgnoreCase("withdraw")) {
                complete.add("1");
                complete.add("100");
                complete.add("1000");
            }
        }

        return complete;
    }
}
