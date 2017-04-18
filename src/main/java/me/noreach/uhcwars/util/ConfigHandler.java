package me.noreach.uhcwars.util;

import me.noreach.uhcwars.UHCWars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by Ryan on 15/04/2017.
 */
public class ConfigHandler {

    private UHCWars uhcWars;
    private FileConfiguration config;

    public ConfigHandler(UHCWars uhcWars){
        this.uhcWars = uhcWars;
        this.uhcWars.reloadConfig();
        this.config = this.uhcWars.getConfig();
        checkConfig();
    }

    private void checkConfig(){
        this.uhcWars.getConfig().addDefault("Settings.SQL.stats", false);
        this.uhcWars.getConfig().addDefault("Settings.SQL.ip", "localhost");
        this.uhcWars.getConfig().addDefault("Settings.SQL.database", "database");
        this.uhcWars.getConfig().addDefault("Settings.SQL.username", "username");
        this.uhcWars.getConfig().addDefault("Settings.SQL.password", "password");
        this.uhcWars.getConfig().addDefault("Settings.Messages.prefix", "defaultPrefix");
        this.uhcWars.getConfig().addDefault("Settings.Messages.scoreboardTitle", "defaultTitle");
        this.uhcWars.getConfig().addDefault("Settings.Messages.scoreboardIP", "defaultIP");
        this.uhcWars.getConfig().addDefault("Settings.Messages.mainColor", "RED");
        this.uhcWars.getConfig().addDefault("Settings.Messages.secondaryColor", "GOLD");
        this.uhcWars.getConfig().addDefault("Settings.gameSettings.spawnWorld", "spawn");
        this.uhcWars.getConfig().addDefault("Settings.gameSettings.gameWorld", "world");
        this.uhcWars.getConfig().addDefault("Settings.gameSettings.wallBlock", "BEDROCK");
        this.uhcWars.getConfig().addDefault("Settings.gameSettings.minStart", 20);
        this.uhcWars.getConfig().addDefault("Settings.gameSettings.maxSlots", 50);
        this.uhcWars.getConfig().addDefault("Settings.gameSettings.timeLimit", 900);
        this.uhcWars.getConfig().addDefault("Settings.gameSettings.wallDropTime", 300);
        this.uhcWars.getConfig().addDefault("Settings.gameSettings.halfTime", 600);
        this.uhcWars.getConfig().addDefault("Settings.gameSettings.goldenHeadOnDeath", true);
        this.uhcWars.getConfig().addDefault("Settings.gameSettings.objectiveHealth", 300);
        this.uhcWars.getConfig().addDefault("Settings.gameSettings.objectiveDmgPerHit", 1);
        this.uhcWars.getConfig().options().copyDefaults(true);
        this.uhcWars.saveConfig();
    }

}
