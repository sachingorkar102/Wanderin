package me.sachin.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.sachin.ConfigurationUtils.MapsSection;
import me.sachin.ConfigurationUtils.TraderConfigSection;
import net.md_5.bungee.api.ChatColor;

public class giveMapCommand extends SubCommand {

    @Override
    public String getName() {
        return "give";
    }

    @Override
    public String getDescription() {
        return "&6gives player a explorer map";
    }

    @Override
    public String getUsage() {
        return "&e/wi give &f<map name> <player name>";
    }

    @Override
    public String getPermission() {
        return "wanderin.command.give";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        List<String> listArgs = Arrays.asList(args);
        Player p = (Player) sender;
        Entity e = (Entity) sender;
        
        if(listArgs.size() > 1){
            if(MapsSection.getLoadedMapsList().contains(listArgs.get(1))){
                if(listArgs.size() == 3){
                    try {
                        Player player = Bukkit.getPlayer(listArgs.get(2));
                        if(player.isOnline()){
                            ItemStack newMap = TraderConfigSection.mapStack((Entity) player, listArgs.get(1));
                            player.getInventory().addItem(newMap);
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6"+player.getName()+" &ehas given map named &6"+listArgs.get(1)));
                        }
                        
                    } catch (Exception ex) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eCould not find player with name &6"+listArgs.get(3)));
                    }
                }else{
                    ItemStack map = TraderConfigSection.mapStack(e, listArgs.get(1));
                    p.getInventory().addItem(map);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eGave map named &6"+listArgs.get(1)));
                }
            }
            else{
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eCould not find map named &6"+listArgs.get(1)));
            }
        }
    }



}