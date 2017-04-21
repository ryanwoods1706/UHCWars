package me.noreach.uhcwars.game;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.enums.GameState;
import me.noreach.uhcwars.player.GamePlayer;
import me.noreach.uhcwars.teams.Teams;
import me.noreach.uhcwars.timers.InGame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Ryan on 16/04/2017.
 */
public class GameManager {

    private UHCWars uhcWars;
    private ChatColor mainColor;
    private ChatColor secondaryColor;


    public GameManager(UHCWars uhcWars){
        this.uhcWars = uhcWars;
        this.mainColor = this.uhcWars.getReferences().getMainColor();
        this.secondaryColor = this.uhcWars.getReferences().getSecondaryColor();
    }


    public void startGame() {
        this.uhcWars.getRegionManager().getTeamsLocations().get(Teams.Team_1).getChunk().load();
        this.uhcWars.getRegionManager().getTeamsLocations().get(Teams.Team_2).getChunk().load();
        Bukkit.getScheduler().runTaskLater(this.uhcWars, new Runnable() {
            @Override
            public void run() {
                uhcWars.getTeamManager().splitPlayers();
                uhcWars.getTeamManager().getTeamKills().put(Teams.Team_1, 0);
                uhcWars.getTeamManager().getTeamKills().put(Teams.Team_2, 0);
                for (Player player : uhcWars.getTeamManager().getTeam1()) {
                    player.teleport(uhcWars.getRegionManager().getTeamsLocations().get(Teams.Team_1));
                    uhcWars.getInvent().giveItems(player, true);
                }
                for (Player player : uhcWars.getTeamManager().getTeam2()) {
                    player.teleport(uhcWars.getRegionManager().getTeamsLocations().get(Teams.Team_2));
                    uhcWars.getInvent().giveItems(player, true);
                }
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    uhcWars.getPlayerManager().getPlayerKills().put(player.getUniqueId(), 0);
                }
                uhcWars.getChestManager().fillTeam1Chests(true);
                uhcWars.getChestManager().fillTeam2Chests(true);
                new InGame(uhcWars).runTaskTimer(uhcWars, 0, 20L);
                uhcWars.getStateManager().setGameState(GameState.INGAME);
            }
        },10L);

    }

    public void endGame(){
        if (this.uhcWars.getObjectiveManager().getActiveObjectives().get(Teams.Team_1).getHealth() > this.uhcWars.getObjectiveManager().getActiveObjectives().get(Teams.Team_2).getHealth()) {
            int health = this.uhcWars.getObjectiveManager().getActiveObjectives().get(Teams.Team_1).getHealth();
            Bukkit.getServer().broadcastMessage(this.uhcWars.getReferences().getPrefix() + secondaryColor + "Team 1" + mainColor + " has won the game with " + secondaryColor + health + mainColor + " health remaining!");
            for (Player player : this.uhcWars.getTeamManager().getTeam1()){
                GamePlayer gamePlayer = this.uhcWars.getPlayerManager().getPlayerData().get(player.getUniqueId());
                gamePlayer.getWins().incrementValue();
                this.uhcWars.getPlayerManager().getPlayerData().put(player.getUniqueId(), gamePlayer);
            }
        }
        if (this.uhcWars.getObjectiveManager().getActiveObjectives().get(Teams.Team_2).getHealth() > this.uhcWars.getObjectiveManager().getActiveObjectives().get(Teams.Team_1).getHealth()){
            int health = this.uhcWars.getObjectiveManager().getActiveObjectives().get(Teams.Team_2).getHealth();
            Bukkit.getServer().broadcastMessage(this.uhcWars.getReferences().getPrefix() + secondaryColor + "Team 2" + mainColor + " has won the game with " + secondaryColor + health + mainColor + " health remaining!");
            for (Player player : this.uhcWars.getTeamManager().getTeam2()){
                GamePlayer gamePlayer = this.uhcWars.getPlayerManager().getPlayerData().get(player.getUniqueId());
                gamePlayer.getWins().incrementValue();
                this.uhcWars.getPlayerManager().getPlayerData().put(player.getUniqueId(), gamePlayer);
            }
        }
        if (this.uhcWars.getObjectiveManager().getActiveObjectives().get(Teams.Team_1).getHealth() == this.uhcWars.getObjectiveManager().getActiveObjectives().get(Teams.Team_2).getHealth()){
            Bukkit.getServer().broadcastMessage(this.uhcWars.getReferences().getPrefix() + mainColor + "Its a tie! Both teams have the same health!");
        }
        Bukkit.getScheduler().runTaskLater(this.uhcWars, new Runnable() {
            @Override
            public void run() {
                uhcWars.getBlockManager().resetAllBlocks();
                for (UUID uuid : uhcWars.getPlayerManager().getPlayerData().keySet()){
                    GamePlayer gamePlayer = uhcWars.getPlayerManager().getPlayerData().get(uuid);
                    gamePlayer.saveInformation();
                }
                Bukkit.getServer().shutdown();
            }
        }, 60L);
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

    public String getTime(int secs) {
        int h = secs / 3600, secondsLeft = secs - h * 3600, m = secondsLeft / 60, s = secondsLeft - m * 60;
        String timeF = "";

        if (h < 10) {
            timeF = timeF + "0";
        }
        timeF = timeF + h + ":";
        if (m < 10) {
            timeF = timeF + "0";
        }
        timeF = timeF + m + ":";
        if (s < 10) {
            timeF = timeF + "0";
        }
        timeF = timeF + s;

        return timeF;
    }
}
