package me.sachin.ConfigurationUtils;

import java.util.List;

import me.sachin.wanderin;

public class TraderOptionConfig {

    private static List<String> disabledWorlds;
    private static int maxTrades;
    
    public static List<String> getdisabledWorlds() {
        try {
            ConfigManager traderCfg = new ConfigManager(wanderin.getPlugin(), "trader.yml");
            disabledWorlds = traderCfg.getConfig().getConfigurationSection("WanderingTrader").getStringList("disabledWorlds");
            if(disabledWorlds.isEmpty()){
                return null;
            }else{
                return disabledWorlds;
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public static int getMaxTrades() {
        int totalTrades = TraderConfigSection.getTradeList().size();
        try {
            maxTrades = TraderConfigSection.getTraderConfig().getInt("MaxTrades");
            if(maxTrades > totalTrades){
                return totalTrades;
            }
        } catch (Exception e) {
            return totalTrades;
        }
        return maxTrades;
    }

    
}
