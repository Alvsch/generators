package me.alvsch.generators.tabcompletion;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GenSlotsTabCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completion = new ArrayList<>();

        if(args.length == 1) {
            completion.add("set");
            completion.add("add");
            completion.add("remove");
            completion.add("amount");
        }
        if(args.length == 2) {
            for(Player player : Bukkit.getOnlinePlayers()) {
                completion.add(player.getName());

            }
        }

        return completion;
    }
}
