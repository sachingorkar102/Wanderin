package me.sachin.listners;

import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TraderLlama;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.LlamaInventory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import me.sachin.wanderin;
import me.sachin.ConfigurationUtils.LlamaOptionsSection;

public class onTraderLlamaInventoryOpenEvent implements Listener{

    private static List<String> disabledWorlds = LlamaOptionsSection.getdisabledWorlds();

    @EventHandler
    public void onLlamaInventoryOpen(InventoryOpenEvent e){
        InventoryHolder holder = e.getInventory().getHolder();
         
        if(!(e.getInventory() instanceof LlamaInventory)) return;
        Entity entity = (Entity) holder;
        PersistentDataContainer data = entity.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(wanderin.getPlugin(), "notEncountered");
        
        if (entity.getType() == EntityType.TRADER_LLAMA 
        && LlamaOptionsSection.CanLlamaAttack() 
        && LlamaOptionsSection.getCanEquipChest() && data.has(key, PersistentDataType.STRING)) {
            if(!Boolean.parseBoolean(data.get(key, PersistentDataType.STRING))){
                return;
            }
            if(disabledWorlds != null){
                if(disabledWorlds.contains(entity.getWorld().getName())){
                    return;
                }
            }
            TraderLlama llama = (TraderLlama) entity;
            if (e.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                new BukkitRunnable(){
                    @Override
                    public void run(){
                        llama.setTamed(false);
                        llama.setTarget(e.getPlayer());
                        e.getPlayer().closeInventory();
                    }
                }.runTaskLater(wanderin.getPlugin(), 60l);

                new BukkitRunnable(){
                    @Override
                    public void run(){
                        if(!llama.isDead()){
                            llama.setTarget(null);
                            data.set(key, PersistentDataType.STRING, "false");
                        }
                    }
                }.runTaskLater(wanderin.getPlugin(), 1000l);
            }
        }
    }


    
    
}