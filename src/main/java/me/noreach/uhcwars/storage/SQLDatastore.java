package me.noreach.uhcwars.storage;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.player.UHCPlayer;
import me.noreach.uhcwars.sql.StorageTypes;
import me.noreach.uhcwars.util.InventoryStringDeSerializer;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.sql.*;
import java.util.UUID;
import java.util.logging.Level;

/**
 * Created by NoReach_ on 27/04/2017.
 */
public class SQLDatastore extends IDatabase {


    //ON ASYNC JOIN GENERATE THE PLAYER DATA AND SET THE LOADOUT OBJECT IN THEIR UHCPLAYER CLASS
    //if result set is null, i.e no kit is found set it to the default inv
    private UHCWars uhcWars;
    private Connection connection;


    public SQLDatastore(UHCWars uhcWars) {
        this.uhcWars = uhcWars;
        this.initalize();
    }


    @Override
    public StorageTypes storageType() {
        return StorageTypes.MySQL;
    }

    @Override
    public boolean initalize() {
        if (!this.uhcWars.getStats()) {
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
    public void createPlayer(UUID uuid) {
        if (!this.uhcWars.getStats()) {
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(this.uhcWars, new Runnable() {
            @Override
            public void run() {
                PreparedStatement statement1 = null;
                try {
                    statement1 = connection.prepareStatement("INSERT INTO `uhcwars_stats` (uuid) VALUES (?);");
                    statement1.setString(1, uuid.toString());
                    statement1.executeUpdate();
                    Bukkit.getLogger().log(Level.INFO, "[Storage] Successfully created user: " + uuid);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public UHCPlayer getPlayer(UUID uuid) {
        UHCPlayer uhcPlayer = new UHCPlayer(uuid);
        if (!this.uhcWars.getStats()){
            return uhcPlayer;
        }
        Bukkit.getScheduler().runTaskAsynchronously(this.uhcWars, new Runnable() {
            @Override
            public void run() {
                    PreparedStatement statement = null;
                    ResultSet resultSet = null;
                    try{
                        statement = connection.prepareStatement("SELECT * FROM `uhcwars_stats` WHERE `uuid` = ?;");
                        statement.setString(1, uuid.toString());
                        statement.executeQuery();
                        resultSet = statement.getResultSet();
                        if (resultSet.next()){
                            uhcPlayer.getKills().setAmount(resultSet.getInt("kills"));
                            uhcPlayer.getDeaths().setAmount(resultSet.getInt("deaths"));
                            uhcPlayer.getWins().setAmount(resultSet.getInt("wins"));
                            Bukkit.getLogger().log(Level.INFO, "[Storage] Successfully retrieved UHCPlayer for: " + uuid);
                        }else{
                            createPlayer(uuid);
                            uhcPlayer.getKills().setAmount(0);
                            uhcPlayer.getDeaths().setAmount(0);
                            uhcPlayer.getWins().setAmount(0);
                        }

                    }catch (SQLException e){
                        e.printStackTrace();
                    }
            }
        });
        return uhcPlayer;
    }

    @Override
    public void updatePlayer(UHCPlayer uhcPlayer) {
        try{
            PreparedStatement statement = connection.prepareStatement("UPDATE `uhcwars_stats` SET `kills` = ?,  `deaths` = ?, `wins` = ? WHERE `uuid` = ?;");
            statement.setInt(1, uhcPlayer.getKills().getAmount());
            statement.setInt(2, uhcPlayer.getDeaths().getAmount());
            statement.setInt(3, uhcPlayer.getWins().getAmount());
            statement.setString(1, uhcPlayer.getUuid().toString());
            statement.executeUpdate();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
            Bukkit.getLogger().log(Level.SEVERE, "[Storage] Failed to update player: " + uhcPlayer.getUuid());
        }
    }


    @Override
    public void closeDataStore() {
        try{
            this.connection.close();
        }catch (SQLException e){
            e.printStackTrace();
            Bukkit.getLogger().log(Level.SEVERE, "[Storage] Could not close the SQL connection, please report this issue ASAP!");
        }
    }

    @Override
    public Inventory playerKit(UUID uuid) {
        Inventory inventory = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = this.connection.prepareStatement("SELECT `inv` FROM `uhcwars_kits` WHERE `uuid` = ?;");
            statement.setString(1, uuid.toString());
            statement.executeQuery();
            resultSet = statement.getResultSet();
            if (resultSet.next()) {
                inventory = this.uhcWars.getInventorySerializer().StringToInventory(resultSet.getString("inv"));
            } else {
                inventory = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
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
        return inventory;
    }

    @Override
    public void saveCustomKit(UUID uuid, Inventory inventory) {
        PreparedStatement statement = null;
        if (playerKit(uuid) == null) {
            createKit(uuid, inventory);
            return;
        }
        try {
            statement = this.connection.prepareStatement("UPDATE `uhcwars_kits` SET `inv` = ? WHERE `uuid` = ?;");
            statement.setString(1, this.uhcWars.getInventorySerializer().InventoryToString(inventory));
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (statement != null){
                    statement.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void createKit(UUID uuid, Inventory inventory) {
        PreparedStatement statement = null;
        try {
            statement = this.connection.prepareStatement("INSERT INTO `uhcwars_kits` (uuid, inv) VALUES (?, ?);");
            statement.setString(1, uuid.toString());
            statement.setString(2, this.uhcWars.getInventorySerializer().InventoryToString(inventory));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                assert (statement != null);
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
