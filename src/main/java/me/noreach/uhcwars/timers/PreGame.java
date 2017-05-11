package me.noreach.uhcwars.timers;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.enums.GameState;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Ryan on 16/04/2017.
 */
public class PreGame extends BukkitRunnable {

    private UHCWars uhcWars;
    private int second = 30;


    public PreGame(UHCWars uhcWars) {
        this.uhcWars = uhcWars;
    }

    @Override
    public void run() {
        if (this.uhcWars.getStateManager().getGameState() == GameState.LOBBY){
            if (Bukkit.getOnlinePlayers().size() < this.uhcWars.getReferences().getReqStart()){
                int difference = this.uhcWars.getReferences().getReqStart() - Bukkit.getOnlinePlayers().size();
                Bukkit.broadcastMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + "Need " + this.uhcWars.getReferences().getSecondaryColor() + difference + this.uhcWars.getReferences().getMainColor() + " more players to start the game!");
            }
            else if (Bukkit.getServer().getOnlinePlayers().size() >= this.uhcWars.getReferences().getReqStart()){
                second-=5;
                if (second > 0) {
                    Bukkit.getServer().broadcastMessage(this.uhcWars.getReferences().getPreGameStartingIn().replace("{seconds}", String.valueOf(second)));
                   // Bukkit.broadcastMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + "Starting game in: " + this.uhcWars.getReferences().getSecondaryColor() + second + this.uhcWars.getReferences().getMainColor() + " seconds!");
                }
                if (second == 0){
                    Bukkit.getServer().broadcastMessage(this.uhcWars.getReferences().getGameStarting());
                    //Bukkit.broadcastMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + "The game is now starting!");
                    this.uhcWars.getGameManager().startGame();
                }
            }
        }

    }
}
