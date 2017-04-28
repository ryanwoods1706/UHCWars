package me.noreach.uhcwars.player;

import me.noreach.uhcwars.UHCWars;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Ryan on 18/04/2017.
 */
public class PlayerManager {

    private UHCWars uhcWars;
    private Map<UUID, UHCPlayer> uhcPlayers = new HashMap<>();
    private Map<UUID, Integer> playerKills = new HashMap<>();

    public PlayerManager(UHCWars uhcWars){
        this.uhcWars = uhcWars;
    }

    public Map<UUID, Integer> getPlayerKills(){ return this.playerKills;}
    public Map<UUID, UHCPlayer> getUhcPlayers(){ return this.uhcPlayers;}


}
