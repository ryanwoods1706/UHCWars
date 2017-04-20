package me.noreach.uhcwars.game;

import me.noreach.uhcwars.UHCWars;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.logging.Level;

/**
 * Created by Ryan on 20/04/2017.
 */
public class BlockManager {

    private UHCWars uhcWars;
    private Map<UUID, List<Block>> playerBlocks = new HashMap<>();

    public BlockManager(UHCWars uhcWars){
        this.uhcWars = uhcWars;
    }

    public Map<UUID, List<Block>> getPlayerBlocks(){ return this.playerBlocks;}

    public void updatePlayerBlocks(Player player, Block block){
        if (this.playerBlocks.containsKey(player.getUniqueId())){
            List<Block> playerBlocks = this.playerBlocks.get(player.getUniqueId());
            playerBlocks.add(block);
            this.playerBlocks.put(player.getUniqueId(), playerBlocks);
            Bukkit.getLogger().log(Level.INFO, "[BlockManager] Updated players data with new block information");
        }
        else{
            List<Block> newList = new ArrayList<>();
            this.playerBlocks.put(player.getUniqueId(), newList);
            Bukkit.getLogger().log(Level.INFO, "[BlockManager] Couldn't find player data creating new player!");
        }
    }

    public void resetAllBlocks(){
        try {
            for (UUID uuid : this.playerBlocks.keySet()) {
                List<Block> tempList = this.playerBlocks.get(uuid);
                for (Block block : tempList) {
                    if (block != null) {
                        block.setType(Material.AIR);
                        block.getState().update();
                    }
                }
            }
        }catch (Exception e){
            Bukkit.getLogger().log(Level.SEVERE, "[BlockManager] Error resetting player blocks! Contact a dev!");
            e.printStackTrace();
        }
    }


}
