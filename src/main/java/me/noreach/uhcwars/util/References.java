package me.noreach.uhcwars.util;

import me.noreach.uhcwars.UHCWars;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.logging.Level;

/**
 * Created by Ryan on 10/04/2017.
 */
public class References {

    private UHCWars uhcWars;
    private FileConfiguration config;


    private World spawnWorld;
    private World gameWorld;
    private Material wallMaterial;
    private int reqStart;
    private int maxSlots;
    private int timeLimit;
    private int wallDropTime;
    private int halfTime;
    private boolean goldenHeadsOnDeath;
    private int objectiveHealth;
    private int objectiveDmgPerHit;
    private int killsTillFill;
    private boolean stats;

    private String prefix;
    private String sbTitle;
    private String sbIP;
    private ChatColor mainColor;
    private ChatColor secondaryColor;


    public References(UHCWars uhcWars) {
        this.uhcWars = uhcWars;
        uhcWars.reloadConfig();
        config = uhcWars.getConfig();


    }

    public void loadValues(){
        stats = config.getBoolean("Settings.SQL.stats");
        prefix = ChatColor.translateAlternateColorCodes('&', config.getString("Settings.Messages.prefix"));
        sbTitle = ChatColor.translateAlternateColorCodes('&', config.getString("Settings.Messages.scoreboardTitle"));
        sbIP = ChatColor.translateAlternateColorCodes('&', config.getString("Settings.Messages.scoreboardIP"));
        secondaryColor = ChatColor.valueOf(config.getString("Settings.Messages.secondaryColor"));
        mainColor = ChatColor.valueOf(config.getString("Settings.Messages.mainColor"));
        spawnWorld = new WorldCreator(config.getString("Settings.gameSettings.spawnWorld")).createWorld();
        gameWorld = new WorldCreator(config.getString("Settings.gameSettings.gameWorld")).createWorld();
        wallMaterial = Material.valueOf(config.getString("Settings.gameSettings.wallBlock"));
        reqStart = config.getInt("Settings.gameSettings.minStart");
        maxSlots = config.getInt("Settings.gameSettings.maxSlots");
        timeLimit = config.getInt("Settings.gameSettings.timeLimit");
        wallDropTime = config.getInt("Settings.gameSettings.wallDropTime");
        halfTime = config.getInt("Settings.gameSettings.halfTime");
        goldenHeadsOnDeath = config.getBoolean("Settings.gameSettings.goldenHeadOnDeath");
        objectiveHealth = config.getInt("Settings.gameSettings.objectiveHealth");
        objectiveDmgPerHit = config.getInt("Settings.gameSettings.objectiveDmgPerHit");
        killsTillFill = config.getInt("Settings.gameSettings.killsTillFill");
        Bukkit.getLogger().log(Level.INFO, "[CONFIG] Successfully loaded all values from the config");

        if (this.timeLimit < this.halfTime) {
            Bukkit.getLogger().log(Level.SEVERE, "[CONFIG ERROR] The timelimit must be greater than the half time value!");
            Bukkit.getServer().shutdown();
        }
        if (this.timeLimit < this.wallDropTime) {
            Bukkit.getLogger().log(Level.SEVERE, "[CONFIG ERROR] The timelimit must be greater than the wallDrop time!");
            Bukkit.getServer().shutdown();
        }
        if (this.spawnWorld == null || this.gameWorld == null) {
            Bukkit.getLogger().log(Level.SEVERE, "[CONFIG ERROR] Either the spawn or game world does not exist!");
            //TODO ADD BACK
            //Bukkit.getServer().shutdown();
        }
    }

    public World getSpawnWorld() {
        return this.spawnWorld;
    }

    public World getGameWorld() {
        return this.gameWorld;
    }

    public Material getWallMaterial() {
        return this.wallMaterial;
    }

    public int getReqStart() {
        return this.reqStart;
    }

    public int getMaxSlots() {
        return this.maxSlots;
    }

    public int getTimeLimit() {
        return this.timeLimit;
    }

    public int getWallDropTime() {
        return this.wallDropTime;
    }

    public int getHalfTime() {
        return this.halfTime;
    }

    public boolean isGoldenHeadsOnDeath() {
        return this.goldenHeadsOnDeath;
    }

    public int getObjectiveHealth() {
        return this.objectiveHealth;
    }

    public int getObjectiveDmgPerHit() {
        return this.objectiveDmgPerHit;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getSbTitle() {
        return this.sbTitle;
    }

    public String getSbIP() {
        return this.sbIP;
    }

    public ChatColor getMainColor() {
        return this.mainColor;
    }

    public ChatColor getSecondaryColor() {
        return this.secondaryColor;
    }

    public boolean getStats() {
        return this.stats;
    }

    public int getKillsTillFill(){ return this.killsTillFill;}


}
