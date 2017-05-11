package me.noreach.uhcwars.commands;

import me.noreach.uhcwars.UHCWars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Ryan on 18/04/2017.
 */
public class WorldCMD implements CommandExecutor{

    private UHCWars uhcWars;

    public WorldCMD(UHCWars uhcWars){
        this.uhcWars = uhcWars;
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof ConsoleCommandSender){
            commandSender.sendMessage(this.uhcWars.getReferences().getPlayerOnlyCmd());
            //commandSender.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "You must be a player to use this command!");
        }
        Player player = (Player) commandSender;
        if (!player.hasPermission("uhcwars.world.teleport")){
            player.sendMessage(this.uhcWars.getReferences().getNoPerms());
            //player.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "You do not have permission to perform this command!");
            return true;
        }
        try{
            World world;
            try {
                world = Bukkit.getWorld(args[0]);
            }catch (NullPointerException e){
                player.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "You must choose a world that is loaded/exists!");
                return true;
            }
            player.teleport(world.getSpawnLocation());
            player.sendMessage(ChatColor.GREEN + "Whoosh!");
        }catch (ArrayIndexOutOfBoundsException e){
            player.sendMessage(ChatColor.RED + "Usage: /world <worldname>");
        }
        return false;
    }
}
