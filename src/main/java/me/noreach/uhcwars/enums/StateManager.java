package me.noreach.uhcwars.enums;

import me.noreach.uhcwars.UHCWars;

/**
 * Created by Ryan on 16/04/2017.
 */
public class StateManager {

    private UHCWars uhcWars;
    private GameState gameState;

    public StateManager(UHCWars uhcWars){
        this.uhcWars = uhcWars;
    }

    public GameState getGameState(){ return this.gameState;}
    public void setGameState(GameState gameState){
        this.gameState = gameState;
    }


}
