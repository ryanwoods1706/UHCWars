package me.noreach.uhcwars.chest;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.teams.Teams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;

/**
 * Created by Ryan on 18/04/2017.
 */
public class ChestManager {

    private UHCWars uhcWars;

    public ChestManager(UHCWars uhcWars){
        this.uhcWars = uhcWars;
    }

    public void fillTeam1Chests(boolean bool){
        World world = this.uhcWars.getReferences().getGameWorld();
        for (Chunk c : world.getLoadedChunks()){
            for (BlockState b : c.getTileEntities()){
                if (b instanceof Chest){
                    if (this.uhcWars.getRegionManager().getTeamAreas().get(Teams.Team_1).contains(b.getBlock())){
                        Chest chest = (Chest) b;
                        chest.getInventory().clear();
                        Tier2Fill.fillChest(chest.getInventory());
                    }
                }
            }
        }
        if(bool) {
            Bukkit.getServer().broadcastMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + "Team 1 has had their chests refilled!");
        }
    }

    /**
     * This method fills all the chests within team  two region!
     */
    public void fillTeam2Chests(boolean bool){
        World world = this.uhcWars.getReferences().getGameWorld();
        for (Chunk c : world.getLoadedChunks()){
            for (BlockState b : c.getTileEntities()){
                if (b instanceof Chest){
                    if (this.uhcWars.getRegionManager().getTeamAreas().get(Teams.Team_2).contains(b.getBlock())){
                        Chest chest = (Chest) b;
                        chest.getInventory().clear();
                        Tier2Fill.fillChest(chest.getInventory());
                    }
                }
            }
        }
        if (bool) {
            Bukkit.getServer().broadcastMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + "Team 2 Has had their chests refilled!");
        }
    }
}
