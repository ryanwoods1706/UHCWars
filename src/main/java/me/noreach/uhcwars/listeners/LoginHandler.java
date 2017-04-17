package me.noreach.uhcwars.listeners;

import me.noreach.uhcwars.UHCWars;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

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
        World world = this.uhcWars.getReferences().getSpawnWorld();
        pl.teleport(world.getSpawnLocation());
    }
}
