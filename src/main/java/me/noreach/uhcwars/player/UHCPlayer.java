package me.noreach.uhcwars.player;

import me.noreach.uhcwars.util.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

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

    /**
     * Constructor to build a new UHCPlayer
     * @param uuid
     */
    public UHCPlayer(UUID uuid){
        this.uuid = uuid;
    }


    /**
     * Method to get the stats inventory for the user
     * @return the inventory to display
     */
    public Inventory getStatsInventory(){
        Inventory inv = Bukkit.createInventory(null, 9 * 3, ChatColor.AQUA + "Statistics: " + ChatColor.YELLOW  + Bukkit.getOfflinePlayer(uuid).getName());
        inv.clear();
        inv.setItem(0, kills.getItemStack());
        inv.setItem(1, deaths.getItemStack());
        inv.setItem(2, wins.getItemStack());
        return inv;
    }



    public Stat getKills(){ return this.kills;}

    public Stat getDeaths(){ return this.deaths;}

    public Stat getWins(){ return this.wins;}


}
