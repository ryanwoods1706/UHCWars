package me.noreach.uhcwars.listeners;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.enums.GameState;
import me.noreach.uhcwars.player.UHCPlayer;
import me.noreach.uhcwars.teams.Teams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
    public void onJoin(PlayerJoinEvent e) {
        Player pl = e.getPlayer();
        pl.setFoodLevel(20);
        pl.setHealth(pl.getMaxHealth());
        if (this.uhcWars.getStateManager().getGameState() == GameState.LOBBY) {
            pl.teleport(this.uhcWars.getReferences().getSpawnWorld().getSpawnLocation());
            this.uhcWars.getInvent().giveJoinInventory(pl);
        }
        else if (this.uhcWars.getStateManager().getGameState() == GameState.INGAME){
            if (pl.hasPermission("uhcwars.spectate")){
                this.uhcWars.getSpectatorManager().addSpectator(pl);
            }else{
                pl.kickPlayer(ChatColor.RED + "Uh oh, you shouldn't of made it this far!");
            }
        }
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e){
        Player pl = e.getPlayer();
        if (this.uhcWars.getStateManager().getGameState() == GameState.INGAME){
            if (!pl.hasPermission("uhcwars.spectate")){
                e.setKickMessage(this.uhcWars.getReferences().getPrefix() + ChatColor.RED + "You do not have permission to spectate games!");
                e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
                if (this.uhcWars.getPlayerManager().getUhcPlayers().containsKey(e.getPlayer().getUniqueId())) {
                    this.uhcWars.getPlayerManager().getUhcPlayers().remove(e.getPlayer().getUniqueId());
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
        UHCPlayer uhcPlayer = this.uhcWars.getStorage().getPlayer(e.getUniqueId());
        this.uhcWars.getPlayerManager().getUhcPlayers().put(e.getUniqueId(), uhcPlayer);





    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent e){
        Player pl = e.getPlayer();
        if (this.uhcWars.getSpectatorManager().getSpectators().contains(pl.getUniqueId())){
            this.uhcWars.getSpectatorManager().getSpectators().remove(pl.getUniqueId());
        }
        if (this.uhcWars.getModManager().getActiveModerators().contains(pl.getUniqueId())){
            this.uhcWars.getModManager().getActiveModerators().remove(pl.getUniqueId());
        }
        UHCPlayer gamePlayer = this.uhcWars.getPlayerManager().getUhcPlayers().get(pl.getUniqueId());
        this.uhcWars.getStorage().updatePlayer(gamePlayer);
        this.uhcWars.getPlayerManager().getUhcPlayers().remove(pl.getUniqueId());
        if (this.uhcWars.getStateManager().getGameState() == GameState.INGAME) {
            Teams teams = this.uhcWars.getTeamManager().getPlayerTeam(pl);
            switch (teams) {
                case Team_1:
                    this.uhcWars.getTeamManager().getTeam1().remove(pl);
                    break;
                case Team_2:
                    this.uhcWars.getTeamManager().getTeam2().remove(pl);
                    break;
            }
        }
        if (this.uhcWars.getTeamManager().getTeam1().size() == 0 && this.uhcWars.getTeamManager().getTeam2().size() > 0){
            this.uhcWars.getGameManager().endGame();
        }
        if (this.uhcWars.getTeamManager().getTeam2().size() == 0 && this.uhcWars.getTeamManager().getTeam1().size() > 0){
            this.uhcWars.getGameManager().endGame();
        }
    }
}
