package me.noreach.uhcwars.locations;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.teams.Teams;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;
import java.util.logging.Level;

/**
 * Created by Ryan on 17/04/2017.
 */
public class ObjectiveManager {

    private UHCWars uhcWars;
    private FileConfiguration config;
    private Map<UUID, Objective> objectiveCache = new HashMap<>();
    private Map<Teams, Objective> activeObjectives = new HashMap<>();

    public ObjectiveManager(UHCWars uhcWars){
        this.uhcWars = uhcWars;
        this.uhcWars.reloadConfig();
        this.config = this.uhcWars.getConfig();
    }


    public void loadObjectives(){
        try {
            this.uhcWars.reloadConfig();
            this.config = this.uhcWars.getConfig();
            String team1Path2 = "Settings.Objective." + Teams.Team_1.toString() + ".location";
            Location location = new Location(Bukkit.getWorld(config.getString(team1Path2 + ".world")), config.getDouble(team1Path2 + ".x"), config.getDouble(team1Path2 + ".y"), config.getDouble(team1Path2 + ".z"));
            Block block = location.getBlock();
            Objective objective = new Objective(block);
            this.activeObjectives.put(Teams.Team_1, objective);
            String team2Path2 = "Settings.Objective." + Teams.Team_2.toString() + ".location";
            Location location2 = new Location(Bukkit.getWorld(config.getString(team2Path2 + ".world")), config.getDouble(team2Path2 + ".x"), config.getDouble(team2Path2 + ".y"), config.getDouble(team2Path2 + ".z"));
            Block block1 = location2.getBlock();
            Objective objective1 = new Objective(block1);
            this.activeObjectives.put(Teams.Team_2, objective1);
            Bukkit.getLogger().log(Level.INFO, "[CONFIG] Successfully loaded all objectives!");
        }catch (Exception e){
            Bukkit.getLogger().log(Level.SEVERE, "[CONFIG ERROR] One or more objectives have not been set!");
            e.printStackTrace();
        }
    }




    public Map<UUID, Objective> getObjectiveCache(){ return this.objectiveCache;}
    public Map<Teams, Objective> getActiveObjectives(){ return this.activeObjectives;}





}
