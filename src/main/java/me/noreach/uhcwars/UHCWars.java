package me.noreach.uhcwars;

import me.noreach.uhcwars.commands.LocationCMD;
import me.noreach.uhcwars.commands.RegionCMD;
import me.noreach.uhcwars.enums.StateManager;
import me.noreach.uhcwars.game.GameManager;
import me.noreach.uhcwars.inventories.Invent;
import me.noreach.uhcwars.listeners.InteractListener;
import me.noreach.uhcwars.locations.ObjectiveManager;
import me.noreach.uhcwars.locations.RegionManager;
import me.noreach.uhcwars.sql.SQLHandler;
import me.noreach.uhcwars.teams.TeamManager;
import me.noreach.uhcwars.timers.PreGame;
import me.noreach.uhcwars.util.References;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Ref;

/**
 * Created by Ryan on 10/04/2017.
 */
public class UHCWars extends JavaPlugin {


    private References references;
    private SQLHandler sqlHandler;
    private StateManager stateManager;
    private TeamManager teamManager;
    private GameManager gameManager;
    private Invent invent;
    private RegionManager regionManager;
    private ObjectiveManager objectiveManager;


    @Override
    public void onEnable() {
        getConfig();
        this.references = new References(this);
        this.sqlHandler = new SQLHandler(this, getConfig().getString("Settings.SQL.ip"), getConfig().getString("Settings.SQL.database"), getConfig().getString("Settings.SQL.username"), getConfig().getString("Settings.SQL.password"));
        this.stateManager = new StateManager(this);
        this.teamManager = new TeamManager(this);
        this.gameManager = new GameManager(this);
        this.invent = new Invent(this);
        this.regionManager = new RegionManager(this);
        this.objectiveManager = new ObjectiveManager(this);
        new PreGame(this).runTaskTimer(this, 0, 20L);


        registerEvents();
        registerCommands();
    }

    @Override
    public void onDisable() {
        this.sqlHandler.closeConnection();
    }


    private void registerCommands() {
        getCommand("region").setExecutor(new RegionCMD(this));
        getCommand("setlocation").setExecutor(new LocationCMD(this));
    }

    private void registerEvents() {
        PluginManager plm = Bukkit.getPluginManager();
        plm.registerEvents(new InteractListener(this), this);

    }


    public References getReferences(){
        return this.references;
    }
    public SQLHandler getSqlHandler(){
        return this.sqlHandler;
    }
    public StateManager getStateManager(){
        return this.stateManager;
    }
    public TeamManager getTeamManager(){ return this.teamManager;}
    public GameManager getGameManager(){ return this.gameManager;}
    public Invent getInvent(){ return this.invent;}
    public RegionManager getRegionManager(){ return this.regionManager;}
    public ObjectiveManager getObjectiveManager(){ return this.objectiveManager;}
}
