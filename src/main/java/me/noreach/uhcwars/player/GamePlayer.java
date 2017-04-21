package me.noreach.uhcwars.player;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.util.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;

/**
 * Created by Ryan on 16/04/2017.
 */
public class GamePlayer {

    private UHCWars uhcWars;
    private UUID uuid;

    private Stat kills = new Stat(new ItemCreator(Material.DIAMOND_SWORD).setName(ChatColor.AQUA + "Kills").toItemStack(), 0);
    private Stat deaths = new Stat(new ItemCreator(Material.SKULL_ITEM).setName(ChatColor.AQUA + "Deaths").toItemStack(), 0);
    private Stat wins = new Stat(new ItemCreator(Material.PAPER).setName(ChatColor.AQUA + "Wins").toItemStack(), 0);

    public GamePlayer(UUID uuid, UHCWars uhcWars) {
        this.uuid = uuid;
        this.uhcWars = uhcWars;
    }


    public void getInformation() {
        if (this.uhcWars.getReferences().getStats()) {
            Bukkit.getScheduler().runTaskAsynchronously(this.uhcWars, new Runnable() {
                @Override
                public void run() {
                    Connection connection = uhcWars.getSqlHandler().getConnection();
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
                            kills.setAmount(resultSet.getInt("kills"));
                            deaths.setAmount(resultSet.getInt("deaths"));
                            wins.setAmount(resultSet.getInt("wins"));
                        } else {
                            Bukkit.getLogger().log(Level.INFO, "[USER] Creating player data for:" + uuid);
                            statement1 = connection.prepareStatement("INSERT INTO `uhcwars_stats` (uuid) VALUES (?);");
                            statement1.setString(1, uuid.toString());
                            statement1.executeUpdate();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }finally {
                        try{
                            if (statement != null){
                                statement.close();
                            }
                            if (statement1 != null){
                                statement1.close();
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
        }else{
            Bukkit.getLogger().log(Level.INFO, "[USER] Stats are not enabled");
        }
    }

    public synchronized void saveInformation() {
        if (this.uhcWars.getReferences().getStats()){
            try{
                Connection connection = this.uhcWars.getSqlHandler().getConnection();
                PreparedStatement statement = connection.prepareStatement("UPDATE `uhcwars_stats` SET `kills` = ?,  `deaths` = ?, `wins` = ? WHERE `uuid` = ?;");
                statement.setInt(1, this.kills.getAmount());
                statement.setInt(2, this.deaths.getAmount());
                statement.setInt(3, this.wins.getAmount());
                statement.setString(4, uuid.toString());
                statement.executeUpdate();
                statement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

    }


    public Inventory getCustomKit() {
        Inventory inventory = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = uhcWars.getSqlHandler().getConnection().prepareStatement("SELECT `inv` FROM `uhcwars_kits` WHERE `uuid` = ?;");
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

    public void saveCustomKit(Inventory inventory) {
        PreparedStatement statement = null;
        if (getCustomKit() == null) {
            createKit(this.uhcWars.getInventorySerializer().InventoryToString(inventory));
            return;
        }
        try {
            statement = this.uhcWars.getSqlHandler().getConnection().prepareStatement("UPDATE `uhcwars_kits` SET `inv` = ? WHERE `uuid` = ?;");
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

    private void createKit(String inventory) {
        PreparedStatement statement = null;
        try {
            statement = this.uhcWars.getSqlHandler().getConnection().prepareStatement("INSERT INTO `uhcwars_kits` (uuid, inv) VALUES (?, ?);");
            statement.setString(1, uuid.toString());
            statement.setString(2, inventory);
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

    public Inventory getStatsInventory(){
        Inventory inv = Bukkit.createInventory(null, 9 * 3, this.uhcWars.getReferences().getMainColor() + "Statistics: " + this.uhcWars.getReferences().getSecondaryColor() + Bukkit.getOfflinePlayer(uuid).getName());
        inv.clear();
        inv.setItem(0, kills.getItemStack());
        inv.setItem(1, deaths.getItemStack());
        inv.setItem(2, wins.getItemStack());
        return inv;
    }


    public Stat getKills(){ return this.kills;}

    public Stat getDeaths(){ return this.deaths;}

    public Stat getWins(){ return this.wins;}


}

