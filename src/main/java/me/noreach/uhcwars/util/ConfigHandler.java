package me.noreach.uhcwars.util;

import me.noreach.uhcwars.UHCWars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

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
        Bukkit.getScheduler().runTaskLater(this.uhcWars, new Runnable() {
            @Override
            public void run() {
                checkConfig();
            }
        }, 20L);
    }

    private void checkConfig(){
        this.config.addDefault("Settings.SQL.ip", "localhost");
        this.config.addDefault("Settings.SQL.port", 3306);
        this.config.addDefault("Settings.SQL.database", "database");
        this.config.addDefault("Settings.SQL.username", "username");
        this.config.addDefault("Settings.SQL.password", "password");
        this.config.addDefault("Settings.Messages.prefix", "defaultPrefix");
        this.config.addDefault("Settings.Messages.scoreboardTitle", "defaultTitle");
        this.config.addDefault("Settings.Messages.scoreboardIP", "defaultIP");
        this.config.addDefault("Settings.Messages.mainColor", ChatColor.AQUA.toString());
        this.config.addDefault("Settings.Messages.secondaryColor", ChatColor.GOLD.toString());
        this.config.addDefault("Settings.gameSettings.spawnWorld", "spawn");
        this.config.addDefault("Settings.gameSettings.gameWorld", "world");
        this.config.addDefault("Settings.gameSettings.minStart", 20);
        this.config.addDefault("Settings.gameSettings.maxSlots", 50);
        this.config.addDefault("Settings.gameSettings.timeLimit", 900);
        this.config.addDefault("Settings.gameSettings.wallDropTime", 300);
        this.config.addDefault("Settings.gameSettings.halfTime", 600);
        this.config.addDefault("Settings.gameSettings.goldenHeadOnDeath", true);
        this.config.addDefault("Settings.gameSettings.objectiveHealth", 300);
        this.config.addDefault("Settings.gameSettings.objectiveDmgPerHit", 1);
        this.config.options().copyDefaults(true);
        this.uhcWars.saveConfig();
        this.uhcWars.reloadConfig();
    }
}
