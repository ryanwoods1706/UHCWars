package me.noreach.uhcwars.commands;

import me.noreach.uhcwars.UHCWars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Ryan on 30/04/2017.
 */
public class StatsResetCMD implements CommandExecutor{


    private UHCWars uhcWars;

    public StatsResetCMD(UHCWars uhcWars){
        this.uhcWars = uhcWars;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player){
            sender.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "This command can only be used by the console!");
            return true;
        }
        try{
            Player target = Bukkit.getPlayer(args[0]);
            this.uhcWars.getStorage().scrubPlayer(target.getUniqueId());
        }catch (ArrayIndexOutOfBoundsException e){
            sender.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "Usage: /statsreset <player>");
        }
        return false;
    }
}
