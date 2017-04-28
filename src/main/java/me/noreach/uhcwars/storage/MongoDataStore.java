package me.noreach.uhcwars.storage;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.player.UHCPlayer;

import java.util.UUID;

/**
 * Created by NoReach_ on 27/04/2017.
 */
public class MongoDataStore extends IDatabase {

    private UHCWars uhcWars;

    public MongoDataStore(UHCWars uhcWars) {
        this.uhcWars = uhcWars;
        this.initalize();
    }

    @Override
    public boolean initalize() {
        return false;
    }

    @Override
    public boolean doesPlayerExist(UUID uuid) {
        return false;
    }

    @Override
    public void createPlayer(UUID uuid) {

    }

    @Override
    public UHCPlayer getPlayer(UUID uuid) {
        return null;
    }

    @Override
    public int getKills(UUID uuid) {
        return 0;
    }

    @Override
    public int getDeaths(UUID uuid) {
        return 0;
    }

    @Override
    public int getWins(UUID uuid) {
        return 0;
    }

    @Override
    public void updateKills(UUID uuid) {

    }

    @Override
    public void updateDeaths(UUID uuid) {

    }

    @Override
    public void updateWins(UUID uuid) {

    }

    @Override
    public void closeDataStore() {

    }
}
