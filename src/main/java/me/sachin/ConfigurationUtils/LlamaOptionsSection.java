package me.sachin.ConfigurationUtils;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.configuration.ConfigurationSection;

import me.sachin.wanderin;
import me.sachin.utils.ConsoleUtils;


public class LlamaOptionsSection {

    private static List<String> disabledWorlds;
    private static boolean biomeDependentCarpet;
    private static ConfigurationSection traderLlamaSection;
    private static List<String> lootTableList;
    private static double chance; 
    private static boolean equipChest;
    private static ConsoleUtils console = new ConsoleUtils();

    public static boolean CanLlamaAttack() {
        try {
            return getTraderLlamaSection().getBoolean("canAttack");
        } catch (Exception e) {
            return false;
        }
    }

    public static double getChance() {
        try {
            chance = getTraderLlamaSection().getDouble("Chance");
        } catch (Exception e) {
            console.sendConsoleMessage('&',"error loading chance");
            chance = 0.5;
        }
        return chance;
    }

    public static boolean getCanEquipChest() {
        try {
            equipChest = getTraderLlamaSection().getBoolean("equipChest");
        } catch (Exception e) {
            equipChest = true;
            console.sendConsoleMessage('&',"error loading chest");
        }
        return equipChest;
    }
    public static List<String> getLootTableList() {
        try {
            lootTableList = getTraderLlamaSection().getConfigurationSection("llamaChestLootTable").getKeys(false).stream().collect(Collectors.toList());
        } catch (Exception e) {
            lootTableList = null;
            console.sendConsoleMessage('&',"error loading loottablelist");
        }
        return lootTableList;
    }
    public static List<String> getdisabledWorlds() {
        try {
            disabledWorlds = getTraderLlamaSection().getStringList("disabledWorlds");
            if(disabledWorlds.isEmpty()){
                disabledWorlds = null;
            }
        } catch (Exception e) {disabledWorlds = null;}
        return disabledWorlds;
    }



    public static boolean getBiomeDependentCarpet() {
        try {
            biomeDependentCarpet = getTraderLlamaSection().getBoolean("biomeDependentCarpets");
        } catch (Exception e) {
            biomeDependentCarpet = true;
        }
        return biomeDependentCarpet;
    }

    
    public static ConfigurationSection getTraderLlamaSection() {
        try {
            ConfigManager config = new ConfigManager(wanderin.getPlugin(), "traderllama.yml");
            traderLlamaSection = config.getConfig().getConfigurationSection("TraderLlama");
            return traderLlamaSection;
        } catch (Exception e) {
            console.sendConsoleMessage('&', "&cCould not load trader llama config section in traderllama.yml");
            return null;
        }
    }


    
    
}
