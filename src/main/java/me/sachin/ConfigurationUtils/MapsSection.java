package me.sachin.ConfigurationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.StructureType;
import org.bukkit.configuration.ConfigurationSection;

import me.sachin.wanderin;
import me.sachin.utils.ColorUtils;
import me.sachin.utils.ConsoleUtils;

public class MapsSection {

    private static ConfigurationSection Maps;
    private static String Display;
    private static List<String> loadedMapsList;
    private static ConsoleUtils console = new ConsoleUtils();

    public static int Model;
    public static String Id;


    public static String getId() {
        try{
            Id = getMaps().getString("Id");
        }catch(Exception e){
            Id = "paper";
        }
    
        return Id;
    }


    public static int getModel(String name) {
        
        try {
            int model = getMaps().getConfigurationSection(name).getInt("Model");
            Model = model;
            return Model;
        } catch (Exception e) {
            return 0;
        }
        
    }

    public static StructureType getType(String name) {

        try {
            String typename = getMaps().getConfigurationSection(name).getString("Type").toLowerCase();
            Map<String,StructureType> strucMaps = StructureType.getStructureTypes();
            StructureType type = strucMaps.get(typename);
            if(type == null){
                return StructureType.VILLAGE;
            }else{
                return type;
            }
        } catch (Exception e) {
            console.sendConsoleMessage('&', "&cCould not load the Structuretype for map: &e"+name+" &c using VILLAGE as default");
            return StructureType.VILLAGE;
        }
        
    }
    public static String getDisplay(String name) {
    
        try {
            String Dis = getMaps().getConfigurationSection(name).getString("Display");
            Display = ColorUtils.translateColorCodes(Dis);
            return Display;
        } catch (Exception e) {
            console.sendConsoleMessage('&', "&cCould not load Display name for map: &e"+name);
            return name;
        }
    
    }

    public static List<String> getLore(String name) {
    
        try {
            List<String> lore = getMaps().getConfigurationSection(name).getStringList("Lore");
            List<String> l = new ArrayList<>();
            lore.forEach(s -> l.add(ColorUtils.translateColorCodes(s)));
            return l;
        } catch (Exception e) {
            return null;
        }
    
    }
    public static ConfigurationSection getMaps() {
        try {
            ConfigManager mapcfg = new ConfigManager(wanderin.getPlugin(), "maps.yml");
            Maps = mapcfg.getConfig().getConfigurationSection("Maps");
        } catch (Exception e) {
            console.sendConsoleMessage('&', "&cCould Not Load Maps from maps.yml");
        }
        return Maps;
    }
    public static List<String> getLoadedMapsList() {

        try {
            loadedMapsList = getMaps().getKeys(false).stream().collect(Collectors.toList());
            loadedMapsList.remove("Id");
            // System.out.println(loadedMapsList.toString());
            return loadedMapsList;
        } catch (Exception e) {
            return null;
        }

    }
    
}
