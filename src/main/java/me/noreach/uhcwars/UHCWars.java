package me.noreach.uhcwars;

import me.noreach.uhcwars.chest.ChestManager;
import me.noreach.uhcwars.chest.Fill;
import me.noreach.uhcwars.commands.LocationCMD;
import me.noreach.uhcwars.commands.ObjectiveCMD;
import me.noreach.uhcwars.commands.RegionCMD;
import me.noreach.uhcwars.commands.TestCMD;
import me.noreach.uhcwars.enums.GameState;
import me.noreach.uhcwars.enums.StateManager;
import me.noreach.uhcwars.game.GameManager;
import me.noreach.uhcwars.listeners.*;
import me.noreach.uhcwars.player.PlayerManager;
import me.noreach.uhcwars.teams.Teams;
import me.noreach.uhcwars.util.Invent;
import me.noreach.uhcwars.locations.ObjectiveManager;
import me.noreach.uhcwars.locations.RegionManager;
import me.noreach.uhcwars.sql.SQLHandler;
import me.noreach.uhcwars.teams.TeamManager;
import me.noreach.uhcwars.timers.PreGame;
import me.noreach.uhcwars.util.ConfigHandler;
import me.noreach.uhcwars.util.InventoryStringDeSerializer;
import me.noreach.uhcwars.util.References;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.JedisPool;

import java.util.logging.Level;

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
    private ConfigHandler configHandler;
    private InventoryStringDeSerializer inventorySerializer;
    private PlayerManager playerManager;
    private Fill fill;
    private ChestManager chestManager;

    @Override
    public void onEnable() {
        getConfig();
        this.configHandler = new ConfigHandler(this);
        this.references = new References(this);
        this.references.loadValues();
        this.sqlHandler = new SQLHandler(this, getConfig().getString("Settings.SQL.ip"), getConfig().getString("Settings.SQL.database"), getConfig().getString("Settings.SQL.username"), getConfig().getString("Settings.SQL.password"));
        this.stateManager = new StateManager(this);
        this.teamManager = new TeamManager(this);
        this.objectiveManager = new ObjectiveManager(this);
        this.inventorySerializer = new InventoryStringDeSerializer(this);
        this.playerManager = new PlayerManager(this);
        this.fill = new Fill(this);
        this.chestManager = new ChestManager(this);
        this.stateManager.setGameState(GameState.LOBBY);
        this.gameManager = new GameManager(this);
        this.regionManager = new RegionManager(this);
        this.invent = new Invent(this);
        new PreGame(this).runTaskTimer(this, 0, 100L);


        registerCommands();
        registerEvents();
        generateWall();
    }

    @Override
    public void onDisable() {
        this.sqlHandler.closeConnection();
        degenerateWall();
    }


    private void registerCommands() {
        getCommand("test").setExecutor(new TestCMD());
        getCommand("region").setExecutor(new RegionCMD(this));
        getCommand("setlocation").setExecutor(new LocationCMD(this));
        getCommand("objective").setExecutor(new ObjectiveCMD(this));
    }

    private void registerEvents() {
        PluginManager plm = Bukkit.getPluginManager();
        plm.registerEvents(new FoodListener(this), this);
        plm.registerEvents(new InteractListener(this), this);
        plm.registerEvents(new LoginHandler(this), this);
        plm.registerEvents(new DamageHandler(this), this);
        plm.registerEvents(new BlockHandler(this), this);

    }

    public void generateWall() {
        Location corner1 = this.regionManager.getWallLocation().get(0);
        Location corner2 = this.regionManager.getWallLocation().get(1);
        for (Block block : this.gameManager.blocksFromTwoPoints(corner1, corner2)) {
            block.setType(this.references.getWallMaterial());
            block.getState().update();
        }
        Bukkit.getLogger().log(Level.INFO, "[Walls] Successfully generated the wall!");
    }

    public void degenerateWall() {
        Location corner1 = this.regionManager.getWallLocation().get(0);
        Location corner2 = this.regionManager.getWallLocation().get(1);
        for (Block block : this.gameManager.blocksFromTwoPoints(corner1, corner2)) {
            block.setType(Material.AIR);
            block.getState().update();
        }
        Bukkit.getLogger().log(Level.INFO, "[Walls] Successfully REMOVED the wall!");
    }


    public References getReferences() {
        return this.references;
    }

    public SQLHandler getSqlHandler() {
        return this.sqlHandler;
    }

    public StateManager getStateManager() {
        return this.stateManager;
    }

    public TeamManager getTeamManager() {
        return this.teamManager;
    }

    public GameManager getGameManager() {
        return this.gameManager;
    }

    public Invent getInvent() {
        return this.invent;
    }

    public RegionManager getRegionManager() {
        return this.regionManager;
    }

    public ObjectiveManager getObjectiveManager() {
        return this.objectiveManager;
    }

    public ConfigHandler getConfigHandler() {
        return this.configHandler;
    }

    public InventoryStringDeSerializer getInventorySerializer() {
        return this.inventorySerializer;
    }

    public PlayerManager getPlayerManager() {
        return this.playerManager;
    }

    public Fill getFill() {
        return this.fill;
    }

    public ChestManager getChestManager() {
        return this.chestManager;
    }

}
