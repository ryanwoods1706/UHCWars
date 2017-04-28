package me.noreach.uhcwars.storage;

import com.sun.org.apache.regexp.internal.RE;
import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.player.UHCPlayer;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

/**
 * Created by NoReach_ on 27/04/2017.
 */
public class SQLDatastore extends IDatabase {

    private UHCWars uhcWars;
    private Connection connection;


    public SQLDatastore(UHCWars uhcWars){
        this.uhcWars = uhcWars;
        this.initalize();
    }

    @Override
    public boolean initalize() {
        if (!this.uhcWars.getStats()){
            Bukkit.getLogger().log(Level.INFO, "[Storage] Stats are disabled");
            return false;
        }
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
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getLogger().log(Level.SEVERE, "[Storage] Could not connect to the MySQL Server/Database");
            return false;
        }

    }

    @Override
    public boolean doesPlayerExist(UUID uuid) {
        AtomicBoolean bool = new AtomicBoolean(false);
        Bukkit.getScheduler().runTaskAsynchronously(this.uhcWars, new Runnable() {
            @Override
            public void run() {
                PreparedStatement statement = null;
                ResultSet resultSet = null;
                try {
                    statement = connection.prepareStatement("SELECT * FROM `uhcwars_stats` WHERE `uuid` = ?");
                    statement.executeQuery();
                    resultSet = statement.getResultSet();
                    if (resultSet.next()){
                        bool.set(true);
                    }else{
                        bool.set(false);
                    }
                }catch (SQLException e) {
                    e.printStackTrace();
                }
                finally {
                    try{
                        if (statement != null){
                            statement.close();
                        }
                        if (resultSet != null){
                            resultSet.close();
                        }
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }
            }
        });
        return bool.get();
    }

    @Override
    public void createPlayer(UUID uuid) {
    Bukkit.getScheduler().runTaskAsynchronously(this.uhcWars, new Runnable() {
        @Override
        public void run() {
            PreparedStatement statement1 = null;
            if (!doesPlayerExist(uuid)){
                try {
                    statement1 = connection.prepareStatement("INSERT INTO `uhcwars_stats` (uuid) VALUES (?);");
                    statement1.setString(1, uuid.toString());
                    statement1.executeUpdate();
                    Bukkit.getLogger().log(Level.INFO, "[Storage] Successfully created user: " + uuid);
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    });
    }

    @Override
    public UHCPlayer getPlayer(UUID uuid) {
        return new UHCPlayer(uuid, this.uhcWars.getStorage().getKills(uuid), this.uhcWars.getStorage().getDeaths(uuid), this.uhcWars.getStorage().getWins(uuid));
    }

    @Override
    public int getKills(UUID uuid) {
        AtomicInteger kills = new AtomicInteger(0);

        Bukkit.getScheduler().runTaskAsynchronously(this.uhcWars, new Runnable() {
            @Override
            public void run() {
                PreparedStatement statement = null;
                ResultSet resultSet = null;
                try{
                    statement = connection.prepareStatement("SELECT `kills` FROM `uhcwars_stats` WHERE `uuid` = ?");
                    statement.setString(1, uuid.toString());
                    statement.executeQuery();
                    resultSet = statement.getResultSet();
                    if (resultSet.next()){
                        kills.set(resultSet.getInt("kills"));
                    }else{
                        Bukkit.getLogger().log(Level.SEVERE, "[Storage] Error retrieving player kills for: " + uuid);
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }


            }
        });
        return kills.get();
    }

    @Override
    public int getDeaths(UUID uuid) {
        AtomicInteger deaths = new AtomicInteger(0);

        Bukkit.getScheduler().runTaskAsynchronously(this.uhcWars, new Runnable() {
            @Override
            public void run() {
                PreparedStatement statement = null;
                ResultSet resultSet = null;
                try{
                    statement = connection.prepareStatement("SELECT `deaths` FROM `uhcwars_stats` WHERE `uuid` = ?");
                    statement.setString(1, uuid.toString());
                    statement.executeQuery();
                    resultSet = statement.getResultSet();
                    if (resultSet.next()){
                        deaths.set(resultSet.getInt("deaths"));
                    }else{
                        Bukkit.getLogger().log(Level.SEVERE, "[Storage] Error retrieving player deaths for: " + uuid);
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        });
        return deaths.get();
    }

    @Override
    public int getWins(UUID uuid) {
        AtomicInteger wins = new AtomicInteger(0);

        Bukkit.getScheduler().runTaskAsynchronously(this.uhcWars, new Runnable() {
            @Override
            public void run() {
                PreparedStatement statement = null;
                ResultSet resultSet = null;
                try{
                    statement = connection.prepareStatement("SELECT `wins` FROM `uhcwars_stats` WHERE `uuid` = ?");
                    statement.setString(1, uuid.toString());
                    statement.executeQuery();
                    resultSet = statement.getResultSet();
                    if (resultSet.next()){
                        wins.set(resultSet.getInt("wins"));
                    }else{
                        Bukkit.getLogger().log(Level.SEVERE, "[Storage] Error retrieving player wins for: " + uuid);
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        });
        return wins.get();
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
