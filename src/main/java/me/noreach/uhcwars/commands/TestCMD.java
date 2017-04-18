package me.noreach.uhcwars.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Ryan on 18/04/2017.
 */
public class TestCMD implements CommandExecutor{


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        player.teleport(Bukkit.getWorld("world").getSpawnLocation());
        return false;
    }
}
