package me.sachin.commands;

import org.bukkit.command.CommandSender;

public abstract class SubCommand {

    public abstract String getName();
    
    public abstract String getDescription();

    public abstract String getUsage();

    public abstract void perform(CommandSender sender,String[] args);

    public abstract String getPermission();

    
}
