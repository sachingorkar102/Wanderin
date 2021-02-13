package me.sachin.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import me.sachin.ConfigurationUtils.MapsSection;


public class TabComplete implements TabCompleter {


    CommandManager manager = new CommandManager();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> arguments = new ArrayList<>();
        if(args.length == 1){
            manager.getSubcommands().forEach(s -> arguments.add(s.getName()));
            return arguments;
        }
        if(args.length == 2){
            if(args[0].equalsIgnoreCase("give")){
                return MapsSection.getLoadedMapsList();
            }
            
        }



        return null;
    }

    


}