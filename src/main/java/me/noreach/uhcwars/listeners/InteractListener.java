package me.noreach.uhcwars.listeners;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.locations.Objective;
import me.noreach.uhcwars.locations.Region;
import me.noreach.uhcwars.teams.Teams;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryan on 17/04/2017.
 */
public class InteractListener implements Listener {

    private UHCWars uhcWars;

    public InteractListener(UHCWars uhcWars){
        this.uhcWars = uhcWars;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player pl = e.getPlayer();
        if (this.uhcWars.getSpectatorManager().getSpectators().contains(pl.getUniqueId())){
            e.setCancelled(true);
            return;
        }
        if (pl.getItemInHand().getType() == Material.GOLD_AXE && pl.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(this.uhcWars.getReferences().getMainColor() + "Region Maker")){
            Region region = this.uhcWars.getRegionManager().getRegionFactory().get(pl.getUniqueId());
            if (region == null){
                pl.sendMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + "Could not find your region");
                return;
            }
            if (e.getAction() == Action.LEFT_CLICK_BLOCK){
                e.setCancelled(true);
                region.setCorner1(e.getClickedBlock().getLocation());
                pl.sendMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + ChatColor.GREEN + "Set Position 1 of region");
            }
            else if (e.getAction() == Action.RIGHT_CLICK_BLOCK){
                region.setCorner2(e.getClickedBlock().getLocation());
                pl.sendMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + ChatColor.GREEN + "Set Position 2 of region");
            }


        }
        else if (pl.getItemInHand().getType() == Material.GOLD_AXE && pl.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(this.uhcWars.getReferences().getMainColor() + "Objective Maker")) {
            if (e.getAction() == Action.LEFT_CLICK_BLOCK){
                e.setCancelled(true);
                Objective objective = this.uhcWars.getObjectiveManager().getObjectiveCache().get(pl.getUniqueId());
                objective.setBlock(e.getClickedBlock());
                pl.sendMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + "Set " + this.uhcWars.getReferences().getSecondaryColor() + objective.getTeamObjective() + this.uhcWars.getReferences().getMainColor() + " Objective block!");

            }
        }
        if (pl.getItemInHand().getType() == Material.PAPER && pl.getInventory().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(this.uhcWars.getReferences().getMainColor() + "Team Chooser")){
            pl.openInventory(this.uhcWars.getInvent().getTeamInventory());
        }
        if (pl.getItemInHand().getType() == Material.CHEST){
            this.uhcWars.getInvent().giveItems(pl, false);
            pl.sendMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + "Save your hotbar layout by doing /kit save");
        }
    }
}
