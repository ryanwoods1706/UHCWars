package me.noreach.uhcwars.util;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.player.GamePlayer;
import me.noreach.uhcwars.teams.Teams;
import me.noreach.uhcwars.util.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * Created by Ryan on 17/04/2017.
 */
public class Invent {

    private UHCWars uhcWars;
    private Inventory teamInventory;

    public Invent(UHCWars uhcWars) {
        this.uhcWars = uhcWars;
        this.teamInventory = Bukkit.createInventory(null, 27, this.uhcWars.getReferences().getMainColor() + "Select a Team!");
    }


    public void giveJoinInventory(Player player) {
        player.setHealth(player.getMaxHealth());
        player.setFlying(false);
        player.setGameMode(GameMode.SURVIVAL);
        PlayerInventory pi = player.getInventory();
        pi.clear();
        pi.setArmorContents(null);
        pi.setItem(0, new ItemCreator(Material.PAPER).setName(this.uhcWars.getReferences().getMainColor() + "Team Chooser").toItemStack());
        pi.setItem(8, new ItemCreator(Material.CHEST).setName(this.uhcWars.getReferences().getMainColor() + "Kit Editor").toItemStack());
        player.updateInventory();
        ;
    }

    public void giveRegionInv(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.getInventory().setItem(0, new ItemCreator(Material.GOLD_AXE).setName(this.uhcWars.getReferences().getMainColor() + "Region Maker").toItemStack());
        player.updateInventory();
    }

    public void giveObjectiveInv(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.getInventory().setItem(0, new ItemCreator(Material.GOLD_AXE).setName(this.uhcWars.getReferences().getMainColor() + "Objective Maker").toItemStack());
        player.updateInventory();
    }

    public void giveItems(Player player, boolean bool) {
        PlayerInventory pi = player.getInventory();
        pi.clear();
        GamePlayer gamePlayer = this.uhcWars.getPlayerManager().getPlayerData().get(player.getUniqueId());
        if (gamePlayer.getCustomKit() != null) {
            pi.setContents(gamePlayer.getCustomKit().getContents());
        } else {
            pi.setItem(0, new ItemCreator(Material.IRON_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 2).toItemStack());
            pi.setItem(1, new ItemCreator(Material.FISHING_ROD).toItemStack());
            pi.setItem(2, new ItemCreator(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 3).toItemStack());
            pi.setItem(3, new ItemCreator(Material.GOLDEN_APPLE).setName(ChatColor.GOLD + "Golden Head").setAmount(3).toItemStack());
            pi.setItem(4, new ItemCreator(Material.GOLDEN_APPLE).setAmount(6).toItemStack());
        }
        if (bool) {
            giveDefaultGameArmour(player);
        }
        player.updateInventory();
    }

    public void giveDefaultItems(Player player) {
        PlayerInventory pi = player.getInventory();
        pi.clear();
        pi.setItem(0, new ItemCreator(Material.IRON_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 2).toItemStack());
        pi.setItem(1, new ItemCreator(Material.FISHING_ROD).toItemStack());
        pi.setItem(2, new ItemCreator(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 3).toItemStack());
        pi.setItem(3, new ItemCreator(Material.GOLDEN_APPLE).setName(ChatColor.GOLD + "Golden Head").setAmount(3).toItemStack());
        pi.setItem(4, new ItemCreator(Material.GOLDEN_APPLE).setAmount(6).toItemStack());
        player.updateInventory();
    }


    public void giveDefaultGameArmour(Player player) {
        PlayerInventory pi = player.getInventory();
        pi.setHelmet(new ItemCreator(Material.DIAMOND_HELMET).addEnchant(Enchantment.PROTECTION_PROJECTILE, 1).toItemStack());
        pi.setChestplate(new ItemCreator(Material.IRON_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack());
        pi.setLeggings(new ItemCreator(Material.DIAMOND_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack());
        pi.setBoots(new ItemCreator(Material.IRON_BOOTS).addEnchant(Enchantment.PROTECTION_PROJECTILE, 1).toItemStack());
    }

    public Inventory getTeamInventory() {
        this.teamInventory.setItem(0, new ItemCreator(Material.WOOL).setDurability((short) 5).setName(ChatColor.GRAY + "Team 1").toItemStack());
        this.teamInventory.setItem(1, new ItemCreator(Material.WOOL).setDurability((short) 14).setName(ChatColor.GRAY + "Team 2").toItemStack());
        return this.teamInventory;
    }

    public void giveModInv(Player player) {
        player.getInventory().clear();
        PlayerInventory pi = player.getInventory();
        pi.setItem(0, new ItemCreator(Material.PAPER).setName(this.uhcWars.getReferences().getMainColor() + "Player Overall Stats").toItemStack());
        pi.setItem(1, new ItemCreator(Material.PAPER).setName(this.uhcWars.getReferences().getMainColor() + "Player Game Stats").toItemStack());
        pi.setItem(4, new ItemCreator(Material.SKULL_ITEM).setName(this.uhcWars.getReferences().getMainColor() + "Player Teleporter").toItemStack());
        pi.setItem(8, new ItemCreator(Material.CHEST).setName(this.uhcWars.getReferences().getMainColor() + "Player Inventory").toItemStack());
    }

    public Inventory getPlayerGameStats(Player target) {
        Inventory inventory = Bukkit.createInventory(null, 9 * 3, this.uhcWars.getReferences().getSecondaryColor() + target.getName() + this.uhcWars.getReferences().getMainColor() + " Game Stats:");
        Teams playerTeam = this.uhcWars.getTeamManager().getPlayerTeam(target);
        inventory.clear();
        inventory.setItem(0, new ItemCreator(Material.DIAMOND_SWORD).setName(this.uhcWars.getReferences().getMainColor() + "Kills: " + this.uhcWars.getReferences().getSecondaryColor() + this.uhcWars.getPlayerManager().getPlayerKills().get(target.getUniqueId())).toItemStack());
        inventory.setItem(1, new ItemCreator(Material.PAPER).setName(this.uhcWars.getReferences().getMainColor() + "Team Kills: " + this.uhcWars.getTeamManager().getTeamKills().get(playerTeam)).toItemStack());
        inventory.setItem(2, new ItemCreator(Material.REDSTONE).setName(this.uhcWars.getReferences().getMainColor() + "Coming Soon!").toItemStack());
        return inventory;
    }


}
