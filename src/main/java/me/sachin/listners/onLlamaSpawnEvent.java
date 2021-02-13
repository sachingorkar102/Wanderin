package me.sachin.listners;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Llama;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.LlamaInventory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.sachin.wanderin;
import me.sachin.ConfigurationUtils.LlamaOptionsSection;
import me.sachin.utils.ConsoleUtils;
import me.sachin.utils.LlamaLootGen;

public class onLlamaSpawnEvent implements Listener {
    private static ConsoleUtils console = new ConsoleUtils();
    

    @EventHandler
    public void onllamaspawn(EntitySpawnEvent e) {
        EntityType etyType = e.getEntityType();
        if (etyType == EntityType.TRADER_LLAMA) {
            
            Llama l = (Llama) e.getEntity();
            LlamaInventory inv = l.getInventory();
            List<String> disabledWorlds = LlamaOptionsSection.getdisabledWorlds();
            if(disabledWorlds != null){
                if(disabledWorlds.contains(l.getWorld().getName())){
                    e.setCancelled(true);;
                }
            }
            if (LlamaOptionsSection.getBiomeDependentCarpet()) {
                Material carpetMaterial = getBiomeCarpet(l.getLocation().getBlock().getBiome());
                if (carpetMaterial != null) {
                    ItemStack carpet = new ItemStack(carpetMaterial);
                    inv.setDecor(carpet);
                }
            }
            if (LlamaOptionsSection.getCanEquipChest() && l.isAdult()) {
                double chance = LlamaOptionsSection.getChance();
                if (Math.random() < chance) {
                    PersistentDataContainer data = e.getEntity().getPersistentDataContainer();
                    NamespacedKey key = new NamespacedKey(wanderin.getPlugin(), "notEncountered");
                    data.set(key, PersistentDataType.STRING, "true");
                    l.setCarryingChest(true);
                    l.setTamed(true);
                    Random rand = new Random();
                    List<String> lootTableList = LlamaOptionsSection.getLootTableList();
                    if (lootTableList.isEmpty() || lootTableList == null)
                        return;
                    String randomTable = lootTableList.get(rand.nextInt(lootTableList.size()));
                    List<ItemStack> items;
                    List<String> chanceList;
                    int chestSize = LlamaLootGen.getInvSize(randomTable);
                    l.setStrength(chestSize);
                    try {
                        items = LlamaLootGen.getItemlist(randomTable);
                        chanceList = LlamaLootGen.getAmountlist().get(randomTable);
                    } catch (Exception ex) {
                        console.sendConsoleMessage('&', "&cerror loading lootTable for: &e"+randomTable);
                        return;
                    }
                    if(items.isEmpty() || items == null) return;
                    for (int a = 2; a <l.getInventory().getSize(); a++) {
                        int randomIndex = rand.nextInt(items.size());
                        int maxItem = Integer.parseInt(chanceList.get(randomIndex));
                        ItemStack mainItem = items.get(randomIndex);
                        if (l.getInventory().contains(mainItem, maxItem)) {
                            continue;
                        }    
                        else if(Math.random() < 0.5){
                            l.getInventory().setItem(a, null);
                            continue;
                        }else {
                            l.getInventory().setItem(a, mainItem);
                        }
                    }
                }
            }
        }
    }


    public Material getBiomeCarpet(Biome biome){
        if(Arrays.asList(Biome.DESERT,Biome.DESERT_HILLS,Biome.DESERT_LAKES,Biome.BADLANDS,Biome.BADLANDS_PLATEAU).contains(biome)) return Material.ORANGE_CARPET;
        else if(Arrays.asList(Biome.SNOWY_BEACH,Biome.SNOWY_MOUNTAINS,Biome.SNOWY_TAIGA,Biome.SNOWY_TAIGA_HILLS,Biome.SNOWY_TAIGA_MOUNTAINS,Biome.SNOWY_TUNDRA).contains(biome)) return Material.WHITE_CARPET;
        else if(Arrays.asList(Biome.SAVANNA,Biome.SAVANNA_PLATEAU).contains(biome)) return Material.YELLOW_CARPET;
        else if(Arrays.asList(Biome.ICE_SPIKES).contains(biome)) return Material.CYAN_CARPET;
        else if(Arrays.asList().contains(biome)) return Material.BLUE_CARPET;
        else if(Arrays.asList(Biome.GRAVELLY_MOUNTAINS,Biome.MODIFIED_GRAVELLY_MOUNTAINS).contains(biome)) return Material.GRAY_CARPET;
        else if(Arrays.asList(Biome.SWAMP,Biome.SWAMP_HILLS).contains(biome)) return Material.BROWN_CARPET;
        else if(Arrays.asList(Biome.BIRCH_FOREST,Biome.BIRCH_FOREST_HILLS,Biome.TALL_BIRCH_HILLS,Biome.TALL_BIRCH_FOREST).contains(biome)) return Material.WHITE_CARPET;
        else if(Arrays.asList(Biome.FOREST,Biome.DARK_FOREST,Biome.DARK_FOREST_HILLS).contains(biome)) return Material.GREEN_CARPET;
        else if(Arrays.asList(Biome.JUNGLE,Biome.JUNGLE_EDGE,Biome.JUNGLE_HILLS,Biome.BAMBOO_JUNGLE,Biome.BAMBOO_JUNGLE_HILLS,Biome.MODIFIED_JUNGLE,Biome.MODIFIED_JUNGLE_EDGE).contains(biome)) return Material.GREEN_CARPET;
        else if(Arrays.asList(Biome.END_BARRENS,Biome.END_HIGHLANDS,Biome.END_MIDLANDS,Biome.THE_END,Biome.SMALL_END_ISLANDS).contains(biome)) return Material.PURPLE_CARPET;
        else if(Arrays.asList(Biome.STONE_SHORE).contains(biome)) return Material.LIGHT_GRAY_CARPET;
        else if(Arrays.asList(Biome.TAIGA,Biome.TAIGA_HILLS,Biome.TAIGA_MOUNTAINS,Biome.GIANT_TREE_TAIGA,Biome.GIANT_TREE_TAIGA_HILLS).contains(biome)) return Material.BROWN_CARPET;
        else if(Arrays.asList(Biome.PLAINS).contains(biome)) return Material.LIME_CARPET;
        else return null;
    }
    

    
    
}
