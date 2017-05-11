package me.noreach.uhcwars.commands;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.enums.GameState;
import me.noreach.uhcwars.locations.Objective;
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
public class ObjectiveCMD implements CommandExecutor {

    private UHCWars uhcWars;

    public ObjectiveCMD(UHCWars uhcWars) {
        this.uhcWars = uhcWars;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(this.uhcWars.getReferences().getPlayerOnlyCmd());
           // sender.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "You cannot use this command as console!");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("uhcwars.objective.register")) {
            player.sendMessage(this.uhcWars.getReferences().getNoPerms());
           // player.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "You do not have permission to perform this command!");
            return true;
        }
        if (this.uhcWars.getStateManager().getGameState() != GameState.LOBBY) {
            player.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "You can only set objective blocks in lobby mode!");
            return true;
        }
        if (this.uhcWars.getRegionManager().getRegionsInUse().size() != 2) {
            player.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "One or more team regions are not defined!");
            return true;
        }
        try {
            if (args[0].equalsIgnoreCase("create")) {
                Teams teams;
                if (args[1].equalsIgnoreCase("team1")) {
                    teams = Teams.Team_1;
                } else if (args[1].equalsIgnoreCase("team2")) {
                    teams = Teams.Team_2;
                } else {
                    player.sendMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + "Usage: /objective create <team1/team2>");
                    return true;
                }
                Objective objective = new Objective(player, uhcWars, teams);
                this.uhcWars.getObjectiveManager().getObjectiveCache().put(player.getUniqueId(), objective);
                this.uhcWars.getInvent().giveObjectiveInv(player);
                player.sendMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + "Left click a block to set it as an Objective Block!");
            } else if (args[0].equalsIgnoreCase("save")) {
                Objective objective = this.uhcWars.getObjectiveManager().getObjectiveCache().get(player.getUniqueId());
                if (objective == null) {
                    player.sendMessage(ChatColor.RED + "Could not find your objective!");
                    return true;
                }
                objective.saveObjective();
                this.uhcWars.getObjectiveManager().getActiveObjectives().put(objective.getTeamObjective(), objective);
                player.sendMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + "Objective is now ACTIVE!");
                this.uhcWars.getObjectiveManager().getObjectiveCache().remove(player.getUniqueId());
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            player.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "Usage: /objective create <team1/team2>");
            player.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "Usage: /objective save");
        }
        return false;
    }
}
