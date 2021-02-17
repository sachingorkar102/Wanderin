package me.sachin.listners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;

import me.sachin.ConfigurationUtils.TraderConfigSection;
import me.sachin.ConfigurationUtils.TraderOptionConfig;

public class onTraderSpawns implements Listener{
    List<String> Lore = new ArrayList<>();

    @EventHandler
    public void ontraderspawn(EntitySpawnEvent e) throws IndexOutOfBoundsException{
        EntityType type = e.getEntityType();
        if(type != EntityType.WANDERING_TRADER) return;
        List<String> disabledWorlds = TraderOptionConfig.getdisabledWorlds();
        if(disabledWorlds != null){
            if(disabledWorlds.contains(e.getEntity().getWorld().getName())){
                e.setCancelled(true);
            }
        }
        WanderingTrader trader = (WanderingTrader) e.getEntity();
        Merchant merchant = (Merchant) trader;

        List<MerchantRecipe> r = TraderConfigSection.getRecipeList(e.getEntity());
        merchant.setRecipes(r);
    }




}