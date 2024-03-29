package me.noreach.uhcwars;

import me.noreach.uhcwars.chest.ChestFill;
import me.noreach.uhcwars.chest.ChestManager;
import me.noreach.uhcwars.commands.*;
import me.noreach.uhcwars.enums.GameState;
import me.noreach.uhcwars.enums.StateManager;
import me.noreach.uhcwars.game.BlockManager;
import me.noreach.uhcwars.game.GameManager;
import me.noreach.uhcwars.game.ModManager;
import me.noreach.uhcwars.listeners.*;
import me.noreach.uhcwars.locations.ObjectiveManager;
import me.noreach.uhcwars.locations.RegionManager;
import me.noreach.uhcwars.player.PlayerManager;
import me.noreach.uhcwars.player.SpectatorManager;
import me.noreach.uhcwars.player.UHCPlayer;
import me.noreach.uhcwars.storage.IDatabase;
import me.noreach.uhcwars.storage.MongoDataStore;
import me.noreach.uhcwars.storage.SQLDatastore;
import me.noreach.uhcwars.storage.YmlDatastore;
import me.noreach.uhcwars.teams.TeamManager;
import me.noreach.uhcwars.timers.PreGame;
import me.noreach.uhcwars.util.ConfigHandler;
import me.noreach.uhcwars.util.Invent;
import me.noreach.uhcwars.util.InventoryStringDeSerializer;
import me.noreach.uhcwars.util.References;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Iterator;
import java.util.UUID;
import java.util.logging.Level;

/**
 * Created by Ryan on 10/04/2017.
 */
public class UHCWars extends JavaPlugin {


    private References references;
    //private SQLHandler sqlHandler;
    private StateManager stateManager;
    private TeamManager teamManager;
    private GameManager gameManager;
    private Invent invent;
    private RegionManager regionManager;
    private ObjectiveManager objectiveManager;
    private ConfigHandler configHandler;
    private InventoryStringDeSerializer inventorySerializer;
    private PlayerManager playerManager;
    private ChestManager chestManager;
    private SpectatorManager spectatorManager;
    private BlockManager blockManager;
    private ChestFill chestFill;
    private ModManager modManager;
    private boolean stats = false;


    private IDatabase iDatabase;


    @Override
    public void onEnable() {
        getConfig();
        this.configHandler = new ConfigHandler(this);
        this.references = new References(this);
        this.references.loadValues();
        this.references.generateMessages();
        if (!getConfig().getBoolean("Settings.stats")){
            this.stats = false;
        }
        if (getConfig().getBoolean("Settings.stats")){
            this.stats = true;
        }
        if (getConfig().getString("Settings.storageType").equalsIgnoreCase("MySQL")){
            iDatabase = new SQLDatastore(this);
        }
        else if(getConfig().getString("Settings.storageType").equalsIgnoreCase("MongoDB")){
            iDatabase = new MongoDataStore(this);
        }
        else if (getConfig().getString("Settings.storageType").equalsIgnoreCase("YML")){
            iDatabase = new YmlDatastore(this);
        }
        else if (getConfig().getString("Settings.storageType").equalsIgnoreCase("None")){
            //REDUNDANT Just makes a data store
            iDatabase = new SQLDatastore(this);
        }
        else{
            Bukkit.getLogger().log(Level.SEVERE, "[Storage] Error you did not select a correct storage type! MySQL/MongoDB/None");
        }
     //   this.sqlHandler = new SQLHandler(this, getConfig().getString("Settings.SQL.ip"), getConfig().getString("Settings.SQL.database"), getConfig().getString("Settings.SQL.username"), getConfig().getString("Settings.SQL.password"));
        this.stateManager = new StateManager(this);
        this.teamManager = new TeamManager(this);
        this.objectiveManager = new ObjectiveManager(this);
        this.inventorySerializer = new InventoryStringDeSerializer(this);
        this.playerManager = new PlayerManager(this);
        this.chestManager = new ChestManager(this);
        this.stateManager.setGameState(GameState.LOBBY);
        this.gameManager = new GameManager(this);
        this.regionManager = new RegionManager(this);
        this.invent = new Invent(this);
        this.spectatorManager = new SpectatorManager(this);
        this.blockManager = new BlockManager(this);
        this.chestFill = new ChestFill(this);
        this.modManager = new ModManager(this);
        for (Chunk chunk : this.references.getGameWorld().getLoadedChunks()){
            chunk.unload();
        }

        Bukkit.getLogger().log(Level.INFO, "[Chunks] Successfully unloaded all gameworld chunks!");
        new PreGame(this).runTaskTimer(this, 0, 100L);
        registerCommands();
        registerEvents();
        generateWall();
    }

    @Override
    public void onDisable() {
        //this.sqlHandler.closeConnection();
        degenerateWall();
        for (UUID uuid : this.playerManager.getUhcPlayers().keySet()){
            UHCPlayer uhcPlayer = this.playerManager.getUhcPlayers().get(uuid);
            this.iDatabase.updatePlayer(uhcPlayer);
        }
        this.iDatabase.closeDataStore();
    }


    private void registerCommands() {
        getCommand("statsreset").setExecutor(new StatsResetCMD(this));
        getCommand("moderation").setExecutor(new ModerationCMD(this));
        getCommand("teamchat").setExecutor(new TeamChatCMD(this));
        getCommand("world").setExecutor(new WorldCMD(this));
        getCommand("region").setExecutor(new RegionCMD(this));
        getCommand("setlocation").setExecutor(new LocationCMD(this));
        getCommand("statistics").setExecutor(new StatsCMD(this));
        getCommand("kit").setExecutor(new KitCMD(this));
        getCommand("objective").setExecutor(new ObjectiveCMD(this));
    }

    private void registerEvents() {
        PluginManager plm = Bukkit.getPluginManager();
        plm.registerEvents(new ConsumeListener(this), this);
        plm.registerEvents(new SpectatorHandler(this), this);
        plm.registerEvents(new FoodListener(this), this);
        plm.registerEvents(new InteractListener(this), this);
        plm.registerEvents(new LoginHandler(this), this);
        plm.registerEvents(new DamageHandler(this), this);
        plm.registerEvents(new BlockHandler(this), this);
        plm.registerEvents(new InventoryListener(this), this);

    }

    public void generateWall() {
        Location corner1 = this.regionManager.getWallLocation().get(0);
        Location corner2 = this.regionManager.getWallLocation().get(1);
        corner1.getChunk().load();
        corner2.getChunk().load();
       /* Iterator blocks = this.gameManager.blocksFromTwoPoints(corner1, corner2).iterator();
        Bukkit.getLogger().log(Level.INFO, "[Wall] Generating using default method");
        Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
            @Override
            public void run() {
                if (blocks.hasNext()) {
                    Block block = (Block) blocks.next();
                    block.setType(references.getWallMaterial());
                    block.getState().update();
                }
            }
        }, 0L, 1L);
        */
        for (Block block : this.gameManager.blocksFromTwoPoints(corner1, corner2)) {
            block.setType(this.references.getWallMaterial());
        }
        Bukkit.getLogger().log(Level.INFO, "[Walls] Successfully generated the wall!");

    }



    public void degenerateWall() {

        Location corner1 = this.regionManager.getWallLocation().get(0);
        Location corner2 = this.regionManager.getWallLocation().get(1);
        /*Iterator iterator = this.gameManager.blocksFromTwoPoints(corner1, corner2).iterator();
        Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
            @Override
            public void run() {
                if (iterator.hasNext()){
                    Block block = (Block) iterator.next();
                    block.setType(Material.AIR);
                    block.getState().update();
                }
            }
        }, 0L, 1L);
        */
        for (Block block : this.gameManager.blocksFromTwoPoints(corner1, corner2)) {
            block.setType(Material.AIR);
            block.getState().update();
        }

        Bukkit.getLogger().log(Level.INFO, "[Walls] Successfully REMOVED the wall!");
    }




    public References getReferences() {
        return this.references;
    }

   // public SQLHandler getSqlHandler() {
   //     return this.sqlHandler;
    //}

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

    public ChestManager getChestManager() {
        return this.chestManager;
    }

    public SpectatorManager getSpectatorManager(){ return this.spectatorManager;}

    public BlockManager getBlockManager(){ return this.blockManager;}

    public ChestFill getChestFill(){ return this.chestFill;}

    public ModManager getModManager(){ return this.modManager;}

    public IDatabase getStorage(){ return this.iDatabase;}

    public boolean getStats(){ return this.stats;}

}
