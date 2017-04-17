package me.noreach.uhcwars.locations;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.teams.Teams;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Created by Ryan on 17/04/2017.
 */
public class Objective {

    private FileConfiguration config;
    private UHCWars uhcWars;
    private Teams teamObjective;
    private Block block;
    private Location location;

    public Objective(Block block, Teams teams, UHCWars uhcWars){
        this.block = block;
        this.location = this.block.getLocation();
        this.teamObjective = teams;
        this.uhcWars = uhcWars;
        uhcWars.reloadConfig();
        this.config = uhcWars.getConfig();
    }

    public boolean saveObjective(){
        if (block == null || location == null || this.teamObjective == null){
            return false;
        }
        String path = "Settings.Objective." + this.teamObjective + ".blockType";
        String path2 = "Settings.Objective." + this.teamObjective + ".location";
        config.set(path, block.getType());
        config.set(path2, location);
        this.uhcWars.saveConfig();
        return true;
    }

}
