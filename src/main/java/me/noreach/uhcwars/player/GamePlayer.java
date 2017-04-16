package me.noreach.uhcwars.player;

import me.noreach.uhcwars.UHCWars;
import org.bukkit.Bukkit;

import java.util.UUID;

/**
 * Created by Ryan on 16/04/2017.
 */
public class GamePlayer {

    private UHCWars uhcWars;

    private UUID uuid;


    public GamePlayer(UUID uuid, UHCWars uhcWars){
        this.uuid = uuid;
        this.uhcWars = uhcWars;
    }


    private void getInformation(){
        Bukkit.getScheduler().runTaskAsynchronously(this.uhcWars, new Runnable() {
            @Override
            public void run() {
                //TODO ADD GETTING INFORMATION
            }
        });
    }

    private synchronized void saveInformation(){
        //TODO ADD SAVING INFORMATION
    }
}
