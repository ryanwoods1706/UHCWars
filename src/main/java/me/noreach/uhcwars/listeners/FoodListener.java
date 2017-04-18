package me.noreach.uhcwars.listeners;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.enums.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

/**
 * Created by Ryan on 18/04/2017.
 */
public class FoodListener implements Listener {

    private UHCWars uhcWars;

    public FoodListener(UHCWars uhcWars){
        this.uhcWars = uhcWars;
    }

    @EventHandler
    public void onFoodLvl(FoodLevelChangeEvent e){
        if (this.uhcWars.getStateManager().getGameState() == GameState.LOBBY){
            e.setCancelled(true);
        }
    }
}
