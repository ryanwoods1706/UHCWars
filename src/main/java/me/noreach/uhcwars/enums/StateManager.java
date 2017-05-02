package me.noreach.uhcwars.enums;

import me.noreach.uhcwars.UHCWars;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import org.bukkit.ChatColor;

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
        if (this.uhcWars.getTestServer()) {
            switch (gameState) {
                case INGAME:
                    MinecraftServer.getServer().setMotd(ChatColor.BOLD + "" + ChatColor.GOLD + "UHCWars game in progress!");
                    break;
                case LOBBY:
                    MinecraftServer.getServer().setMotd(ChatColor.BOLD + "" + ChatColor.GOLD + "UHCWars Lobby Mode!");
                    break;
            }
        }
    }


}
