package me.noreach.uhcwars.chest;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.teams.Teams;
import org.bukkit.*;
import org.bukkit.block.Block;
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
        for (Block block : this.uhcWars.getRegionManager().getTeamAreas().get(Teams.Team_1)){
            for (BlockState blockState : block.getChunk().getTileEntities()){
                if (blockState instanceof Chest) {
                    Chest chest = (Chest) blockState;
                    chest.getInventory().clear();
                    this.uhcWars.getChestFill().fillChest(chest.getInventory());
                }
            }
        }
        if(bool) {
            Bukkit.getServer().broadcastMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + "Team 1 has had their chests refilled!");
        }
    }

    public void fillTeam2Chests(boolean bool){
        for (Block block : this.uhcWars.getRegionManager().getTeamAreas().get(Teams.Team_2)){
            for (BlockState blockState : block.getChunk().getTileEntities()){
                if (blockState instanceof Chest) {
                    Chest chest = (Chest) blockState;
                    chest.getInventory().clear();
                    this.uhcWars.getChestFill().fillChest(chest.getInventory());
                }
            }
        }
        if(bool) {
            Bukkit.getServer().broadcastMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + "Team 2 has had their chests refilled!");
        }
    }

}
