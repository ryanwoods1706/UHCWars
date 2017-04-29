package me.noreach.uhcwars.commands;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.enums.GameState;
import me.noreach.uhcwars.locations.Region;
import me.noreach.uhcwars.teams.Teams;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Ryan on 17/04/2017.
 */
public class RegionCMD implements CommandExecutor{

    private UHCWars uhcWars;

    public RegionCMD(UHCWars uhcWars){
        this.uhcWars = uhcWars;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof ConsoleCommandSender){
            sender.sendMessage(this.uhcWars.getReferences().getPrefix() +ChatColor.RED + "You cannot use this command as console!");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("uhcwars.region.create")){
            player.sendMessage(this.uhcWars.getReferences().getPrefix() +ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }
        if (this.uhcWars.getStateManager().getGameState() != GameState.LOBBY){
            player.sendMessage(this.uhcWars.getReferences().getPrefix() +ChatColor.RED + "You can only create regions whilst in lobby mode!");
            return true;
        }
        try{
            if (args[0].equalsIgnoreCase("create")){
                String name;
                if (args[1].equalsIgnoreCase("team1")){
                    name = Teams.Team_1.toString();
                }else if (args[1].equalsIgnoreCase("team2")){
                    name = Teams.Team_2.toString();
                }else if (args[1].equalsIgnoreCase("wall")){
                    name = "wall";
                }else{
                    player.sendMessage(this.uhcWars.getReferences().getPrefix() +ChatColor.RED + "Region names: 'Team1' 'Team2' or 'wall'");
                    return true;
                }
                this.uhcWars.getInvent().giveRegionInv(player);
                Region region = new Region(player, uhcWars);
                region.setRegionname(name);
                this.uhcWars.getRegionManager().getRegionFactory().put(player.getUniqueId(), region);
                player.sendMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + "Left and right click to create the region!");
            }else if (args[0].equalsIgnoreCase("save")){
                Region region = this.uhcWars.getRegionManager().getRegionFactory().get(player.getUniqueId());
                if (region == null){
                    player.sendMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() +"Could not find your region!");
                    return true;
                }
                if (!region.attemptSave()) {
                    return false;
                }
                region.attemptSave();
                player.sendMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + "Successfully saved your region!");
                this.uhcWars.getRegionManager().getRegionFactory().remove(player.getUniqueId());
                this.uhcWars.getRegionManager().getRegionsInUse().add(region);

            }


        }catch (ArrayIndexOutOfBoundsException e){
            player.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "Usage: /region create <team1/team2/wall>");
            player.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "Usage: /region save");
        }

        return false;
    }
}
