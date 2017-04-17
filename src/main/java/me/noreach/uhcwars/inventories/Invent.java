package me.noreach.uhcwars.inventories;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.util.ItemCreator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by Ryan on 17/04/2017.
 */
public class Invent {

    private UHCWars uhcWars;

    public Invent(UHCWars uhcWars){
        this.uhcWars = uhcWars;
    }

    public void giveRegionInv(Player player){
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.getInventory().setItem(0, new ItemCreator(Material.GOLD_AXE).setName(this.uhcWars.getReferences().getMainColor() + "Region Maker").toItemStack());
        player.updateInventory();
    }
    public void giveObjectiveInv(Player player){
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.getInventory().setItem(0, new ItemCreator(Material.GOLD_AXE).setName(this.uhcWars.getReferences().getMainColor() + "Objective Maker").toItemStack());
        player.updateInventory();
    }
}
