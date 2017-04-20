package me.noreach.uhcwars.listeners;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.enums.GameState;
import me.noreach.uhcwars.player.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Ryan on 17/04/2017.
 */
public class LoginHandler implements Listener {

    private UHCWars uhcWars;

    public LoginHandler(UHCWars uhcWars){
        this.uhcWars = uhcWars;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player pl = e.getPlayer();
        pl.teleport(this.uhcWars.getReferences().getSpawnWorld().getSpawnLocation());
        this.uhcWars.getInvent().giveJoinInventory(pl);
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e){
        Player pl = e.getPlayer();
        if (this.uhcWars.getStateManager().getGameState() == GameState.INGAME){
            if (!pl.hasPermission("uhcwars.spectate")){
                e.setKickMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "You do not have permission to spectate games!");
                e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
                if (this.uhcWars.getPlayerManager().getPlayerData().containsKey(e.getPlayer().getUniqueId())) {
                    this.uhcWars.getPlayerManager().getPlayerData().remove(e.getPlayer().getUniqueId());
                }
            }
        }
    }

    @EventHandler
    public void onAsync(AsyncPlayerPreLoginEvent e){
        if (this.uhcWars.getStateManager().getGameState() == GameState.LOBBY){
            if (Bukkit.getServer().getOnlinePlayers().size() >= this.uhcWars.getReferences().getMaxSlots()){
                e.setKickMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "You cannot join full games!");
                e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
                return;
            }
        }
        GamePlayer gamePlayer = new GamePlayer(e.getUniqueId(), uhcWars);
        gamePlayer.getInformation();
        this.uhcWars.getPlayerManager().getPlayerData().put(e.getUniqueId(), gamePlayer);
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent e){
        Player pl = e.getPlayer();
        GamePlayer gamePlayer = this.uhcWars.getPlayerManager().getPlayerData().get(pl.getUniqueId());
        gamePlayer.saveInformation();
        this.uhcWars.getPlayerManager().getPlayerData().remove(pl.getUniqueId());
    }
}
