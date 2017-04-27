package me.noreach.uhcwars.storage;

import me.noreach.uhcwars.player.UHCPlayer;

/**
 * Created by NoReach_ on 27/04/2017.
 */
public abstract class IDatabase {

    public abstract boolean initalize();

    public abstract boolean doesPlayerExist();

    public abstract void createPlayer();

    public abstract UHCPlayer getPlayer();

    public abstract boolean getKills();


    public abstract boolean getDeaths();


    public abstract boolean getWins();


    public abstract void updateKills();


    public abstract void updateDeaths();


    public abstract void updateWins();


    public abstract void closeDataStore();
}