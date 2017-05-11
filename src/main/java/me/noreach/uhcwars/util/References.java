package me.noreach.uhcwars.util;

import me.noreach.uhcwars.UHCWars;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.logging.Level;

/**
 * Created by Ryan on 10/04/2017.
 */
public class References {

    private UHCWars uhcWars;
    private FileConfiguration config;


    private World spawnWorld;
    private World gameWorld;
    private Material wallMaterial;
    private int reqStart;
    private int maxSlots;
    private int timeLimit;
    private int wallDropTime;
    private int halfTime;
    private boolean goldenHeadsOnDeath;
    private int objectiveHealth;
    private int objectiveDmgPerHit;
    private int killsTillFill;

    private String prefix;
    private String sbTitle;
    private String sbIP;
    private ChatColor mainColor;
    private ChatColor secondaryColor;


    public References(UHCWars uhcWars) {
        this.uhcWars = uhcWars;
        uhcWars.reloadConfig();
        config = uhcWars.getConfig();


    }

    public void loadValues(){
        prefix = ChatColor.translateAlternateColorCodes('&', config.getString("Settings.Messages.prefix"));
        sbTitle = ChatColor.translateAlternateColorCodes('&', config.getString("Settings.Messages.scoreboardTitle"));
        sbIP = ChatColor.translateAlternateColorCodes('&', config.getString("Settings.Messages.scoreboardIP"));
        secondaryColor = ChatColor.valueOf(config.getString("Settings.Messages.secondaryColor"));
        mainColor = ChatColor.valueOf(config.getString("Settings.Messages.mainColor"));
        spawnWorld = new WorldCreator(config.getString("Settings.gameSettings.spawnWorld")).createWorld();
        gameWorld = new WorldCreator(config.getString("Settings.gameSettings.gameWorld")).createWorld();
        wallMaterial = Material.valueOf(config.getString("Settings.gameSettings.wallBlock"));
        reqStart = config.getInt("Settings.gameSettings.minStart");
        maxSlots = config.getInt("Settings.gameSettings.maxSlots");
        timeLimit = config.getInt("Settings.gameSettings.timeLimit");
        wallDropTime = config.getInt("Settings.gameSettings.wallDropTime");
        halfTime = config.getInt("Settings.gameSettings.halfTime");
        goldenHeadsOnDeath = config.getBoolean("Settings.gameSettings.goldenHeadOnDeath");
        objectiveHealth = config.getInt("Settings.gameSettings.objectiveHealth");
        objectiveDmgPerHit = config.getInt("Settings.gameSettings.objectiveDmgPerHit");
        killsTillFill = config.getInt("Settings.gameSettings.killsTillFill");
        Bukkit.getLogger().log(Level.INFO, "[CONFIG] Successfully loaded all values from the config");

        if (this.timeLimit < this.halfTime) {
            Bukkit.getLogger().log(Level.SEVERE, "[CONFIG ERROR] The timelimit must be greater than the half time value!");
            Bukkit.getServer().shutdown();
        }
        if (this.timeLimit < this.wallDropTime) {
            Bukkit.getLogger().log(Level.SEVERE, "[CONFIG ERROR] The timelimit must be greater than the wallDrop time!");
            Bukkit.getServer().shutdown();
        }
        if (this.spawnWorld == null || this.gameWorld == null) {
            Bukkit.getLogger().log(Level.SEVERE, "[CONFIG ERROR] Either the spawn or game world does not exist!");
            //TODO ADD BACK
            //Bukkit.getServer().shutdown();
        }
    }

    public World getSpawnWorld() {
        return this.spawnWorld;
    }

    public World getGameWorld() {
        return this.gameWorld;
    }

    public Material getWallMaterial() {
        return this.wallMaterial;
    }

    public int getReqStart() {
        return this.reqStart;
    }

    public int getMaxSlots() {
        return this.maxSlots;
    }

    public int getTimeLimit() {
        return this.timeLimit;
    }

    public int getWallDropTime() {
        return this.wallDropTime;
    }

    public int getHalfTime() {
        return this.halfTime;
    }

    public boolean isGoldenHeadsOnDeath() {
        return this.goldenHeadsOnDeath;
    }

    public int getObjectiveHealth() {
        return this.objectiveHealth;
    }

    public int getObjectiveDmgPerHit() {
        return this.objectiveDmgPerHit;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getSbTitle() {
        return this.sbTitle;
    }

    public String getSbIP() {
        return this.sbIP;
    }

    public ChatColor getMainColor() {
        return this.mainColor;
    }

    public ChatColor getSecondaryColor() {
        return this.secondaryColor;
    }


    public int getKillsTillFill(){ return this.killsTillFill;}


    private String noPerms;
    private String playerOnlyCmd;
    private String consoleOnlyCmd;
    private String statsDisabled;
    private String successKitSave;
    private String successObjectiveSave;
    private String successRegionSave;
    private String successTeam1SpawnSave;
    private String successTeam2SpawnSave;
    private String preGameStartingIn;
    private String gameStarting;
    private String gamePvP;
    private String halfTimeStarted;
    private String halfTimeEnded;
    private String team1Won;
    private String team2Won;
    private String gameTie;

    public String getNoPerms(){ return this.noPerms;}
    public String getPlayerOnlyCmd(){ return this.playerOnlyCmd;}
    public String getConsoleOnlyCmd(){ return this.consoleOnlyCmd;}
    public String getStatsDisabled(){ return this.statsDisabled;}
    public String getSuccessKitSave(){ return this.successKitSave;}
    public String getSuccessObjectiveSave(){ return this.successObjectiveSave;}
    public String getSuccessRegionSave(){ return this.successRegionSave;}
    public String getSuccessTeam1SpawnSave(){ return this.successTeam1SpawnSave;}
    public String getSuccessTeam2SpawnSave(){ return this.successTeam2SpawnSave;}
    public String getPreGameStartingIn(){ return this.preGameStartingIn;}
    public String getGameStarting(){ return this.gameStarting;}
    public String getGamePvP(){ return this.gamePvP;}
    public String getHalfTimeStarted(){ return this.halfTimeStarted;}
    public String getHalfTimeEnded(){ return this.halfTimeEnded;}
    public String getTeam1Won(){ return this.team1Won;}
    public String getTeam2Won(){ return this.team2Won;}
    public String getGameTie(){ return this.gameTie;}


    public void generateMessages(){
        File messagesFile = new File(this.uhcWars.getDataFolder() + File.separator + "messages.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(messagesFile);
        try{
            if (!messagesFile.exists()){
                String path = "Settings.messages";
                config.createSection(path);
                config.set(path + ".noperms", "&cYou do not have permission to execute this command");
                config.set(path + ".playerOnlyCmd", "&cThis command can only be used a player!");
                config.set(path + ".consoleOnlyCmd", "&cThis command can only be used by console");
                config.set(path + ".statsDisabled", "&cStats are currently disabled on this server");
                config.set(path + ".successKitSave", "&aSuccessfully saved your kit!");
                config.set(path + ".successObjectiveSave", "&aSuccessfully saved the objective");
                config.set(path + ".successRegionSave", "&aSuccessfully saved your region");
                config.set(path + ".successTeam1SpawnSave", "&aSuccessfully saved spawn point for Team_1");
                config.set(path + ".successTeam2SpawnSave", "&aSuccessfully saved spawn point for Team_2");
                config.set(path + ".pregameStartingIn", "&6 Starting game in {seconds} seconds!");
                config.set(path + ".gameStarting", "&6The game is now starting!");
                config.set(path + ".gamePvP", "&6PvP is now ENABLED!");
                config.set(path + ".halfTime", "&6Half time! All chests have been refilled! You have 2 minutes to collect items");
                config.set(path + ".halfTimeEnded", "&6Half time has now ended! May the odds be ever in your favour!");
                config.set(path + ".team1Won", "&7Team 1 has won the game with {health} health remaining!");
                config.set(path + ".team2Won", "&7Team 2 has won the game with {health} health remaining!");
                config.set(path + ".gameTie", "&7Its a tie! Both teams have the same health!");
                config.save(messagesFile);
            }
            this.noPerms = ChatColor.translateAlternateColorCodes('&', config.getString("Settings.messages.noperms"));
            this.playerOnlyCmd = ChatColor.translateAlternateColorCodes('&', config.getString("Settings.messages.playerOnlyCmd"));
            this.consoleOnlyCmd = ChatColor.translateAlternateColorCodes('&', config.getString("Settings.messages.consoleOnlyCmd"));
            this.statsDisabled = ChatColor.translateAlternateColorCodes('&', config.getString("Settings.messages.statsDisabled"));
            this.successKitSave = ChatColor.translateAlternateColorCodes('&', config.getString("Settings.messages.successKitSave"));
            this.successObjectiveSave = ChatColor.translateAlternateColorCodes('&', config.getString("Settings.messages.successObjectiveSave"));
            this.successRegionSave = ChatColor.translateAlternateColorCodes('&', config.getString("Settings.messages.successRegionSave"));
            this.successTeam1SpawnSave = ChatColor.translateAlternateColorCodes('&', config.getString("Settings.messages.successTeam1SpawnSave"));
            this.successTeam2SpawnSave = ChatColor.translateAlternateColorCodes('&', config.getString("Settings.messages.successTeam2SpawnSave"));
            this.preGameStartingIn = ChatColor.translateAlternateColorCodes('&', config.getString("Settings.messages.pregameStartingIn"));
            this.gameStarting = ChatColor.translateAlternateColorCodes('&', config.getString("Settings.messages.gameStarting"));
            this.gamePvP = ChatColor.translateAlternateColorCodes('&', config.getString("Settings.messages.gamePvP"));
            this.halfTimeStarted = ChatColor.translateAlternateColorCodes('&', config.getString("Settings.messages.halfTime"));
            this.halfTimeEnded = ChatColor.translateAlternateColorCodes('&', config.getString("Settings.messages.halfTimeEnded"));
            this.team1Won = ChatColor.translateAlternateColorCodes('&', config.getString("Settings.messages.team1Won"));
            this.team2Won = ChatColor.translateAlternateColorCodes('&', config.getString("Settings.messages.team2Won"));
            this.gameTie = ChatColor.translateAlternateColorCodes('&', config.getString("Settings.messages.gameTie"));
        }catch (Exception e){
            Bukkit.getLogger().log(Level.INFO, "[Message Config] An exception has occured: " + e);

        }

    }


}
