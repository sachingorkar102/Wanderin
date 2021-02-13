package me.sachin.ConfigurationUtils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.sachin.utils.ConsoleUtils;

public class ConfigManager {


    private final JavaPlugin plugin;
    private final String name;
    private File file;
    private FileConfiguration configuration;


    
    public <T extends JavaPlugin> ConfigManager(T plugin, String name) {
        this(plugin, new File(plugin.getDataFolder(), name));
    }
    public <T extends JavaPlugin> ConfigManager(T plugin, File file) {
        this.plugin = plugin;
        this.name = file.getName();
        this.file = file;
    }
    public void reloadConfig() {
        configuration = YamlConfiguration.loadConfiguration(file);
    }


    public FileConfiguration getConfig() {
        if (configuration == null) {
            reloadConfig();
        }
        return configuration;
    }


    public void saveConfig() {
        if (configuration == null || file == null) {
            return;
        }
        else {
            try {
                getConfig().save(file);
            } catch (IOException exception) {}
        }
    }


    public void saveDefaultConfig() {
         if (!file.exists()) {
              new ConsoleUtils().sendConsoleMessage('&', file.getName()+" could not found,generating one");
              plugin.saveResource(name, false);
         }
    }

    
    public void createNewFile(boolean replaceExisting) {
        try {
            if (file.exists() && replaceExisting) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    
}
