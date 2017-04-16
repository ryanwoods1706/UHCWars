package me.noreach.uhcwars.locations;

import me.noreach.uhcwars.UHCWars;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 * Created by Ryan on 16/04/2017.
 */
public class Region {

    private FileConfiguration configuration;

    private Player player;
    private Location corner1;
    private Location corner2;
    private String regionname;

    public Region(Player player, UHCWars uhcWars){
        this.player = player;
        uhcWars.reloadConfig();
        this.configuration = uhcWars.getConfig();

    }

    public Region(Location corner1, Location corner2){
        this.corner1 = corner1;
        this.corner2 = corner2;
    }

    public void setCorner1(Location corner1){
        this.corner1 = corner1;
        sendMessage(ChatColor.GREEN + "Successfully set Corner_1!");
    }
    public void setCorner2(Location corner2){
        this.corner2 = corner2;
        sendMessage(ChatColor.GREEN + "Successfully set Corner_2!");
    }
    public void setRegionname(String regionname){
        this.regionname = regionname;
    }

    public void attemptSave(){
        if (this.corner1 == null){
            sendMessage(ChatColor.RED + "You must set corner 1 before saving!");
            return;
        }
        if (this.corner2 == null){
            sendMessage(ChatColor.RED + "You must set corner 2 before saving!");
            return;
        }
        String pos1Path = "Settings.Regions." + regionname + ".pos1.";
        String pos2Path = "Settings.Regions." + regionname + ".pos2.";
        configuration.set(pos1Path + "worldname", this.corner1.getWorld().getName());
        configuration.set(pos1Path + "x", this.corner1.getX());
        configuration.set(pos1Path + "y", this.corner1.getY());
        configuration.set(pos1Path + "z", this.corner1.getZ());

        configuration.set(pos2Path + "worldname", this.corner2.getWorld().getName());
        configuration.set(pos2Path + "x", this.corner2.getX());
        configuration.set(pos2Path + "y", this.corner2.getY());
        configuration.set(pos2Path + "z", this.corner2.getZ());

        return;
    }

    private void sendMessage(String message){
        if (this.player != null){
            if (this.player.isOnline()){
                player.sendMessage("" + message);
            }
        }
    }
}
