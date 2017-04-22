package me.noreach.uhcwars.locations;

import me.noreach.uhcwars.UHCWars;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 * Created by Ryan on 17/04/2017.
 */
public class SpawnLocation {

    private UHCWars uhcWars;
    private Player player;
    FileConfiguration configuration;
    private Location team1Location;
    private Location team2Location;


    public SpawnLocation(UHCWars uhcWars, Player player){
        this.uhcWars = uhcWars;
        uhcWars.reloadConfig();
        this.configuration = uhcWars.getConfig();
        this.player = player;
    }

    public void setTeam1Location(Location location){
        this.team1Location = location;
        String path = "Settings.SpawnLocations.Team1.";
        configuration.set(path + "world", location.getWorld().getName());
        configuration.set(path + "x", location.getX());
        configuration.set(path + "y", location.getY());
        configuration.set(path + "z", location.getZ());
        configuration.set(path + "yaw", location.getYaw());
        configuration.set(path + "pitch", location.getPitch());
        uhcWars.saveConfig();
        player.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.GREEN + "Successfully set Team 1 Location");
    }

    public void setTeam2Location(Location location){
        this.team2Location = location;
        String path = "Settings.SpawnLocations.Team2.";
        configuration.set(path + "world", location.getWorld().getName());
        configuration.set(path + "x", location.getX());
        configuration.set(path + "y", location.getY());
        configuration.set(path + "z", location.getZ());
        configuration.set(path + "yaw", location.getYaw());
        configuration.set(path + "pitch", location.getPitch());
        uhcWars.saveConfig();
        player.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.GREEN + "Successfully set Team 2 Location");
    }

    public Location getTeam1Location(){
        return this.team1Location;
    }
    public Location getTeam2Location(){
        return this.team2Location;
    }

}
