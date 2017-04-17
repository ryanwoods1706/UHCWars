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

    public ObjectiveManager(UHCWars uhcWars) {
        this.uhcWars = uhcWars;
        this.uhcWars.reloadConfig();
        this.config = this.uhcWars.getConfig();
    }


    public Map<UUID, Objective> getObjectiveCache() {
        return this.objectiveCache;
    }

    public Map<Teams, Objective> getActiveObjectives() {
        return this.activeObjectives;
    }


}
