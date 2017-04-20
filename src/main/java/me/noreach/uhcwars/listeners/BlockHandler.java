package me.noreach.uhcwars.listeners;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.enums.GameState;
import me.noreach.uhcwars.locations.Objective;
import me.noreach.uhcwars.teams.Teams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Created by Ryan on 18/04/2017.
 */
public class BlockHandler implements Listener{

    private UHCWars uhcWars;
    private ChatColor mainColor;
    private ChatColor secondaryColor;

    public BlockHandler(UHCWars uhcWars){
        this.uhcWars = uhcWars;
        this.mainColor  = this.uhcWars.getReferences().getMainColor();
        this.secondaryColor = this.uhcWars.getReferences().getSecondaryColor();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Block block = e.getBlock();
        Player pl = e.getPlayer();
        if (this.uhcWars.getStateManager().getGameState() == GameState.INGAME){
            Teams teams = this.uhcWars.getTeamManager().getPlayerTeam(pl);
            if (this.uhcWars.getRegionManager().getTeamAreas().get(teams).contains(block)){
                e.setCancelled(true);
                pl.sendMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + "You cannot break your own objective!");
            }
            if (this.uhcWars.getRegionManager().getTeamAreas().get(this.uhcWars.getTeamManager().getOppositeTeam(pl)).contains(block)){
                Objective objective = this.uhcWars.getObjectiveManager().getActiveObjectives().get(this.uhcWars.getTeamManager().getOppositeTeam(pl));
                objective.decrementHealth();
                this.uhcWars.getObjectiveManager().getActiveObjectives().put(this.uhcWars.getTeamManager().getOppositeTeam(pl), objective);
                if (objective.getHealth() > 0){
                    e.setCancelled(true);
                }
                if (objective.getHealth() == this.uhcWars.getReferences().getObjectiveHealth() /2){
                    Bukkit.broadcastMessage(this.uhcWars.getReferences().getPrefix() + secondaryColor + this.uhcWars.getTeamManager().getOppositeTeam(pl) + mainColor + " is at 50% health!");
                }
                if (objective.getHealth() == 0){
                    e.setCancelled(true);
                    this.uhcWars.getGameManager().endGame();
                }
            }
        }
        else if (this.uhcWars.getStateManager().getGameState() == GameState.LOBBY){
            if (!pl.hasPermission("uhcwars.build.lobby")) {
                e.setCancelled(true);
                pl.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "You cannot break blocks here!");
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        Player pl = e.getPlayer();
        Block block = e.getBlock();
        if (this.uhcWars.getStateManager().getGameState() == GameState.INGAME) {
            this.uhcWars.getBlockManager().updatePlayerBlocks(pl, block);
        }
        else if (this.uhcWars.getStateManager().getGameState() == GameState.LOBBY){
            if (!pl.hasPermission("uhcwars.build.lobby")) {
                e.setCancelled(true);
                pl.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "You cannot place blocks here!");
            }
        }
    }
}
