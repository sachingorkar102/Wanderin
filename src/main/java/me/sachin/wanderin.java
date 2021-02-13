package me.sachin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.sachin.ConfigurationUtils.MapsSection;
import me.sachin.ConfigurationUtils.TraderConfigSection;
import me.sachin.commands.CommandManager;
import me.sachin.commands.TabComplete;
import me.sachin.ConfigurationUtils.ConfigManager;
import me.sachin.ConfigurationUtils.LlamaOptionsSection;
import me.sachin.listners.mapClickEvent;
import me.sachin.listners.onLlamaSpawnEvent;
import me.sachin.listners.onTraderLlamaInventoryOpenEvent;
import me.sachin.listners.onTraderSpawns;
import me.sachin.utils.ConsoleUtils;


public final class wanderin extends JavaPlugin{


    private static wanderin plugin;
    ConsoleUtils console = new ConsoleUtils();


    public static wanderin getPlugin() {
        return plugin;
    }
    @Override
    public void onEnable() {
        plugin = this;
        this.getServer().getPluginManager().registerEvents(new mapClickEvent(), plugin);
        this.saveDefaultConfig();
        this.reloadConfig();
        reloadConfigs();
        getCommand("wanderin").setExecutor(new CommandManager());
        getServer().getPluginManager().registerEvents(new onLlamaSpawnEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new onTraderSpawns(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new onTraderLlamaInventoryOpenEvent(), this);
        getCommand("wanderin").setTabCompleter(new TabComplete());
        console.sendConsoleMessage('&', "&aPlugin Started");

    }

    public static void reloadConfigs(){

        ConsoleUtils console = new ConsoleUtils();
        wanderin.getPlugin().reloadConfig();
        ConfigManager tcfg = new ConfigManager(wanderin.getPlugin(), "trader.yml");
        ConfigManager llamacfg = new ConfigManager(wanderin.getPlugin(), "traderllama.yml");
        ConfigManager mapcfg = new ConfigManager(wanderin.getPlugin(), "maps.yml");
        mapcfg.saveDefaultConfig();
        llamacfg.saveDefaultConfig();
        tcfg.saveDefaultConfig();
        wanderin.getPlugin().saveDefaultConfig();
        mapcfg.reloadConfig();
        llamacfg.reloadConfig();
        tcfg.reloadConfig();
        wanderin.getPlugin().reloadConfig();
        console.sendConsoleMessage('&', "&b---------------&3Wanderin&b---------------");
        console.sendConsoleMessage('&', "&2Loaded &a"+TraderConfigSection.getTradeList().size()+"&2 trades for Wandering Trader");
        console.sendConsoleMessage('&', "&2Loaded &a"+MapsSection.getLoadedMapsList().size()+"&2 maps");
        console.sendConsoleMessage('&', "&2Loaded &a"+LlamaOptionsSection.getLootTableList().size()+"&2 loot tables for Trader Llama");
        console.sendConsoleMessage('&', "&2Wanderin plugin successfully reloaded");
        console.sendConsoleMessage('&', "&b--------------------------------------");

    }
}
