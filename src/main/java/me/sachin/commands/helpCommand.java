package me.sachin.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.sachin.utils.ColorUtils;

public class helpCommand extends SubCommand {

    @Override
    public String getName() {
        
        return "help";
    }

    @Override
    public String getDescription() {
        
        return "&6displays command list for wanderin plugin";
    }

    @Override
    public String getUsage() {
        
        return "&e/wi help";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        List<String> helplist = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        List<SubCommand> subCommandsList = new CommandManager().getSubcommands();
        helplist.add(ColorUtils.translateColorCodes("&a-------------&2==&a&lWanderin&2==&a-------------\n"));
        helplist.add("\n");
        for (SubCommand subCommand : subCommandsList) {
            helplist.add(ColorUtils.translateColorCodes(subCommand.getUsage()+" &e- "+subCommand.getDescription()));
        }
        helplist.add("\n");
        helplist.add(ColorUtils.translateColorCodes("&a--------------------------------------"));
        for (String string : helplist) {
            buffer.append(string+"\n");
        }
        p.sendMessage(buffer.toString());

    }

    @Override
    public String getPermission() {
        
        return "wanderin.command.help";
    }
    
}
