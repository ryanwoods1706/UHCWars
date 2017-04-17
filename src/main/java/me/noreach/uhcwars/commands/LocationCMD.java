package me.noreach.uhcwars.commands;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.locations.SpawnLocation;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Ryan on 17/04/2017.
 */
public class LocationCMD implements CommandExecutor {

    private UHCWars uhcWars;

    public LocationCMD(UHCWars uhcWars){
        this.uhcWars = uhcWars;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof ConsoleCommandSender){
            sender.sendMessage(ChatColor.RED + "You must be a player to perform this command!");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("uhcwars.SpawnLocation.set")){
            player.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "You do not have permission to perform this command!");
            return true;
        }
        try{
            if (args[0].equalsIgnoreCase("team1")){
                SpawnLocation spawnLocation = new SpawnLocation(uhcWars,  player);
                spawnLocation.setTeam1Location(player.getLocation());
                return true;
            }
            else if (args[0].equalsIgnoreCase("team2")){
                SpawnLocation spawnLocation = new SpawnLocation(uhcWars,  player);
                spawnLocation.setTeam2Location(player.getLocation());
                return true;
            }
            else{
                player.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "Usage: /setlocation <team1/team2>");
                return true;
            }
        }catch (ArrayIndexOutOfBoundsException e){
            player.sendMessage(ChatColor.RED + "Usage: /setlocation <team1/team2>");
        }

        return false;
    }
}
