package me.noreach.uhcwars.sql;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.player.Stat;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

/**
 * Created by Ryan on 16/04/2017.
 */
public class SQLHandler {

    private UHCWars uhcWars;


    private Connection connection;
    private String ip;
    private String database;
    private String username;
    private String password;


    public SQLHandler(UHCWars uhcWars, String ip, String database, String username, String password){
        this.uhcWars = uhcWars;
        this.uhcWars.reloadConfig();
        this.ip = ip; this.database = database; this.username = username; this.password = password;
        openConnection();
    }

    private void openConnection(){
        if (!this.uhcWars.getConfig().getBoolean("Settings.SQL.stats")){
            Bukkit.getLogger().log(Level.INFO, "[SQL] Running plugin without statistics");
            return;
        }
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + database, username, password);
            Bukkit.getLogger().info("[SQL] Connected");
            createTables();
        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getLogger().info("[SQL] Failed to connect!");
        }
    }

    public void closeConnection(){
        try {
            if (!this.uhcWars.getConfig().getBoolean("Settings.SQL.stats")){
                return;
            }
            if (!this.connection.isClosed()) {
                this.connection.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        try {
            if (this.connection.isClosed()) {
                openConnection();
                return this.connection;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return this.connection;
    }

    private void createTables(){
        try {
            Statement statement = this.connection.createStatement();
            Statement statement1 = this.connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `uhcwars_stats` (`uuid` VARCHAR(60), `kills` INT NOT NULL DEFAULT '0', `deaths` INT NOT NULL DEFAULT '0', `wins` INT NOT NULL DEFAULT '0');");
            statement1.executeUpdate("CREATE TABLE IF NOT EXISTS `uhcwars_kits` (`uuid` VARCHAR(60), `inv` VARCHAR(999));");
            statement.close();
            statement1.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
