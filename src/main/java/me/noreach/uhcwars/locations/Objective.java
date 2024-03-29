package me.noreach.uhcwars.locations;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.teams.Teams;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 * Created by Ryan on 17/04/2017.
 */
public class Objective {

    private FileConfiguration config;
    private Player creator;
    private UHCWars uhcWars;

    private int health;
    private Teams teamObjective;
    private Block block;
    private Location location;

    public Objective(Player creator, UHCWars uhcWars, Teams teams){
        this.creator = creator;
        this.uhcWars = uhcWars;
        this.uhcWars.reloadConfig();
        this.config = this.uhcWars.getConfig();
        this.teamObjective = teams;
        this.health = this.uhcWars.getReferences().getObjectiveHealth();
    }
    public Objective(Block block, UHCWars uhcWars){
        this.uhcWars = uhcWars;
        setBlock(block);

    }


    public void setHealth(UHCWars uhcWars){
        this.health = uhcWars.getReferences().getObjectiveHealth();
    }

    public void setBlock(Block block){
        this.block = block;
        this.location = this.block.getLocation();
        //sendMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + "Successfully set " + this.uhcWars.getReferences().getSecondaryColor() + teamObjective + this.uhcWars.getReferences().getMainColor() + " objective block!");
        //sendMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + "Objective ready to save: /objective save");
    }

    public void setLocation(Location location){
        this.location = location;
    }

    public boolean saveObjective(){
        if (block == null || location == null || this.teamObjective == null){
            return false;
        }
        String path = "Settings.Objective." + this.teamObjective + ".blockType";
        String path2 = "Settings.Objective." + this.teamObjective + ".location";
        config.set(path, block.getType().toString());
        config.set(path2 + ".world", location.getWorld().getName());
        config.set(path2 + ".x", (float) location.getX());
        config.set(path2 + ".y", (float) location.getY());
        config.set(path2 + ".z", (float) location.getZ());
        this.uhcWars.saveConfig();
        sendMessage(this.uhcWars.getReferences().getSuccessObjectiveSave());
       // sendMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + "Successfully saved Objective!");
        return true;
    }

    private void sendMessage(String message){
        if (this.creator != null && this.creator.isOnline()){
            this.creator.sendMessage("" + message);
        }
    }

    public Block getBlock(){ return this.block;}

    public int getHealth(){ return this.health;}
    public void decrementHealth(){
        this.health -= this.uhcWars.getReferences().getObjectiveDmgPerHit();
    }

    public Teams getTeamObjective(){ return this.teamObjective;}
    public void setTeamObjective(Teams teamObjective){ this.teamObjective = teamObjective;}

}
