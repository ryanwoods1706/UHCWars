package me.noreach.uhcwars.util;

import me.noreach.uhcwars.UHCWars;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Created by Ryan on 10/04/2017.
 */
public class References {

    private UHCWars uhcWars;
    private FileConfiguration config;

    private String prefix;
    private String spawnWorld;
    private String gameWorld;
    private int reqStart;
    private int maxSlots;
    private String sbTitle;
    private String sbIP;
    private ChatColor mainColor;
    private ChatColor secondaryColor;


    public References(UHCWars uhcWars){
        this.uhcWars = uhcWars;
        this.uhcWars.reloadConfig();
        this.config = this.uhcWars.getConfig();
        this.prefix = this.config.getString("Settings.Messages.prefix");
        this.spawnWorld = this.config.getString("Settings.gameSettings.spawnWorld");
        this.gameWorld = this.config.getString("Settings.gameSettings.gameWorld");


    }
}
