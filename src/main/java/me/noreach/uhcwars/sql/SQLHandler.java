package me.noreach.uhcwars.sql;

import me.noreach.uhcwars.UHCWars;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
        }
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + database, username, password);
            Bukkit.getLogger().info("[SQL] Connected");
        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getLogger().info("[SQL] Failed to connect!");
        }
    }

    public void closeConnection(){
        try {
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
}
