package me.noreach.uhcwars.sql;

import com.mongodb.*;
import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.player.UHCPlayer;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

/**
 * Created by NoReach_ on 26/04/2017.
 */
public class StorageHandler {


    private UHCWars uhcWars;
    private StorageTypes storageType;


    /**
     * MySQL Connection Object
     */
    private Connection connection;

    private DBCollection collection;
    private DB database;
    private MongoClient client;


    public StorageHandler(UHCWars uhcWars){
        this.uhcWars = uhcWars;
        try{
            this.storageType = StorageTypes.valueOf(this.uhcWars.getConfig().getString("Settings.storageType"));
        }catch (Exception e){
            Bukkit.getLogger().log(Level.SEVERE, "[Storage] Error please enter a valid storage type");
            Bukkit.getLogger().log(Level.SEVERE, "[Storage] Storage Types: MySQL/MongoDB/None");
        }
        Bukkit.getScheduler().runTaskLater(this.uhcWars, new Runnable() {
            @Override
            public void run() {
                connect();
            }
        }, 20L);
    }


    public boolean connect(){
        switch (storageType){
            case None:
                Bukkit.getLogger().log(Level.INFO, "[Storage] Not storing any data for this plugin!");
                return true;
            case MySQL:
                String ip = this.uhcWars.getConfig().getString("Settings.SQL.ip");
                String database = this.uhcWars.getConfig().getString("Settings.SQL.database");
                String username = this.uhcWars.getConfig().getString("Settings.SQL.username");
                String password = this.uhcWars.getConfig().getString("Settings.SQL.password");
                try {
                    this.connection = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + database, username, password);
                    Statement statement = this.connection.createStatement();
                    Statement statement1 = this.connection.createStatement();
                    statement.executeUpdate("CREATE TABLE IF NOT EXISTS `uhcwars_stats` (`uuid` VARCHAR(60), `kills` INT NOT NULL DEFAULT '0', `deaths` INT NOT NULL DEFAULT '0', `wins` INT NOT NULL DEFAULT '0');");
                    statement1.executeUpdate("CREATE TABLE IF NOT EXISTS `uhcwars_kits` (`uuid` VARCHAR(60), `inv` VARCHAR(999));");
                    statement.close();
                    statement1.close();
                    Bukkit.getLogger().log(Level.INFO, "[Storage] Successfully connected to the MySQL Server/Database");
                }catch (SQLException e){
                    e.printStackTrace();
                    Bukkit.getLogger().log(Level.SEVERE, "[Storage] Could not connect to the MySQL Server/Database");
                    return false;
                }
                return true;
            case  MongoDB:
                try {
                    String mongoIP = this.uhcWars.getConfig().getString("Settings.MongoDB.ip");
                    int mongoPort = this.uhcWars.getConfig().getInt("Settings.MongoDB.port");
                    String mongoUser = this.uhcWars.getConfig().getString("Settings.MongoDB.username");
                    String mongoPassword = this.uhcWars.getConfig().getString("Settings.MongoDB.password");
                    String mongoDB = this.uhcWars.getConfig().getString("Settings.MongoDB.database");
                    ServerAddress addr = new ServerAddress(mongoIP, mongoPort);
                    List<MongoCredential> credentials = new ArrayList<>();
                    credentials.add(MongoCredential.createCredential(mongoUser, mongoDB, mongoPassword.toCharArray()));
                    client = new MongoClient(addr, credentials);
                    this.database = client.getDB(mongoDB);
                    this.collection = this.database.getCollection("uhcwars_data");
                }catch (Exception e){
                    e.printStackTrace();
                    Bukkit.getLogger().log(Level.SEVERE, "[Storage] Could not connect to the MongoDB Server/Database");
                }
                return true;
        }
        return true;
    }


    public void closeStorage(){
        switch (storageType){
            case None:
                Bukkit.getLogger().log(Level.INFO, "[Storage] No storage type found, nothing to close");
                break;
            case MySQL:
                try {
                    this.connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                    Bukkit.getLogger().log(Level.SEVERE, "[Storage] Could not close the MySQL Connection");
                }
                break;
            case MongoDB:
                try {
                    this.database.getMongo().close();
                }
                catch (Exception e){
                    e.printStackTrace();
                    Bukkit.getLogger().log(Level.SEVERE, "[Storage] Could not close the MongoDB pool");
                }
        }
    }


    public UHCPlayer manageUser(UUID uuid){
        int kills = 0;
        int deaths = 0;
        int wins = 0;
        switch (storageType){
            case None:
                Bukkit.getLogger().log(Level.INFO, "[Storage]  No storage type found, nothing to create");
                break;
            case MySQL:
                break;
            case MongoDB:
        }


        return new UHCPlayer(uuid, kills, deaths, wins);
    }

}
