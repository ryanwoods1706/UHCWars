package me.noreach.uhcwars.storage;

/**
 * Created by NoReach_ on 27/04/2017.
 */
public class SQLDatastore extends IDatabase {

    public SQLDatastore(){
        this.initalize();
    }

    @Override
    public boolean initalize() {
        return false;
    }

    @Override
    public boolean getKills() {
        return false;
    }

    @Override
    public boolean getDeaths() {
        return false;
    }

    @Override
    public boolean getWins() {
        return false;
    }

    @Override
    public void updateKills() {

    }

    @Override
    public void updateDeaths() {

    }

    @Override
    public void updateWins() {

    }

    @Override
    public void closeDataStore() {

    }
}
