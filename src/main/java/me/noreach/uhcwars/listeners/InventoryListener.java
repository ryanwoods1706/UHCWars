package me.noreach.uhcwars.listeners;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.teams.Teams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Created by Ryan on 20/04/2017.
 */
public class InventoryListener implements Listener {

    private UHCWars uhcWars;

    public InventoryListener(UHCWars uhcWars){
        this.uhcWars = uhcWars;
    }


    @EventHandler
    public void onInventClick(InventoryClickEvent e){
        Player pl = (Player) e.getWhoClicked();
        if (e.getClickedInventory() != null) {
            if (e.getClickedInventory().getTitle() != null) {
                if (e.getCurrentItem() != null) {
                    if (e.getClickedInventory().getTitle().contains(this.uhcWars.getReferences().getMainColor() + "Statistics")) {
                        e.setCancelled(true);
                    }
                    if (e.getClickedInventory().getTitle().contains(this.uhcWars.getReferences().getMainColor() + "Game Stats:")) {
                        e.setCancelled(true);
                    }
                    if (e.getClickedInventory().getTitle().equalsIgnoreCase(this.uhcWars.getReferences().getMainColor() + "Select a Team!")) {
                        if (e.getCurrentItem().getType() == Material.WOOL) {
                            if (!pl.hasPermission("sgwars.chooseteam")) {
                                pl.closeInventory();
                                pl.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "Uh oh, you do not have permission to choose a team!");
                                return;
                            }
                            if (this.uhcWars.getModManager().getActiveModerators().contains(pl.getUniqueId())) {
                                pl.closeInventory();
                                pl.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "You're in moderation mode silly!");
                                return;
                            }
                            if (Bukkit.getServer().getOnlinePlayers().size() < 5) {
                                pl.closeInventory();
                                pl.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "There must be a minimum of 5 players for players to choose their team!");
                                return;
                            }
                            switch (e.getCurrentItem().getDurability()) {
                                case (short) 5:
                                    this.uhcWars.getTeamManager().getPreferredTeams().put(pl.getUniqueId(), Teams.Team_1);
                                    pl.closeInventory();
                                    pl.sendMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + "You've chosen Team 1!");
                                    break;
                                case (short) 14:
                                    this.uhcWars.getTeamManager().getPreferredTeams().put(pl.getUniqueId(), Teams.Team_2);
                                    pl.closeInventory();
                                    pl.sendMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + "You've chosen Team 2!");
                                    break;
                            }
                        }

                    }
                }
            }
        }
    }
}
