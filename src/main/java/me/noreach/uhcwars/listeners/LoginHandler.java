package me.noreach.uhcwars.listeners;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.player.GamePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Ryan on 17/04/2017.
 */
public class LoginHandler implements Listener {

    private UHCWars uhcWars;

    public LoginHandler(UHCWars uhcWars){
        this.uhcWars = uhcWars;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player pl = e.getPlayer();
        pl.teleport(this.uhcWars.getReferences().getSpawnWorld().getSpawnLocation());
        this.uhcWars.getInvent().giveJoinInventory(pl);
    }

    @EventHandler
    public void onAsync(AsyncPlayerPreLoginEvent e){
        GamePlayer gamePlayer = new GamePlayer(e.getUniqueId(), uhcWars);
        gamePlayer.getInformation();
        this.uhcWars.getPlayerManager().getPlayerData().put(e.getUniqueId(), gamePlayer);
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent e){
        Player pl = e.getPlayer();
        GamePlayer gamePlayer = this.uhcWars.getPlayerManager().getPlayerData().get(pl.getUniqueId());
        gamePlayer.saveInformation();
        this.uhcWars.getPlayerManager().getPlayerData().remove(pl.getUniqueId());
    }
}
