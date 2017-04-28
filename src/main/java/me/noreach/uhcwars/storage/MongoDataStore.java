package me.noreach.uhcwars.storage;

import com.mongodb.*;
import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.player.UHCPlayer;
import me.noreach.uhcwars.sql.StorageTypes;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

/**
 * Created by NoReach_ on 27/04/2017.
 */
public class MongoDataStore extends IDatabase {

    private UHCWars uhcWars;

    private DBCollection playerKitsCol;
    private DBCollection collection;
    private DB database;
    private MongoClient client;

    public MongoDataStore(UHCWars uhcWars) {
        this.uhcWars = uhcWars;
        this.initalize();
    }

    @Override
    public StorageTypes storageType() {
        return StorageTypes.MongoDB;
    }

    @Override
    public boolean initalize() {
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
            this.collection = this.database.getCollection("uhcwars_stats");
            this.playerKitsCol = this.database.getCollection("uhcwars_kits");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getLogger().log(Level.SEVERE, "[Storage] Could not connect to the MongoDB Server/Database");
        }
        return false;
    }

    @Override
    public void createPlayer(UUID uuid) {
        if (!this.uhcWars.getStats()){
            return;
        }
        DBObject dbObject = new BasicDBObject("uuid", uuid);
        dbObject.put("kills", 0);
        dbObject.put("deaths", 0);
        dbObject.put("wins", 0);
        this.collection.insert(dbObject);
    }

    @Override
    public UHCPlayer getPlayer(UUID uuid) {
        UHCPlayer uhcPlayer = new UHCPlayer(uuid);
        if (!this.uhcWars.getStats()){
            return uhcPlayer;
        }
        DBObject dbObject = new BasicDBObject("uuid", uuid);
        DBObject found = collection.findOne(dbObject);
        if (found == null){
            createPlayer(uuid);
            found = collection.findOne(dbObject);
        }
        uhcPlayer.getKills().setAmount((int) found.get("kills"));
        uhcPlayer.getDeaths().setAmount((int) found.get("deaths"));
        uhcPlayer.getWins().setAmount((int) found.get("wins"));
        Bukkit.getLogger().log(Level.INFO, "[Storage] Successfully retrieved player info for: " + uhcPlayer.getUuid());

        return uhcPlayer;
    }

    @Override
    public void updatePlayer(UHCPlayer uhcPlayer) {
        if (!this.uhcWars.getStats()){
            return;
        }
        DBObject dbObject = new BasicDBObject("uuid", uhcPlayer.getUuid());
        DBObject found = this.collection.findOne(dbObject);
        if (found == null){
            createPlayer(uhcPlayer.getUuid());
            return;
        }
        DBObject replacement = new BasicDBObject("uuid", uhcPlayer.getUuid());
        replacement.put("kills", uhcPlayer.getKills().getAmount());
        replacement.put("deaths", uhcPlayer.getDeaths().getAmount());
        replacement.put("wins", uhcPlayer.getWins().getAmount());
        this.collection.update(found, replacement);
        Bukkit.getLogger().log(Level.INFO, "[Storage] Successfully updated player info for: " + uhcPlayer.getUuid());
    }

    @Override
    public void closeDataStore() {
        if (!this.uhcWars.getStats()){
            return;
        }
        this.client.close();
        Bukkit.getLogger().log(Level.INFO, "[Storage] Successfully closed the MongoDB Client!");

    }

    @Override
    public Inventory playerKit(UUID uuid) {
        DBObject dbObject1 = new BasicDBObject("uuid", uuid);
        DBObject found1 = playerKitsCol.findOne(dbObject1);
        if (found1 == null){
            Bukkit.getLogger().log(Level.INFO, "[Storage] Could not find a player kit for: " + uuid);
            return null;
        }
        Inventory inv = this.uhcWars.getInventorySerializer().StringToInventory((String) found1.get("kit"));
        Bukkit.getLogger().log(Level.INFO, "[Storage] Successfully retrieved the kit for: " + uuid);
        return inv;
    }

    @Override
    public void saveCustomKit(UUID uuid, Inventory inventory) {
        DBObject dbObject = new BasicDBObject("uuid", uuid);
        DBObject found = playerKitsCol.findOne(dbObject);
        if (found == null){
            createKit(uuid, inventory);
            return;
        }
        DBObject replacement = new BasicDBObject("uuid", uuid);
        replacement.put("kit", this.uhcWars.getInventorySerializer().InventoryToString(inventory));
        this.playerKitsCol.update(found, replacement);
        Bukkit.getLogger().log(Level.INFO, "[Storage] Successfully updated and saved kit for: " + uuid);
    }

    @Override
    public void createKit(UUID uuid, Inventory inventory) {
        DBObject dbObject = new BasicDBObject("uuid", uuid);
        DBObject found = playerKitsCol.findOne(dbObject);
        if (found != null){
            saveCustomKit(uuid, inventory);
            return;
        }
        dbObject.put("kit", this.uhcWars.getInventorySerializer().InventoryToString(inventory));
        this.playerKitsCol.insert(dbObject);
        Bukkit.getLogger().log(Level.INFO, "[Storage] Successfully created player kit for: " + uuid);
    }
}
