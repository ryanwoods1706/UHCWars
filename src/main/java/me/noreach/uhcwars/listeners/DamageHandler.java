package me.noreach.uhcwars.listeners;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.enums.GameState;
import me.noreach.uhcwars.player.GamePlayer;
import me.noreach.uhcwars.teams.Teams;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Created by Ryan on 18/04/2017.
 */
public class DamageHandler implements Listener {

    private UHCWars uhcWars;
    private ChatColor mainColor;

    public DamageHandler(UHCWars uhcWars){
        this.uhcWars = uhcWars;
        this.mainColor = this.uhcWars.getReferences().getMainColor();
    }

    @EventHandler
    public void onDmg(EntityDamageEvent e){
        if (e.getEntityType() == EntityType.PLAYER){
            Player pl = (Player) e.getEntity();
            if (this.uhcWars.getStateManager().getGameState() != GameState.INGAME){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEDmgByE(EntityDamageByEntityEvent e){
        if (e.getEntityType() == EntityType.PLAYER){
            if (e.getDamager().getType() == EntityType.PLAYER){
                Player victim = (Player) e.getEntity();
                Player damager = (Player) e.getDamager();
                if (this.uhcWars.getTeamManager().getTeam1().contains(victim) && this.uhcWars.getTeamManager().getTeam1().contains(damager)){
                    damager.sendMessage(this.uhcWars.getReferences().getPrefix() + mainColor + "You cannot damage your own teammate!");
                }
                if (this.uhcWars.getTeamManager().getTeam2().contains(victim) && this.uhcWars.getTeamManager().getTeam2().contains(damager)){
                    damager.sendMessage(this.uhcWars.getReferences().getPrefix() + mainColor + "You cannot damage your own teammate!");
                }
            }
        }
    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        Player pl = e.getEntity();
        if (e.getEntity().getKiller() != null){
            GamePlayer victimPlayer = this.uhcWars.getPlayerManager().getPlayerData().get(pl.getUniqueId());
            Player killer = e.getEntity().getKiller();
            GamePlayer killerPlayer = this.uhcWars.getPlayerManager().getPlayerData().get(killer.getUniqueId());
            int kills = this.uhcWars.getPlayerManager().getPlayerKills().get(killer.getUniqueId());
            kills +=1;
            this.uhcWars.getPlayerManager().getPlayerKills().put(killer.getUniqueId(), kills);
            killerPlayer.getKills().incrementValue();
            victimPlayer.getDeaths().incrementValue();
            this.uhcWars.getPlayerManager().getPlayerData().put(pl.getUniqueId(), victimPlayer);
            this.uhcWars.getPlayerManager().getPlayerData().put(killer.getUniqueId(), killerPlayer);
            Teams killerTeam = this.uhcWars.getTeamManager().getPlayerTeam(killer);
            int teamKills = this.uhcWars.getTeamManager().getTeamKills().get(killerTeam);
            teamKills +=1;
            this.uhcWars.getTeamManager().getTeamKills().put(killerTeam, teamKills);
        }
    }
}
