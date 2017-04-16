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
        if (this.uhcWars.getStateManager().getGameState() == GameState.LOBBY) {
            if (Bukkit.getOnlinePlayers().size() >= this.uhcWars.getReferences().getReqStart()) {
                if (this.second % 5 == 0) {
                    this.second -= 5;
                    Bukkit.broadcastMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + "Starting in " + this.uhcWars.getReferences().getSecondaryColor() + this.second + this.uhcWars.getReferences().getMainColor() + " seconds!");
                }
                if (this.second == 0){
                    //TODO ADD START GAME
                }
            } else {
                if (this.second != 30) {
                    Bukkit.broadcastMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + "Need more players to start the game!");
                    this.second = 30;
                    Bukkit.broadcastMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + "Reset the countdown!");
                }
            }
        }

    }
}
