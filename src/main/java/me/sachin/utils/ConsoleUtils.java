package me.sachin.utils;

import net.md_5.bungee.api.ChatColor;

import static org.bukkit.Bukkit.getServer;

public class ConsoleUtils {


    public void sendConsoleMessage(char sym,String Message){
        getServer().getConsoleSender()
                .sendMessage(ChatColor.translateAlternateColorCodes(sym,Message));
    }

}
