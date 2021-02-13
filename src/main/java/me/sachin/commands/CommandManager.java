package me.sachin.commands;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.sachin.ConfigurationUtils.ConfigSection;
import me.sachin.utils.ConsoleUtils;

public class CommandManager implements CommandExecutor {

    private ArrayList<SubCommand> subcommands = new ArrayList<>();
    private static ConsoleUtils console = new ConsoleUtils();

    public CommandManager(){
        subcommands.add(new giveMapCommand());
        subcommands.add(new reloadCommand());
        subcommands.add(new helpCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            console.sendConsoleMessage('&', "&cRequires a player to execute command");
            return true;
        }
        Player p = (Player) sender;
        if (args.length > 0){
            for (int i = 0; i < getSubcommands().size(); i++){
                if (args[0].equalsIgnoreCase(getSubcommands().get(i).getName())){
                    if(!p.hasPermission(getSubcommands().get(1).getPermission()) || !p.hasPermission("wanderin.command.*")){
                        p.sendMessage(ConfigSection.getPermissionMessage());
                    }else{
                        getSubcommands().get(i).perform(sender, args);
                    }
                }
            }
        }
        
        return true;
    }

    public ArrayList<SubCommand> getSubcommands(){
        return subcommands;
    }

}