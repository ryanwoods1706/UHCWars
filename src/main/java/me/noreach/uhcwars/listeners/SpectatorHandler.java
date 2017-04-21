package me.noreach.uhcwars.listeners;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.enums.GameState;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

/**
 * Created by Ryan on 21/04/2017.
 */
public class SpectatorHandler implements Listener {

    private UHCWars uhcWars;

    public SpectatorHandler(UHCWars uhcWars){
        this.uhcWars = uhcWars;
    }


    @EventHandler
    public void onDmg(EntityDamageEvent e){
        if (e.getEntityType() == EntityType.PLAYER){
            Player pl = (Player) e.getEntity();
            if (this.uhcWars.getStateManager().getGameState() == GameState.INGAME){
                if (this.uhcWars.getSpectatorManager().getSpectators().contains(pl.getUniqueId())){
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onEDmgByE(EntityDamageByEntityEvent e){
        if (e.getDamager() instanceof Player){
            Player pl = (Player) e.getDamager();
            if (this.uhcWars.getStateManager().getGameState() == GameState.INGAME){
                if (this.uhcWars.getSpectatorManager().getSpectators().contains(pl.getUniqueId())){
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        Player pl = e.getPlayer();
        if (this.uhcWars.getSpectatorManager().getSpectators().contains(pl.getUniqueId())){
            e.setCancelled(true);
        }
        if (this.uhcWars.getStateManager().getGameState() == GameState.LOBBY){
            e.setCancelled(true);
        }
    }

}
