package me.sachin.listners;

import java.util.Arrays;
import java.util.List;

import org.bukkit.NamespacedKey;
import org.bukkit.StructureType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.sachin.wanderin;
import me.sachin.ConfigurationUtils.ConfigSection;
import me.sachin.ConfigurationUtils.MapsSection;

public class mapClickEvent implements Listener {
    public List<String> netherStructures = Arrays.asList("bastion_remnant","fortress");
        
    
    @EventHandler
    public void onMapClick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        ItemStack item = p.getInventory().getItemInMainHand();
        if(item == null) return;
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getHand().equals(EquipmentSlot.HAND)) {
            if(!item.hasItemMeta()) return;
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer data = meta.getPersistentDataContainer();
            NamespacedKey StructureKey = new NamespacedKey(wanderin.getPlugin(), "structureType");
            if(!data.has(StructureKey, PersistentDataType.STRING)) return;
            try {
                StructureType type = MapsSection.getType(data.get(StructureKey, PersistentDataType.STRING));
                ItemStack newMap = wanderin.getPlugin().getServer().createExplorerMap(p.getWorld(), p.getLocation(), type,1000,false);
                ItemMeta mapMeta = newMap.getItemMeta();
                mapMeta.setDisplayName(meta.getDisplayName());
                if(meta.getLore() != null){
                    mapMeta.setLore(meta.getLore());
                }
                newMap.setItemMeta(mapMeta);
                p.getInventory().setItemInMainHand(null);
                p.getInventory().setItemInMainHand(newMap);
                e.setCancelled(true);
            } catch (Exception ex) {
                p.sendMessage(ConfigSection.getInvalidDimensionMessage());
                e.setCancelled(true);
            }
        }

    }
}
