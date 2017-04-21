package me.noreach.uhcwars.commands;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.enums.GameState;
import me.noreach.uhcwars.teams.Teams;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Ryan on 21/04/2017.
 */
public class TeamChatCMD implements CommandExecutor {

    private UHCWars uhcWars;

    public TeamChatCMD(UHCWars uhcWars){
        this.uhcWars = uhcWars;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof ConsoleCommandSender){
            sender.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "You cannot use team chat as console!");
            return true;
        }
        if (this.uhcWars.getStateManager().getGameState() != GameState.INGAME){
            sender.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "You can only use this command ingame!");
            return true;
        }
        Player player = (Player) sender;
        Teams teams = this.uhcWars.getTeamManager().getPlayerTeam(player);
        try{
            StringBuilder msg = new StringBuilder();
            for (String string : args) {
                msg.append(string).append(" ");
            }
            switch (teams){
                case Team_1:
                    for (Player teamPlayers : this.uhcWars.getTeamManager().getTeam1()){
                        teamPlayers.sendMessage(this.uhcWars.getReferences().getSecondaryColor() + msg.toString());
                    }
                    break;
                case Team_2:
                    for (Player teamPlayers : this.uhcWars.getTeamManager().getTeam2()){
                        teamPlayers.sendMessage(this.uhcWars.getReferences().getSecondaryColor() + msg.toString());
                    }
                    break;
            }
        }catch (Exception e){
            player.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "Usage: /teamchat <message>");
        }
        return false;
    }
}
