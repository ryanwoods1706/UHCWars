package me.noreach.uhcwars.commands;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.enums.GameState;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Ryan on 17/04/2017.
 */
public class SetObjectiveCMD implements CommandExecutor {

    private UHCWars uhcWars;

    public SetObjectiveCMD(UHCWars uhcWars){
        this.uhcWars = uhcWars;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof ConsoleCommandSender){
            sender.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "You cannot use this command as console!");
            return true;
        }
        Player player = (Player) sender;
        if (this.uhcWars.getStateManager().getGameState() != GameState.LOBBY){
            player.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "You can only set objective blocks in lobby mode!");
            return true;
        }
        if (this.uhcWars.getRegionManager().getRegionsInUse().size() != 2){
            player.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "One or more team regions are not defined!");
            return true;
        }
        this.uhcWars.getInvent().giveObjectiveInv(player);
        player.sendMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + "Left click to set Team_1 Objective, and right click to set Team_2 Objective!");
        return false;
    }
}
