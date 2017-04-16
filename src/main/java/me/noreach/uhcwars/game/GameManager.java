package me.noreach.uhcwars.game;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.teams.Teams;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

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




    public List<Block> blocksFromTwoPoints(Location loc1, Location loc2) {
        List<Block> blocks = new ArrayList<Block>();

        int topBlockX = (loc1.getBlockX() < loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
        int bottomBlockX = (loc1.getBlockX() > loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());

        int topBlockY = (loc1.getBlockY() < loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
        int bottomBlockY = (loc1.getBlockY() > loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());

        int topBlockZ = (loc1.getBlockZ() < loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
        int bottomBlockZ = (loc1.getBlockZ() > loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());

        for (int x = bottomBlockX; x <= topBlockX; x++) {
            for (int z = bottomBlockZ; z <= topBlockZ; z++) {
                for (int y = bottomBlockY; y <= topBlockY; y++) {
                    Block block = loc1.getWorld().getBlockAt(x, y, z);
                    blocks.add(block);
                }
            }
        }

        return blocks;
    }
}
