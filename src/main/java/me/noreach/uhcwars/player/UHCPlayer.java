package me.noreach.uhcwars.player;

import me.noreach.uhcwars.util.ItemCreator;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.UUID;

/**
 * Created by NoReach_ on 26/04/2017.
 */
public class UHCPlayer {

    private UUID uuid;


    /**
     * Statistic Objects for player
     */
    private Stat kills = new Stat(new ItemCreator(Material.DIAMOND_SWORD).setName(ChatColor.AQUA + "Kills").toItemStack(), 0);
    private Stat deaths = new Stat(new ItemCreator(Material.SKULL_ITEM).setName(ChatColor.AQUA + "Deaths").toItemStack(), 0);
    private Stat wins = new Stat(new ItemCreator(Material.PAPER).setName(ChatColor.AQUA + "Wins").toItemStack(), 0);


    public UHCPlayer(UUID uuid, int kills, int deaths, int wins){
        this.uuid = uuid;
        this.kills.setAmount(kills);
        this.deaths.setAmount(deaths);
        this.wins.setAmount(wins);
    }


}
