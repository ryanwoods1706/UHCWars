package me.noreach.uhcwars.game;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.enums.GameState;
import me.noreach.uhcwars.player.UHCPlayer;
import me.noreach.uhcwars.teams.Teams;
import me.noreach.uhcwars.timers.InGame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

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
            }
        },10L);
        for (UUID uuid : this.uhcWars.getModManager().getActiveModerators()){
            Player player = Bukkit.getPlayer(uuid);
            for (Player gamePl : Bukkit.getServer().getOnlinePlayers()){
                gamePl.hidePlayer(player);
            }
            Location spawnLoc = this.uhcWars.getRegionManager().getTeamsLocations().get(Teams.Team_1);
            Location specLocation = new Location(spawnLoc.getWorld(), spawnLoc.getX(), spawnLoc.getY() + 15, spawnLoc.getZ());
            player.teleport(specLocation);
            this.uhcWars.getInvent().giveModInv(player);
        }
        this.uhcWars.getStateManager().setGameState(GameState.INGAME);

    }

    public void endGame(){
        if (this.uhcWars.getObjectiveManager().getActiveObjectives().get(Teams.Team_1).getHealth() > this.uhcWars.getObjectiveManager().getActiveObjectives().get(Teams.Team_2).getHealth()) {
            int health = this.uhcWars.getObjectiveManager().getActiveObjectives().get(Teams.Team_1).getHealth();
            Bukkit.getServer().broadcastMessage(this.uhcWars.getReferences().getTeam1Won().replace("{health}", String.valueOf(health)));
            //Bukkit.getServer().broadcastMessage(this.uhcWars.getReferences().getPrefix() + secondaryColor + "Team 1" + mainColor + " has won the game with " + secondaryColor + health + mainColor + " health remaining!");
            for (Player player : this.uhcWars.getTeamManager().getTeam1()){
                UHCPlayer gamePlayer = this.uhcWars.getPlayerManager().getUhcPlayers().get(player.getUniqueId());
                gamePlayer.getWins().incrementValue();
                this.uhcWars.getPlayerManager().getUhcPlayers().put(player.getUniqueId(), gamePlayer);
            }
        }
        if (this.uhcWars.getObjectiveManager().getActiveObjectives().get(Teams.Team_2).getHealth() > this.uhcWars.getObjectiveManager().getActiveObjectives().get(Teams.Team_1).getHealth()){
            int health = this.uhcWars.getObjectiveManager().getActiveObjectives().get(Teams.Team_2).getHealth();
            Bukkit.getServer().broadcastMessage(this.uhcWars.getReferences().getTeam2Won().replace("{health}", String.valueOf(health)));
           // Bukkit.getServer().broadcastMessage(this.uhcWars.getReferences().getPrefix() + secondaryColor + "Team 2" + mainColor + " has won the game with " + secondaryColor + health + mainColor + " health remaining!");
            for (Player player : this.uhcWars.getTeamManager().getTeam2()){
                UHCPlayer gamePlayer = this.uhcWars.getPlayerManager().getUhcPlayers().get(player.getUniqueId());
                gamePlayer.getWins().incrementValue();
                this.uhcWars.getPlayerManager().getUhcPlayers().put(player.getUniqueId(), gamePlayer);
            }
        }
        if (this.uhcWars.getObjectiveManager().getActiveObjectives().get(Teams.Team_1).getHealth() == this.uhcWars.getObjectiveManager().getActiveObjectives().get(Teams.Team_2).getHealth()){
            Bukkit.getServer().broadcastMessage(this.uhcWars.getReferences().getGameTie());
           // Bukkit.getServer().broadcastMessage(this.uhcWars.getReferences().getPrefix() + mainColor + "Its a tie! Both teams have the same health!");
        }
        Bukkit.getScheduler().runTaskLater(this.uhcWars, new Runnable() {
            @Override
            public void run() {
                uhcWars.getBlockManager().resetAllBlocks();
                for (UUID uuid : uhcWars.getPlayerManager().getUhcPlayers().keySet()){
                    UHCPlayer uhcPlayer = uhcWars.getPlayerManager().getUhcPlayers().get(uuid);
                    uhcWars.getStorage().updatePlayer(uhcPlayer);
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
