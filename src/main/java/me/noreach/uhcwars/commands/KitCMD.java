package me.noreach.uhcwars.commands;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.enums.GameState;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Created by Ryan on 21/04/2017.
 */
public class KitCMD implements CommandExecutor {

    private UHCWars uhcWars;
    private ChatColor mainColor;
    private ChatColor secondaryColor;

    public KitCMD(UHCWars uhcWars){
        this.uhcWars = uhcWars;
        this.mainColor = this.uhcWars.getReferences().getMainColor();
        this.secondaryColor = this.uhcWars.getReferences().getSecondaryColor();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof ConsoleCommandSender){
            sender.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "You cannot use this command as console");
            return true;
        }
        Player player = (Player) sender;
        if (this.uhcWars.getStateManager().getGameState() != GameState.LOBBY){
            player.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "This command can only be used whilst in the lobby!");
            return true;
        }
        try{
            if (args[0].equalsIgnoreCase("save")){
                Inventory inventory = player.getInventory();
                this.uhcWars.getStorage().saveCustomKit(player.getUniqueId(), inventory);
                player.sendMessage(this.uhcWars.getReferences().getPrefix() + mainColor + "Successfully saved your hotbar layout!");
                this.uhcWars.getInvent().giveJoinInventory(player);
            }



        }catch (ArrayIndexOutOfBoundsException e){
            //TODO ADD USAGE
        }

        return false;
    }
}
