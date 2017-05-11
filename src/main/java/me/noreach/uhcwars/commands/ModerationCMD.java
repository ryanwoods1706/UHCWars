package me.noreach.uhcwars.commands;

import me.noreach.uhcwars.UHCWars;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Ryan on 22/04/2017.
 */
public class ModerationCMD implements CommandExecutor {

    private UHCWars uhcWars;

    public ModerationCMD(UHCWars uhcWars){
        this.uhcWars = uhcWars;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof ConsoleCommandSender){
            sender.sendMessage(this.uhcWars.getReferences().getPlayerOnlyCmd());
          //  sender.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "You must be a player to perform this action!");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("uhcwars.moderate")){
            player.sendMessage(this.uhcWars.getReferences().getNoPerms());
            //player.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "You do not have permission to perform this command!");
            return true;
        }
        this.uhcWars.getModManager().manageModerator(player);
        return false;

    }
}
