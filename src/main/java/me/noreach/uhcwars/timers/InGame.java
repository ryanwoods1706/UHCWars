package me.noreach.uhcwars.timers;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.teams.Teams;
import me.noreach.uhcwars.util.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Ryan on 17/04/2017.
 */
public class InGame extends BukkitRunnable {

    private UHCWars uhcWars;
    private ScoreboardManager scoreboard = new ScoreboardManager();
    private ChatColor mainColor;
    private ChatColor secondaryColor;
    private int second;

    public InGame(UHCWars uhcWars){
        this.uhcWars = uhcWars;
        this.mainColor = this.uhcWars.getReferences().getMainColor();
        this.secondaryColor = this.uhcWars.getReferences().getSecondaryColor();
        this.second = this.uhcWars.getReferences().getTimeLimit();
    }
    @Override
    public void run() {
        second--;
        if (second == this.uhcWars.getReferences().getWallDropTime()){
            this.uhcWars.degenerateWall();
            Bukkit.getServer().broadcastMessage(this.uhcWars.getReferences().getGamePvP());
            //Bukkit.getServer().broadcastMessage(this.uhcWars.getReferences().getPrefix() + mainColor + "PvP is now enabled! May the odds be ever in your favour!");
        }
        else if (second == this.uhcWars.getReferences().getHalfTime()){
            this.uhcWars.generateWall();
            Bukkit.getServer().broadcastMessage(this.uhcWars.getReferences().getHalfTimeStarted());
            //Bukkit.getServer().broadcastMessage(this.uhcWars.getReferences().getPrefix() + mainColor + "Half Time! All chests have been refilled and you have 2 minutes to collect Items!");
            this.uhcWars.getChestManager().fillTeam1Chests(false);
            this.uhcWars.getChestManager().fillTeam2Chests(false);
            Bukkit.getScheduler().runTaskLater(this.uhcWars, new Runnable() {
                @Override
                public void run() {
                    for (Player player : uhcWars.getTeamManager().getTeam1()){
                        player.teleport(uhcWars.getRegionManager().getTeamsLocations().get(Teams.Team_1));
                    }
                    for (Player player : uhcWars.getTeamManager().getTeam2()){
                        player.teleport(uhcWars.getRegionManager().getTeamsLocations().get(Teams.Team_2));
                    }
                }
            }, 20L);
        }
        else if (second == this.uhcWars.getReferences().getHalfTime() - 120){
            this.uhcWars.degenerateWall();
            Bukkit.getServer().broadcastMessage(this.uhcWars.getReferences().getHalfTimeEnded());
            //Bukkit.getServer().broadcastMessage(this.uhcWars.getReferences().getPrefix() + mainColor + "Half time has now ended! May the odds be ever in your favour!");
        }
        else if (second == 0){
            this.uhcWars.getGameManager().endGame();
        }
        for (Player player : Bukkit.getServer().getOnlinePlayers()){
            scoreboard.unrankedSidebarDisplay(player, this.uhcWars.getReferences().getSbTitle(), new String[]{
                    mainColor + "You >>",
                    secondaryColor + player.getName(),
                    ChatColor.YELLOW.toString(),
                    mainColor + "Game Information >>",
                    mainColor + "Time: " + secondaryColor + this.uhcWars.getGameManager().getTime(second),
                    mainColor + "Team Kills: " + secondaryColor + this.uhcWars.getTeamManager().getTeamKills().get(this.uhcWars.getTeamManager().getPlayerTeam(player)),
                    mainColor + "Your Kills: " + secondaryColor + this.uhcWars.getPlayerManager().getPlayerKills().get(player.getUniqueId()),
                    mainColor + "Your Objective: " + secondaryColor  + this.uhcWars.getObjectiveManager().getActiveObjectives().get(this.uhcWars.getTeamManager().getPlayerTeam(player)).getHealth(),
                    mainColor + "Enemy Objective: " + secondaryColor + this.uhcWars.getObjectiveManager().getActiveObjectives().get(this.uhcWars.getTeamManager().getOppositeTeam(player)).getHealth(),
                    ChatColor.GOLD.toString(),
                    this.uhcWars.getReferences().getSbIP()
            });
        }


    }
}
