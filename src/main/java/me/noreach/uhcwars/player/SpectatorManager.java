package me.noreach.uhcwars.player;

import me.noreach.uhcwars.UHCWars;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

/**
 * Created by Ryan on 20/04/2017.
 */
public class SpectatorManager {

    private UHCWars uhcWars;
    private List<UUID> spectators = new ArrayList<>();


    public SpectatorManager(UHCWars uhcWars) {
        this.uhcWars = uhcWars;
    }


    public List<UUID> getSpectators() {
        return this.spectators;
    }

    public void addSpectator(Player player) {
        if (player.isOnline()){
            this.spectators.add(player.getUniqueId());
            player.setFlying(true);
            player.setAllowFlight(true);
            player.setGameMode(GameMode.CREATIVE);
            for (Player onlinePl : Bukkit.getServer().getOnlinePlayers()){
                onlinePl.hidePlayer(player);
            }
            player.sendMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + "You're now spectating!");
        }else{
            Bukkit.getLogger().log(Level.SEVERE, "[Spectating] Error player is not online!");
        }
    }

    public void removeSpectator(Player player){
        if (player.isOnline()){
            this.spectators.remove(player.getUniqueId());
            player.setAllowFlight(false);
            player.setFlying(false);
            player.setGameMode(GameMode.SURVIVAL);
            for (Player onlinePl: Bukkit.getServer().getOnlinePlayers()){
                onlinePl.showPlayer(player);
            }
            player.sendMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + "You're no longer spectating!");
        }else{
            Bukkit.getLogger().log(Level.SEVERE, "[Spectating] Error player is not online!");
        }
    }


}
