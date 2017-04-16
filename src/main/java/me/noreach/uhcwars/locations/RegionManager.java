package me.noreach.uhcwars.locations;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.teams.Teams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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
    
    
    public RegionManager(UHCWars uhcWars){
        this.uhcWars = uhcWars;
        this.uhcWars.reloadConfig();
        this.configuration = uhcWars.getConfig();
        loadExisting();
    }

    public void loadExisting(){
        try{
            String team1Path = "Settings.Regions." + Teams.Team_1.toString() + ".pos1.";
            String team1PathPo2 = "Settings.Regions." + Teams.Team_1.toString() + ".pos2.";
            Location team1Pos1 = new Location(Bukkit.getWorld(configuration.getString(team1Path + "worldname")), configuration.getDouble(team1Path + "x"), configuration.getDouble(team1Path + "y"), configuration.getDouble(team1Path + "z"));
            Location team1Pos2 = new Location(Bukkit.getWorld(configuration.getString(team1PathPo2 + "worldname")), configuration.getDouble(team1PathPo2 + "x"), configuration.getDouble(team1PathPo2 + "y"), configuration.getDouble(team1PathPo2 + "z"));
            Region region = new Region(team1Pos1, team1Pos2);
            this.regionsInUse.add(region);
            String team2Path = "Settings.Regions." + Teams.Team_2.toString() + ".pos1.";
            String team2PathPo2 = "Settings.Regions." + Teams.Team_2.toString() + ".pos2.";
            Location team2Pos1 = new Location(Bukkit.getWorld(configuration.getString(team2Path + "worldname")), configuration.getDouble(team2Path + "x"), configuration.getDouble(team2Path + "y"), configuration.getDouble(team2Path + "z"));
            Location team2Pos2 = new Location(Bukkit.getWorld(configuration.getString(team2PathPo2 + "worldname")), configuration.getDouble(team2PathPo2 + "x"), configuration.getDouble(team2PathPo2 + "y"), configuration.getDouble(team2PathPo2 + "z"));
            Region region2 = new Region(team2Pos1, team2Pos2);
            this.regionsInUse.add(region2);
            teamAreas.put(Teams.Team_1, this.uhcWars.getGameManager().blocksFromTwoPoints(team1Pos1, team1Pos2));
            teamAreas.put(Teams.Team_2, this.uhcWars.getGameManager().blocksFromTwoPoints(team2Pos1, team2Pos2));
            String wallpos1 = "Settings.Regions.Wall.pos1.";
            String wallpos2 = "Settings.Regions.Wall.pos2.";
            Location wallPos1 = new Location(Bukkit.getWorld(configuration.getString(wallpos1 + "worldname")), configuration.getDouble(wallpos1 + "x"), configuration.getDouble(wallpos1 + "y"), configuration.getDouble(wallpos1 + "z"));
            Location wallPos2 = new Location(Bukkit.getWorld(configuration.getString(wallpos2 + "worldname")), configuration.getDouble(wallpos2 + "x"), configuration.getDouble(wallpos2 + "y"), configuration.getDouble(wallpos2 + "z"));
            this.wallLocation.add(wallPos1);
            this.wallLocation.add(wallPos2);
        }catch (Exception e){
            Bukkit.getLogger().log(Level.SEVERE, ChatColor.RED + "!!!!COULD NOT LOAD REGIONS FROM CONFIG!!!!");
            e.printStackTrace();
        }
    }


    public List<Region> getRegionsInUse(){ return this.regionsInUse;}
    public Map<Teams, List<Block>> getTeamAreas(){ return this.teamAreas;}
    public List<Location> getWallLocation(){return this.wallLocation;}
}
