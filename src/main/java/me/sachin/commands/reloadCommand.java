package me.sachin.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.sachin.wanderin;
import net.md_5.bungee.api.ChatColor;

public class reloadCommand extends SubCommand {

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {    
        return "&6Reloads config.yml for wanderin";
    }

    @Override
    public String getUsage() {
        return "&e/wi reload";
    }

    @Override
    public String getPermission() {
        
        return "wanderin.command.reload";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        wanderin.reloadConfigs();
        Player p = (Player) sender;
        p.sendMessage(ChatColor.YELLOW+"Wanderin "+ChatColor.GREEN+"has been reloaded");
    }
}
