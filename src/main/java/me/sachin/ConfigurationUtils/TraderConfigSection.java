package me.sachin.ConfigurationUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.items.MythicItem;
import me.sachin.wanderin;
import me.sachin.utils.ConsoleUtils;

public class TraderConfigSection {

    private static ConfigurationSection traderConfig;
    private static ConfigurationSection trades;
    private static List<MerchantRecipe> recipeList = new ArrayList<>();
    private static List<String> tradeList;
    private static boolean unLoadedTrades = false;
    private static boolean keepVanillTrades;
    private static boolean isMythicItem;
    private static int Maxuses;

    private static ItemStack MythicItemStack;
    private static ConsoleUtils console = new ConsoleUtils();

    
    public static int getMaxuses(String name) {
        if(!unLoadedTrades){
            try {
                Maxuses = getTrades().getConfigurationSection(name).getInt("MaxUses");
            } catch (Exception e) {
                Maxuses = 2;
            }
        }
        return Maxuses;
    }
    

    public static boolean getisMythicItem(String name) {
        String itemName = name.replace("mmitem", "").replace("{", "").replace("}", "");
        try {
            Optional<MythicItem> itemOpt = MythicMobs.inst().getItemManager().getItem(itemName);
            isMythicItem = true;
            if(!itemOpt.isPresent()){
                isMythicItem = false;
            }
        } catch (Exception e) {
            isMythicItem = false;
        }
        return isMythicItem;
    }

    public static ItemStack getMythicItemStack(String name) {
        String itemName = name.replace("mmitem", "").replace("{", "").replace("}", "");
        Optional<MythicItem> itemOpt = MythicMobs.inst().getItemManager().getItem(itemName);
        MythicItemStack = BukkitAdapter.adapt(itemOpt.get().generateItemStack(1));
        return MythicItemStack;
    }

    
    public static boolean getVanillaTrades(){
        try {
            keepVanillTrades = getTraderConfig().getBoolean("KeepVanillaTrades");
        } catch (Exception e) {
            console.sendConsoleMessage('&', "&c Could not load boolean keepVanilla Trades for Wandering Trader Defaulting to True");
            keepVanillTrades = true;
        }
        return keepVanillTrades;
    }

    public static List<MerchantRecipe> getRecipeList(Entity entity) {
        if(!unLoadedTrades){
            WanderingTrader trader = (WanderingTrader) entity;
            Merchant merchant = (Merchant) trader;
            if(!recipeList.isEmpty()) recipeList.clear();
            List<String> tradesnumber = getTradeList();
            for (String string : tradesnumber) {
                int maxeuses = getMaxuses(string);
                MerchantRecipe recipe = new MerchantRecipe(getResult(entity,string), maxeuses);
                recipe.addIngredient(getItem1(string));
                if(getItem2(string) != null){
                    // System.out.println(getItem2(string));
                    recipe.addIngredient(getItem2(string));
                }
                recipeList.add(recipe);
            }
            int maxuses = TraderOptionConfig.getMaxTrades();
            Collections.shuffle(recipeList);
            recipeList = recipeList.subList(0, maxuses);

            if(getVanillaTrades()){
                List<MerchantRecipe> newrecipelist = Stream.of(merchant.getRecipes(),recipeList).flatMap(Collection::stream)
                .collect(Collectors.toList());
                return newrecipelist;
            }
        }
        return recipeList;
    }


    public static ItemStack getItem1(String tradeSection) {
        ItemStack Item1 = null;
        if(!unLoadedTrades){
            try {
                String name = getTrades().getConfigurationSection(tradeSection).getString("Item1");
                List<String> nameAndAmmount = Arrays.asList(name.split(" "));
                String itemName;
                int amount;
                if(nameAndAmmount.size() == 2){
                    itemName = nameAndAmmount.get(1);
                    amount = Integer.parseInt(nameAndAmmount.get(0));
                }else{
                    itemName = nameAndAmmount.get(0);
                    amount = 1;
                }
                if(getisMythicItem(itemName)){
                    return getMythicItemStack(itemName);
                }
                Item1 = new ItemStack(Material.matchMaterial(itemName),amount);
            } catch (Exception e) {
                console.sendConsoleMessage('&', "&cCould not get the Item1 for Trade Section &e"+tradeSection);
            }
        } 
        return Item1;
    }

    public static ItemStack getItem2(String tradeSection) {
        ItemStack Item2 = null;
        if(!unLoadedTrades){
            if(getTrades().getConfigurationSection(tradeSection).getKeys(false).contains("Item2")){
                try {
                    String name = getTrades().getConfigurationSection(tradeSection).getString("Item2");
                    List<String> nameAndAmmount = Arrays.asList(name.split(" "));
                String itemName;
                int amount;
                if(nameAndAmmount.size() == 2){
                    itemName = nameAndAmmount.get(1);
                    amount = Integer.parseInt(nameAndAmmount.get(0));
                }else{
                    itemName = nameAndAmmount.get(0);
                    amount = 1;
                }
                    if(getisMythicItem(itemName)){
                        return getMythicItemStack(itemName);
                    }
                    Item2 = new ItemStack(Material.matchMaterial(itemName),amount);
                } catch (Exception e) {
                    return null;
                }
            }
        }

        return Item2;
    }

    public static ItemStack getResult(Entity entity,String tradeSection) {
        ItemStack Result = null;
        if(!unLoadedTrades){
            String name = "";
            try {
                name = getTrades().getConfigurationSection(tradeSection).getString("Result");
                List<String> nameAndAmmount = Arrays.asList(name.split(" "));
                String itemName;
                int amount;
                if(nameAndAmmount.size() == 2){
                    itemName = nameAndAmmount.get(1);
                    amount = Integer.parseInt(nameAndAmmount.get(0));
                }else{
                    itemName = nameAndAmmount.get(0);
                    amount = 1;
                }

                if(getisMythicItem(name)){
                   return getMythicItemStack(name);
                }
                if(MapsSection.getLoadedMapsList().contains(name)){
                    return mapStack(entity, name);
                }
                Result = new ItemStack(Material.matchMaterial(itemName),amount);
            } catch (Exception e) {
                console.sendConsoleMessage('&', "&cCould not Load Result For Trader Section &e"+tradeSection);
                Result = new ItemStack(Material.DIRT);
            }
        }
        return Result;
    }
    public static ItemStack mapStack(Entity entity,String name){
        String Display = MapsSection.getDisplay(name);
        ItemStack map = new ItemStack(Material.matchMaterial(MapsSection.getId()));
        // ItemStack map = Bukkit.getServer().createExplorerMap(entity.getWorld(), entity.getLocation(), type,2000,false);
        ItemMeta meta = map.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        NamespacedKey StructureKey = new NamespacedKey(wanderin.getPlugin(), "structureType");
        data.set(StructureKey, PersistentDataType.STRING, name);
        meta.setDisplayName(Display);
        if(MapsSection.getLore(name) != null){
            List<String> Lore = MapsSection.getLore(name);
            meta.setLore(Lore);
        }
        int model = MapsSection.getModel(name);
        if(model != 0){
            meta.setCustomModelData(model);
        }
        map.setItemMeta(meta);
        return map;
    }

    public static ConfigurationSection getTraderConfig() {
        try {
            ConfigManager traderCfg = new ConfigManager(wanderin.getPlugin(), "trader.yml");
            traderConfig = traderCfg.getConfig().getConfigurationSection("WanderingTrader");
            unLoadedTrades = false;
            
        } catch (Exception e) {
            console.sendConsoleMessage('&', "&cCould not load Wandering Trader Config Section");
            unLoadedTrades = true;
        }
        return traderConfig;
    }
    public static ConfigurationSection getTrades() {
        if(!unLoadedTrades){
            try {
                trades = getTraderConfig().getConfigurationSection("Trades");
                unLoadedTrades = false;
            } catch (Exception e) {
                console.sendConsoleMessage('&', "&cCould Not load Trades For Wandering Trader");
                unLoadedTrades = true;
            }
        }
        return trades;
    }

    public static List<String> getTradeList() {
        if(!unLoadedTrades){
            try {
                tradeList = getTrades().getKeys(false).stream().collect(Collectors.toList());
            } catch (Exception e) {
                console.sendConsoleMessage('&', "&cCould not load trades for wandering trader");
                unLoadedTrades = true;
            }
        }
        return tradeList;
    }



    
}
