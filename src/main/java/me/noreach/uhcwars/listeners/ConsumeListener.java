package me.noreach.uhcwars.listeners;

import me.noreach.uhcwars.UHCWars;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by Ryan on 22/04/2017.
 */
public class ConsumeListener implements Listener {

    private UHCWars uhcWars;

    public ConsumeListener(UHCWars uhcWars){
        this.uhcWars = uhcWars;
    }


    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e){
        Player pl = e.getPlayer();
        ItemStack itemStack = e.getItem();
        if (itemStack != null){
            if (itemStack.getType() == Material.GOLDEN_APPLE){
                if (itemStack.getItemMeta().getDisplayName() != null) {
                    if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Golden Head")) {
                        pl.removePotionEffect(PotionEffectType.REGENERATION);
                        pl.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1));
                    }
                }
            }
        }
    }
}
