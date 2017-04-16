package me.noreach.uhcwars.game;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.teams.Teams;

/**
 * Created by Ryan on 16/04/2017.
 */
public class GameManager {

    private UHCWars uhcWars;


    public GameManager(UHCWars uhcWars){
        this.uhcWars = uhcWars;
    }


    public void startGame(){
        this.uhcWars.getTeamManager().splitPlayers();
        this.uhcWars.getTeamManager().getTeamKills().put(Teams.Team_1, 0);
        this.uhcWars.getTeamManager().getTeamKills().put(Teams.Team_2, 0);
        //ADD REGISTERING OBJECTIVE BLOCKS
    }
}
