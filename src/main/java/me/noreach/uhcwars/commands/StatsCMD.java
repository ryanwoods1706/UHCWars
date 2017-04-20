package me.noreach.uhcwars.commands;

import me.noreach.uhcwars.UHCWars;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Ryan on 20/04/2017.
 */
public class StatsCMD implements CommandExecutor {

    private UHCWars uhcWars;

    public StatsCMD(UHCWars uhcWars){
        this.uhcWars = uhcWars;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        return false;
    }
}
