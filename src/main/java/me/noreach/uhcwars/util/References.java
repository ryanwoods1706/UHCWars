package me.noreach.uhcwars.util;

import me.noreach.uhcwars.UHCWars;
import org.bukkit.ChatColor;

/**
 * Created by Ryan on 10/04/2017.
 */
public class References {

    private UHCWars uhcWars;
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
    }
}
