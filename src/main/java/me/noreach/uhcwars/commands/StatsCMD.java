package me.noreach.uhcwars.commands;

import me.noreach.uhcwars.UHCWars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Ryan on 20/04/2017.
 */
public class StatsCMD implements CommandExecutor {

    private UHCWars uhcWars;

    public StatsCMD(UHCWars uhcWars) {
        this.uhcWars = uhcWars;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player pl = (Player) sender;
        if (sender instanceof ConsoleCommandSender){
            sender.sendMessage(this.uhcWars.getReferences().getPlayerOnlyCmd());
            return true;
        }
        if (!this.uhcWars.getStats()){
            pl.sendMessage(this.uhcWars.getReferences().getStatsDisabled());
       //     pl.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "Statistics are not enabled!");
            return true;
        }
        if (args.length == 0) {
            OfflinePlayer target = Bukkit.getPlayer(pl.getUniqueId());

            pl.openInventory(this.uhcWars.getPlayerManager().getUhcPlayers().get(target.getUniqueId()).getStatsInventory());
        } else {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                pl.sendMessage("§cThat player is not online!");
                return true;
            }
            pl.openInventory(this.uhcWars.getPlayerManager().getUhcPlayers().get(target.getUniqueId()).getStatsInventory());
        }
        return false;
    }
}
