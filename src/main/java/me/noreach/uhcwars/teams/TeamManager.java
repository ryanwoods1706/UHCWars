package me.noreach.uhcwars.teams;

import me.noreach.uhcwars.UHCWars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Created by Ryan on 16/04/2017.
 */
public class TeamManager {

    private UHCWars uhcWars;
    private List<Player> team1 = new ArrayList<>();
    private List<Player> team2 = new ArrayList<>();
    private Map<Teams, Integer> teamKills = new HashMap<>();
    private Map<UUID, Teams> preferredTeams = new HashMap<>();


    public TeamManager(UHCWars uhcWars) {
        this.uhcWars = uhcWars;
    }


    public void splitPlayers() {
        boolean bool = false;
        List<Player> tempList = new ArrayList<>();
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            tempList.add(player);
        }
        for (UUID uuid : this.preferredTeams.keySet()) {
            if (this.preferredTeams.get(uuid) == Teams.Team_1) {
                this.team1.add(Bukkit.getPlayer(uuid));
                tempList.remove(Bukkit.getPlayer(uuid));
                Bukkit.getPlayer(uuid).sendMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + "You've been assigned to: " + this.uhcWars.getReferences().getSecondaryColor() + Teams.Team_1);
            }
            if (this.preferredTeams.get(uuid) == Teams.Team_2) {
                this.team2.add(Bukkit.getPlayer(uuid));
                tempList.remove(Bukkit.getPlayer(uuid));
                Bukkit.getPlayer(uuid).sendMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + "You've been assigned to: " + this.uhcWars.getReferences().getSecondaryColor() + Teams.Team_2);
            }
        }
        for (Player player : tempList) {
            bool = !bool;
            if (bool) {
                team1.add(player);
                player.sendMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + "You've been assigned to: " + this.uhcWars.getReferences().getSecondaryColor() + Teams.Team_1);
            } else {
                team2.add(player);
                player.sendMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + "You've been assigned to: " + this.uhcWars.getReferences().getSecondaryColor() + Teams.Team_2);
            }
        }

    }

    public List<Player> getTeam1() {
        return this.team1;
    }

    public List<Player> getTeam2() {
        return this.team2;
    }

    public Map<UUID, Teams> getPreferredTeams() {
        return this.preferredTeams;
    }

    public Map<Teams, Integer> getTeamKills() {
        return this.teamKills;
    }

    public Teams getPlayerTeam(Player player) {
        if (this.team1.contains(player)) {
            return Teams.Team_1;
        }
        if (this.team2.contains(player)) {
            return Teams.Team_2;
        }
        return null;
    }

    public Teams getOppositeTeam(Player player) {
        if (this.team1.contains(player)) {
            return Teams.Team_2;
        }
        if (this.team2.contains(player)) {
            return Teams.Team_1;
        }
        return null;
    }
}
