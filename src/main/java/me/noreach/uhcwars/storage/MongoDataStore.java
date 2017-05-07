package me.noreach.uhcwars.storage;

import com.mongodb.*;
import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.player.UHCPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
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
    public boolean initalize() {
        if (!this.uhcWars.getStats()){
            return true;
        }
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
        dbObject.put("objectiveDmg", 0);
        this.collection.insert(dbObject);
    }

    @Override
    public UHCPlayer getPlayer(UUID uuid) {
        UHCPlayer uhcPlayer = new UHCPlayer(uuid);
        if (!this.uhcWars.getStats()){
            uhcPlayer.getKills().setAmount(0);
            uhcPlayer.getDeaths().setAmount(0);
            uhcPlayer.getWins().setAmount(0);
            return uhcPlayer;
        }
        DBObject dbObject = new BasicDBObject("uuid", uuid);
        DBObject found = collection.findOne(dbObject);
        DBObject foundKit = playerKitsCol.findOne(dbObject);
        if (found == null){
            createPlayer(uuid);
            uhcPlayer.getKills().setAmount(0);
            uhcPlayer.getDeaths().setAmount(0);
            uhcPlayer.getWins().setAmount(0);
            if (foundKit != null){
                uhcPlayer.setSerialisedKit((String) foundKit.get("kit"));
            }
            Bukkit.getLogger().log(Level.INFO, "[Storage] Successfully created and retrieved: " + uhcPlayer.getUuid());
            return uhcPlayer;
        }
        if (foundKit != null){
            uhcPlayer.setSerialisedKit((String) foundKit.get("kit"));
        }
        uhcPlayer.getKills().setAmount((int) found.get("kills"));
        uhcPlayer.getDeaths().setAmount((int) found.get("deaths"));
        uhcPlayer.getWins().setAmount((int) found.get("wins"));
        uhcPlayer.getObjectiveDmg().setAmount((int) found.get("objectiveDmg"));

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
        DBObject foundKit = this.playerKitsCol.findOne(dbObject);
        if (found == null){
            createPlayer(uhcPlayer.getUuid());
            return;
        }
        if (uhcPlayer.getSerialisedKit() != null){
            DBObject replacement = new BasicDBObject("uuid", uhcPlayer.getUuid());
            replacement.put("kit", uhcPlayer.getSerialisedKit());
            if (foundKit != null) {
                this.playerKitsCol.update(foundKit, replacement);
            }else{
                this.playerKitsCol.insert(replacement);
            }
            Bukkit.getLogger().log(Level.INFO, "[Storage] Successfully updated player kit for: " + uhcPlayer.getUuid());
        }
        DBObject replacement = new BasicDBObject("uuid", uhcPlayer.getUuid());
        replacement.put("kills", uhcPlayer.getKills().getAmount());
        replacement.put("deaths", uhcPlayer.getDeaths().getAmount());
        replacement.put("wins", uhcPlayer.getWins().getAmount());
        replacement.put("objectiveDmg", uhcPlayer.getObjectiveDmg().getAmount());
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
    public void scrubPlayer(UUID uuid) {
        if (!this.uhcWars.getStats()){
            return;
        }
        Player player = Bukkit.getPlayer(uuid);
        UHCPlayer uhcPlayer = this.uhcWars.getPlayerManager().getUhcPlayers().get(uuid);
        DBObject dbObject = new BasicDBObject("uuid", uuid);
        DBObject found = collection.findOne(dbObject);
        if (found != null){
            collection.remove(found);
            createPlayer(uuid);
            uhcPlayer.getKills().setAmount(0);
            uhcPlayer.getWins().setAmount(0);
            uhcPlayer.getDeaths().setAmount(0);
            uhcPlayer.getObjectiveDmg().setAmount(0);
            Bukkit.getLogger().log(Level.INFO, "[Storage] Successfully scrubbed all statistics for player: " + uuid);
            if (player.isOnline()) {
                player.sendMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.GREEN + "Successfully reset all your statistics!");
            }
        }else{
            Bukkit.getLogger().log(Level.SEVERE, "[Storage] Could not find any player data for: " + uuid);
        }

    }
}
