package me.noreach.uhcwars.game;

import me.noreach.uhcwars.UHCWars;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Ryan on 22/04/2017.
 */
public class ModManager {

    private UHCWars uhcWars;
    private List<UUID> activeModerators = new ArrayList<>();


    public ModManager(UHCWars uhcWars) {
        this.uhcWars = uhcWars;
    }

    public void manageModerator(Player player){
        if (!this.activeModerators.contains(player.getUniqueId())) {
            this.activeModerators.add(player.getUniqueId());
            player.sendMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + "You'll become a moderator once the game begins!");
        }else{
            this.activeModerators.remove(player.getUniqueId());
            player.sendMessage(this.uhcWars.getReferences().getPrefix() + this.uhcWars.getReferences().getMainColor() + "You're no longer a moderator!");
        }
    }

    public List<UUID> getActiveModerators() {
        return this.activeModerators;
    }


}
