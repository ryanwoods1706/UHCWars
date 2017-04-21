package me.noreach.uhcwars.locations;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.teams.Teams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;
import java.util.logging.Level;

/**
 * Created by Ryan on 16/04/2017.
 */
public class RegionManager {

    private UHCWars uhcWars;
    private FileConfiguration configuration;
    private Map<Teams, List<Block>> teamAreas = new HashMap<>();
    private List<Region> regionsInUse = new ArrayList<>();
    private List<Location> wallLocation = new ArrayList<>();
    private Map<UUID, Region> regionFactory = new HashMap<>();
    private Map<Teams, Location> teamsLocations = new HashMap<>();


    public RegionManager(UHCWars uhcWars) {
        this.uhcWars = uhcWars;
        this.uhcWars.reloadConfig();
        this.configuration = this.uhcWars.getConfig();
        loadExisting();
    }

    public void loadExisting() {
        try {
            String team1Path = "Settings.Regions." + Teams.Team_1.toString() + ".pos1.";
            String team1PathPo2 = "Settings.Regions." + Teams.Team_1.toString() + ".pos2.";
            Location team1Pos1 = new Location(Bukkit.getWorld(configuration.getString(team1Path + "worldname")), configuration.getDouble(team1Path + "x"), configuration.getDouble(team1Path + "y"), configuration.getDouble(team1Path + "z"));
            Location team1Pos2 = new Location(Bukkit.getWorld(configuration.getString(team1PathPo2 + "worldname")), configuration.getDouble(team1PathPo2 + "x"), configuration.getDouble(team1PathPo2 + "y"), configuration.getDouble(team1PathPo2 + "z"));
            team1Pos1.getChunk().load();
            team1Pos2.getChunk().load();
            Region region = new Region(team1Pos1, team1Pos2);
            this.regionsInUse.add(region);
            String team2Path = "Settings.Regions." + Teams.Team_2.toString() + ".pos1.";
            String team2PathPo2 = "Settings.Regions." + Teams.Team_2.toString() + ".pos2.";
            Location team2Pos1 = new Location(Bukkit.getWorld(configuration.getString(team2Path + "worldname")), configuration.getDouble(team2Path + "x"), configuration.getDouble(team2Path + "y"), configuration.getDouble(team2Path + "z"));
            Location team2Pos2 = new Location(Bukkit.getWorld(configuration.getString(team2PathPo2 + "worldname")), configuration.getDouble(team2PathPo2 + "x"), configuration.getDouble(team2PathPo2 + "y"), configuration.getDouble(team2PathPo2 + "z"));
            team2Pos1.getChunk().load();
            team2Pos2.getChunk().load();
            Region region2 = new Region(team2Pos1, team2Pos2);
            this.regionsInUse.add(region2);
            teamAreas.put(Teams.Team_1, this.uhcWars.getGameManager().blocksFromTwoPoints(team1Pos1, team1Pos2));
            teamAreas.put(Teams.Team_2, this.uhcWars.getGameManager().blocksFromTwoPoints(team2Pos1, team2Pos2));
            String wallpos1 = "Settings.Regions.wall.pos1.";
            String wallpos2 = "Settings.Regions.wall.pos2.";
            Location wallPos1 = new Location(Bukkit.getWorld(configuration.getString(wallpos1 + "worldname")), configuration.getDouble(wallpos1 + "x"), configuration.getDouble(wallpos1 + "y"), configuration.getDouble(wallpos1 + "z"));
            Location wallPos2 = new Location(Bukkit.getWorld(configuration.getString(wallpos2 + "worldname")), configuration.getDouble(wallpos2 + "x"), configuration.getDouble(wallpos2 + "y"), configuration.getDouble(wallpos2 + "z"));
            wallPos1.getChunk().load();
            wallPos2.getChunk().load();
            this.wallLocation.add(wallPos1);
            this.wallLocation.add(wallPos2);
            FileConfiguration configuration = uhcWars.getConfig();
            String path = "Settings.SpawnLocations.Team1.";
            String path2 = "Settings.SpawnLocations.Team2.";
            try {
                Location team1Location = new Location(Bukkit.getWorld(configuration.getString(path + "world")), configuration.getDouble(path + "x"), configuration.getDouble(path + "y"), configuration.getDouble(path + "z"));
                Location team2Location = new Location(Bukkit.getWorld(configuration.getString(path2 + "world")), configuration.getDouble(path2 + "x"), configuration.getDouble(path2 + "y"), configuration.getDouble(path2 + "z"));
                team1Location.getChunk().load();
                team2Location.getChunk().load();
                this.teamsLocations.put(Teams.Team_1, team1Location);
                this.teamsLocations.put(Teams.Team_2, team2Location);
            } catch (Exception e) {
                Bukkit.getLogger().log(Level.SEVERE, "[CONFIG ERROR] Team Spawn locations have not been set!");
                e.printStackTrace();
            }

            try {
                String path3 = "Settings.Objective.Team_1.location.";
                World Team1world = Bukkit.getWorld(this.configuration.getString("Settings.Objective.Team_1.location.world"));
                Block block = Team1world.getBlockAt(this.configuration.getInt(path3 + "x"), this.configuration.getInt(path3 + "y"), this.configuration.getInt(path3 + "z"));
                block.getChunk().load();
                Objective objective = new Objective(block, uhcWars);
                objective.setTeamObjective(Teams.Team_1);
                objective.setHealth(this.uhcWars);
                this.uhcWars.getObjectiveManager().getActiveObjectives().put(objective.getTeamObjective(), objective);

                String path4 = "Settings.Objective.Team_1.location.";
                World Team2world = Bukkit.getWorld(this.configuration.getString("Settings.Objective.Team_2.location.world"));
                Block block2 = Team2world.getBlockAt(this.configuration.getInt(path4 + "x"), this.configuration.getInt(path4 + "y"), this.configuration.getInt(path4 + "z"));
                block2.getChunk().load();
                Objective objective2 = new Objective(block2, uhcWars);
                objective2.setTeamObjective(Teams.Team_2);
                objective2.setHealth(this.uhcWars);
                this.uhcWars.getObjectiveManager().getActiveObjectives().put(objective2.getTeamObjective(), objective2);
                Bukkit.getLogger().log(Level.INFO, "[CONFIG] Successfully loaded all objectives from the config");
            } catch (Exception e) {
                e.printStackTrace();
                Bukkit.getLogger().log(Level.SEVERE, "[CONFIG ERROR] The objectives have not been set!");
            }
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, "[CONFIG ERROR] Some regions have not been set!");
            e.printStackTrace();
        }
    }


    public List<Region> getRegionsInUse() {
        return this.regionsInUse;
    }

    public Map<Teams, List<Block>> getTeamAreas() {
        return this.teamAreas;
    }

    public List<Location> getWallLocation() {
        return this.wallLocation;
    }

    public Map<UUID, Region> getRegionFactory() {
        return this.regionFactory;
    }

    public Map<Teams, Location> getTeamsLocations() {
        return this.teamsLocations;
    }
}
