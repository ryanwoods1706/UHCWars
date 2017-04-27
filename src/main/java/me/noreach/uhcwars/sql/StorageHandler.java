package me.noreach.uhcwars.sql;

import com.mongodb.*;
import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.player.UHCPlayer;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;

/**
 * Created by NoReach_ on 26/04/2017.
 */
public class StorageHandler {


    private UHCWars uhcWars;
    private StorageTypes storageType;

    private Map<UUID, List<Integer>> inUseMap = new HashMap<>();

    /**
     * MySQL Connection Object
     */
    private Connection connection;

    private DBCollection collection;
    private DB database;
    private MongoClient client;


    public StorageHandler(UHCWars uhcWars) {
        this.uhcWars = uhcWars;
        try {
            this.storageType = StorageTypes.valueOf(this.uhcWars.getConfig().getString("Settings.storageType"));
        } catch (Exception e) {
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


    public boolean connect() {
        switch (storageType) {
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
                } catch (SQLException e) {
                    e.printStackTrace();
                    Bukkit.getLogger().log(Level.SEVERE, "[Storage] Could not connect to the MySQL Server/Database");
                    return false;
                }
                return true;
            case MongoDB:
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
                } catch (Exception e) {
                    e.printStackTrace();
                    Bukkit.getLogger().log(Level.SEVERE, "[Storage] Could not connect to the MongoDB Server/Database");
                }
                return true;
        }
        return true;
    }


    public void closeStorage() {
        switch (storageType) {
            case None:
                Bukkit.getLogger().log(Level.INFO, "[Storage] No storage type found, nothing to close");
                break;
            case MySQL:
                try {
                    this.connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Bukkit.getLogger().log(Level.SEVERE, "[Storage] Could not close the MySQL Connection");
                }
                break;
            case MongoDB:
                try {
                    this.database.getMongo().close();
                } catch (Exception e) {
                    e.printStackTrace();
                    Bukkit.getLogger().log(Level.SEVERE, "[Storage] Could not close the MongoDB pool");
                }
        }
    }


    public UHCPlayer generateData(UUID uuid) {
        UHCPlayer uhcPlayer = new UHCPlayer(uuid);
        switch (storageType) {
            case None:
                Bukkit.getLogger().log(Level.INFO, "[Storage]  No storage type found, nothing to create");
                break;
            case MySQL:
                Bukkit.getScheduler().runTaskAsynchronously(this.uhcWars, new Runnable() {
                    @Override
                    public void run() {
                        PreparedStatement statement = null;
                        PreparedStatement statement1 = null;
                        ResultSet resultSet = null;
                        try {
                            assert connection != null;
                            statement = connection.prepareStatement("SELECT * FROM `uhcwars_stats` WHERE `uuid` = ?;");
                            statement.setString(1, uuid.toString());
                            statement.executeQuery();
                            resultSet = statement.getResultSet();
                            if (resultSet.next()) {
                                uhcPlayer.getKills().setAmount(resultSet.getInt("kills"));
                                uhcPlayer.getDeaths().setAmount(resultSet.getInt("deaths"));
                                uhcPlayer.getWins().setAmount(resultSet.getInt("wins"));;
                            }else{
                                statement1 = connection.prepareStatement("INSERT INTO `uhcwars_stats` (uuid) VALUES(?);");
                                statement1.setString(1, uuid.toString());
                                statement1.executeUpdate();
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
                return uhcPlayer;
            case MongoDB:
                DBObject dbObject = new BasicDBObject("uuid", uuid);
                DBObject found = collection.findOne(dbObject);
                if (found == null){
                    dbObject.put("kills", 0);
                    dbObject.put("deaths", 0);
                    dbObject.put("wins", 0);
                    collection.insert(dbObject);
                    Bukkit.getLogger().log(Level.INFO, "[Storage] Could not find user in the collection, creating the user!");
                    found = collection.findOne(dbObject);
                }
                uhcPlayer.getKills().setAmount((int) found.get("kills"));
                uhcPlayer.getDeaths().setAmount((int) found.get("deaths"));
                uhcPlayer.getWins().setAmount((int) found.get("wins"));
                return uhcPlayer;
        }
        return null;
    }

}
