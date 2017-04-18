package me.noreach.uhcwars.timers;

import me.noreach.uhcwars.UHCWars;
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
    private ChatColor mainColor = this.uhcWars.getReferences().getMainColor();
    private ChatColor secondaryColor = this.uhcWars.getReferences().getSecondaryColor();

    public InGame(UHCWars uhcWars){
        this.uhcWars = uhcWars;
    }
    private int second = this.uhcWars.getReferences().getTimeLimit();
    @Override
    public void run() {
        second--;
        if (second == this.uhcWars.getReferences().getHalfTime()){
            this.uhcWars.generateWall();
            Bukkit.broadcastMessage(this.uhcWars.getReferences().getPrefix() + mainColor + "Half Time! All chests have been refilled and you have 2 minutes to collect Items!");
            this.uhcWars.getChestManager().fillTeam1Chests(false);
            this.uhcWars.getChestManager().fillTeam2Chests(false);
        }
        if (second == this.uhcWars.getReferences().getHalfTime() - 120){
            this.uhcWars.degenerateWall();
            Bukkit.broadcastMessage(this.uhcWars.getReferences().getPrefix() + mainColor + "Half time has now ended! May the odds be ever in your favour!");
        }
        if (second == 0){
            //TODO ADD END GAME METHOD
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
                    mainColor + this.uhcWars.getReferences().getSbIP()
            });
        }


    }
}
