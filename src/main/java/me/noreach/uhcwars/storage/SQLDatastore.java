package me.noreach.uhcwars.storage;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.player.UHCPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
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
            statement1.executeUpdate("CREATE TABLE IF NOT EXISTS `uhcwars_kits` (`uuid` VARCHAR(60), `inv` TEXT NOT NULL);");
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
                    PreparedStatement statement1 = null;
                    ResultSet resultSet1 = null;
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
                        statement1 = connection.prepareStatement("SELECT * FROM `uhcwars_kits` WHERE `uuid` = ?;");
                        statement1.setString(1, uuid.toString());
                        statement1.executeQuery();
                        resultSet1 = statement1.getResultSet();
                        if (resultSet1.next()){
                            uhcPlayer.setSerialisedKit(resultSet1.getString("inv"));
                        }
                    }catch (SQLException e){
                        e.printStackTrace();
                    }finally {
                        try {
                            if (statement != null){
                                statement.close();
                            }
                            if (statement1 != null){
                                statement1.close();
                            }
                            if (resultSet != null){
                                resultSet.close();
                            }
                            if (resultSet1 != null){
                                resultSet1.close();
                            }
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
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

            if (uhcPlayer.getSerialisedKit() != null) {
                PreparedStatement statement1 = connection.prepareStatement("UPDATE `uhcwars_kits` SET `inv` = ? WHERE `uuid` = ?;");
                statement1.setString(1, uhcPlayer.getSerialisedKit());
                statement1.setString(2, uhcPlayer.getUuid().toString());
                statement1.executeUpdate();
            }

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
    public void scrubPlayer(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        UHCPlayer uhcPlayer = this.uhcWars.getPlayerManager().getUhcPlayers().get(uuid);
        PreparedStatement statement = null;
        try{
            statement = this.connection.prepareStatement("DELETE FROM `uhcwars_stats` WHERE `uuid` = ?;");
            statement.setString(1, uuid.toString());
            statement.executeUpdate();
            createPlayer(uuid);
            uhcPlayer.getKills().setAmount(0);
            uhcPlayer.getWins().setAmount(0);
            uhcPlayer.getDeaths().setAmount(0);
            Bukkit.getLogger().log(Level.INFO, "[Storage] Successfully scrubbed all data from player: " + uuid);
            if (player.isOnline()) {
                player.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.GREEN + "Successfully reset all your statistics!");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

}
