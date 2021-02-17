package me.sachin.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.sachin.ConfigurationUtils.LlamaOptionsSection;
import me.sachin.ConfigurationUtils.TraderConfigSection;


public class LlamaLootGen {



    private static List<ItemStack> itemlist = new ArrayList<>();
    private static HashMap<String,List<String>> amountlist = new HashMap<>();
    private static ConsoleUtils console = new ConsoleUtils();


    public static HashMap<String, List<String>> getAmountlist() {
        return amountlist;
    }

    
    public static List<ItemStack> getItemlist(String loottable) {
        List<String> itemStringList = new ArrayList<>();
        List<String> amounts = new ArrayList<>();
        if(!itemlist.isEmpty()) itemlist.clear();
        try {
            itemStringList = LlamaOptionsSection.getTraderLlamaSection().getConfigurationSection("llamaChestLootTable").getConfigurationSection(loottable).getStringList("Items");
        } catch (Exception e) {
            console.sendConsoleMessage('&', "&cError loading items for loottable id: &e"+loottable);
            return null;
        }
        for (String item : itemStringList) {
            String[] itemString = item.split(" ");
            String maxAmount;
            String name;
            if(Arrays.asList(itemString).isEmpty() || Arrays.asList(itemString) == null) continue;
            if(itemString[1] == null){
                maxAmount = "1";
                name= itemString[0];
            }else{
                maxAmount = itemString[0];
                name = itemString[1];
            }
            ItemStack mainItem;
            amounts.add(maxAmount);
            if(name.contains("mmitem{}")){
                if(TraderConfigSection.getisMythicItem(name)){
                    mainItem = TraderConfigSection.getMythicItemStack(name);
                    itemlist.add(mainItem);
                    continue;
                }
            }
            try {
                mainItem = new ItemStack(Material.matchMaterial(name), 1);
            } catch (Exception e) {
                console.sendConsoleMessage('&', "&cError loading item named: &e"+name);
                mainItem = new ItemStack(Material.DIRT);
            }
            itemlist.add(mainItem);
        }
        amountlist.put(loottable, amounts);
        return itemlist;
    }
    public static int getInvSize(String loottable) {
        try {
            int inveSize = LlamaOptionsSection.getTraderLlamaSection().getConfigurationSection("llamaChestLootTable").getConfigurationSection(loottable).getInt("Size");
            switch (inveSize) {
                case 3:
                    return 1;
                case 6:
                    return 2;
                case 9:
                    return 3;
                case 12:
                    return 4;
                case 15:
                    return 5;                
                default:
                    return 5;
            }
        } catch (Exception e) {
            return 5;
        }
    }
    
}