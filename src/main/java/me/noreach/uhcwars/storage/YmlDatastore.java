package me.noreach.uhcwars.storage;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.player.UHCPlayer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

/**
 * Created by Ryan on 30/04/2017.
 */
public class YmlDatastore extends IDatabase {

    private UHCWars uhcWars;

    public YmlDatastore(UHCWars uhcWars){
        this.uhcWars = uhcWars;

    }


    @Override
    public boolean initalize() {
        return false;
    }

    @Override
    public void createPlayer(UUID uuid) {
        File userData = new File(uhcWars.getDataFolder(), File.separator + "playerData");
        File userFile = new File(userData, File.separator + uuid + ".yml");
        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(userFile);
        try {
            if (!userFile.exists()) {
                playerConfig.createSection("Stats");
                playerConfig.set("Stats.kills", 0);
                playerConfig.set("Stats.deaths", 0);
                playerConfig.set("Stats.wins", 0);
                playerConfig.set("Stats.objectiveDmg", 0);
                playerConfig.save(userFile);
            }
        }catch (IOException e){
            e.printStackTrace();
            Bukkit.getLogger().log(Level.SEVERE, "[Storage] An IO Exception occured whilst trying to create the player: " + uuid);
        }
    }

    @Override
    public UHCPlayer getPlayer(UUID uuid) {
        UHCPlayer uhcPlayer = new UHCPlayer(uuid);
        Bukkit.getScheduler().runTaskAsynchronously(this.uhcWars, new Runnable() {
            @Override
            public void run() {
                File userData = new File(uhcWars.getDataFolder(), File.separator + "playerData");
                File userFile = new File(userData, File.separator + uuid + ".yml");
                FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(userFile);
                if (userFile.exists()) {
                    uhcPlayer.getKills().setAmount(playerConfig.getInt("Stats.kills"));
                    uhcPlayer.getDeaths().setAmount(playerConfig.getInt("Stats.deaths"));
                    uhcPlayer.getWins().setAmount(playerConfig.getInt("Stats.wins"));
                    uhcPlayer.getObjectiveDmg().setAmount(playerConfig.getInt("Stats.objectiveDmg"));
                } else {
                    createPlayer(uuid);
                    uhcPlayer.getKills().setAmount(0);
                    uhcPlayer.getDeaths().setAmount(0);
                    uhcPlayer.getWins().setAmount(0);
                }
                //USER KIT FILES
                File kitData = new File(uhcWars.getDataFolder(), File.separator + "playerKits");
                File kitFile = new File(kitData, File.separator + uuid + ".yml");
                FileConfiguration kitConfig = YamlConfiguration.loadConfiguration(kitFile);
                try {
                    if (kitFile.exists()) {
                        uhcPlayer.setSerialisedKit(kitConfig.getString("Kit.inv"));
                    }
                }catch (Exception e){
                    if (e instanceof NullPointerException){
                        Bukkit.getLogger().log(Level.INFO, "[Storage] Failed to find a kit for player: " + uuid);
                    }
                }
            }
        });
        return uhcPlayer;
    }

    @Override
    public void updatePlayer(UHCPlayer uhcPlayer) {
        UUID playerUUID = uhcPlayer.getUuid();
        int kills = uhcPlayer.getKills().getAmount();
        int deaths = uhcPlayer.getDeaths().getAmount();
        int wins = uhcPlayer.getWins().getAmount();
        File userData = new File(uhcWars.getDataFolder(), File.separator + "playerData");
        File userFile = new File(userData, File.separator + playerUUID + ".yml");
        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(userFile);
        try{
            if (userFile.exists()){
                playerConfig.set("Stats.kills", kills);
                playerConfig.set("Stats.deaths", deaths);
                playerConfig.set("Stats.wins", wins);
                playerConfig.set("Stats.objectiveDmg", uhcPlayer.getObjectiveDmg().getAmount());
                playerConfig.save(userFile);
                Bukkit.getLogger().log(Level.INFO, "[Storage] Successfully updated user file for player: " + playerUUID);
            }else{
                throw new IOException("[Storage] Tried to save to a file that did not exist!");
            }
        }catch (IOException e){
            e.printStackTrace();
            Bukkit.getLogger().log(Level.SEVERE, "[Storage] An IO Exception occured whilst trying to update player: " + playerUUID);
        }


        //USER KIT FILES
        File kitData = new File(uhcWars.getDataFolder(), File.separator + "playerKits");
        File kitFile = new File(kitData, File.separator + playerUUID + ".yml");
        FileConfiguration kitConfig = YamlConfiguration.loadConfiguration(kitFile);
        try{
            kitConfig.set("Kit.inv", uhcPlayer.getSerialisedKit());
            Bukkit.getLogger().log(Level.INFO, "[Storage] Successfully updated kit for player: " + uhcPlayer.getUuid());
            kitConfig.save(kitFile);

        }catch (Exception e){
            if (e instanceof NullPointerException){
                Bukkit.getLogger().log(Level.INFO, "[Storage] Could not find a custom kit whilst trying to update: " + playerUUID);
            }
        }
    }

    @Override
    public void closeDataStore() {

    }

    @Override
    public void scrubPlayer(UUID uuid) {
        UHCPlayer uhcPlayer = this.uhcWars.getPlayerManager().getUhcPlayers().get(uuid);
        File userData = new File(uhcWars.getDataFolder(), File.separator + "playerData");
        File userFile = new File(userData, File.separator + uuid + ".yml");
        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(userFile);
        try{
            if (userFile.exists()){
                playerConfig.set("Stats.kills", 0);
                playerConfig.set("Stats.deaths", 0);
                playerConfig.set("Stats.wins", 0);
                playerConfig.set("Stats.objectiveDmg", 0);
                uhcPlayer.getKills().setAmount(0);
                uhcPlayer.getDeaths().setAmount(0);
                uhcPlayer.getWins().setAmount(0);
                uhcPlayer.getObjectiveDmg().setAmount(0);
                playerConfig.save(userFile);
                Bukkit.getLogger().log(Level.INFO, "[Storage] Successfully scrubbed statistics for:" + uuid);
            }else{
                throw new IOException("[Storage] Uh oh this shouldn't of happened! Could not find any data for user: " + uuid);
            }
        }catch (IOException e){
            e.printStackTrace();
            Bukkit.getLogger().log(Level.SEVERE, "[Storage] An IO Exception occured whilst trying to update player: " + uuid);
        }

    }
}
