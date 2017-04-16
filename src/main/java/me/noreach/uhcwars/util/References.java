package me.noreach.uhcwars.util;

import me.noreach.uhcwars.UHCWars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
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

    private String prefix;
    private String sbTitle;
    private String sbIP;
    private ChatColor mainColor;
    private ChatColor secondaryColor;


    public References(UHCWars uhcWars) {
        this.uhcWars = uhcWars;
        this.uhcWars.reloadConfig();
        this.config = this.uhcWars.getConfig();
        this.prefix = ChatColor.translateAlternateColorCodes('&', this.config.getString("Settings.Messages.prefix"));
        this.sbTitle = ChatColor.translateAlternateColorCodes('&', this.config.getString("Settings.Messages.scoreboardTitle"));
        this.sbIP = ChatColor.translateAlternateColorCodes('&', this.config.getString("Settings.Messages.scoreboardIP"));
        this.mainColor = ChatColor.valueOf(this.config.getString("Settings.Messages.mainColor"));
        this.secondaryColor = ChatColor.valueOf(this.config.getString("Settings.Messages.secondaryColor"));
        this.spawnWorld = Bukkit.getWorld(this.config.getString("Settings.gameSettings.spawnWorld"));
        this.gameWorld = Bukkit.getWorld(this.config.getString("Settings.gameSettings.gameWorld"));
        this.wallMaterial = Material.valueOf(this.config.getString("Settings.gameSettings.wallBlock"));
        this.reqStart = this.config.getInt("Settings.gameSettings.minStart");
        this.maxSlots = this.config.getInt("Settings.gameSettings.maxSlots");
        this.timeLimit = this.config.getInt("Settings.gameSettings.timeLimit");
        this.wallDropTime = this.config.getInt("Settings.gameSettings.wallDropTime");
        this.halfTime = this.config.getInt("Settings.gameSettings.halfTime");
        this.goldenHeadsOnDeath = this.config.getBoolean("Settings.gameSettings.goldenHeadOnDeath");
        this.objectiveHealth = this.config.getInt("Settings.gameSettings.objectiveHealth");
        this.objectiveDmgPerHit = this.config.getInt("Settings.gameSettings.objectiveDmgPerHit");

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
            Bukkit.getServer().shutdown();
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


}
