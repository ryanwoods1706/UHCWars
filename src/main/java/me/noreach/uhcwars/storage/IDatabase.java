package me.noreach.uhcwars.storage;

import me.noreach.uhcwars.player.UHCPlayer;

import java.util.UUID;

/**
 * Created by NoReach_ on 27/04/2017.
 */
public abstract class IDatabase {

    public abstract boolean initalize();

    public abstract boolean doesPlayerExist(UUID uuid);

    public abstract void createPlayer(UUID uuid);

    public abstract UHCPlayer getPlayer(UUID uuid);

    public abstract int getKills(UUID uuid);


    public abstract int getDeaths(UUID uuid);


    public abstract int getWins(UUID uuid);


    public abstract void updateKills(UUID uuid);


    public abstract void updateDeaths(UUID uuid);


    public abstract void updateWins(UUID uuid);


    public abstract void closeDataStore();
}
