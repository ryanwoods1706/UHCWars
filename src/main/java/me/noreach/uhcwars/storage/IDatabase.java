package me.noreach.uhcwars.storage;

import me.noreach.uhcwars.player.UHCPlayer;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

/**
 * Created by NoReach_ on 27/04/2017.
 */
public abstract class IDatabase {


    public abstract boolean initalize();

    public abstract void createPlayer(UUID uuid);

    public abstract UHCPlayer getPlayer(UUID uuid);

    public abstract void updatePlayer(UHCPlayer uhcPlayer);

    public abstract void closeDataStore();

//    public abstract Inventory playerKit(UUID uuid);

   // public abstract void saveCustomKit(UUID uuid, Inventory inventory);

 //   public abstract void createKit(UUID uuid, Inventory inventory);

    public abstract void scrubPlayer(UUID uuid);

}
