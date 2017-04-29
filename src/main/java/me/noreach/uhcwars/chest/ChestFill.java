package me.noreach.uhcwars.chest;
import java.util.ArrayList;
import java.util.List;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.util.Invent;
import me.noreach.uhcwars.util.ItemCreator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ChestFill {

    private final int TOTAL_ITEMS = 7;
    private UHCWars uhcWars;

    public ChestFill(UHCWars uhcWars) {
        this.uhcWars = uhcWars;
    }


    public void fillChest(Inventory inv) {
        List<ItemStack> added = new ArrayList<ItemStack>();

        for (int i = 0; i < TOTAL_ITEMS; i++) {
            int slot = (int) (Math.random() * 27);
            ItemStack item = null;

            while (item == null || added.contains(item)) {

                if (i == 0) {
                    int k = (int) (Math.random() * 3);

                    if (k == 0) {
                        item = getPrimaryWeapon();
                    } else {
                        item = getArmor();
                    }
                }

                else if (i == 1) {
                    int k = (int) (Math.random() * 3);

                    if (k == 0) {
                        item = getFood();
                    }

                    if (k == 1) {
                        item = getSecondaryWeapon();
                    }

                    if (k == 2) {
                        item = getMisc();
                    }

                }

                else if (i == 2) {
                    item = getFood();
                }

                else if (i == 3 || i == 4) {
                    int k = (int) (Math.random() * 2);

                    if (k == 0) {
                        item = getArmor();
                    }

                    if (k == 1) {
                        item = getMisc();
                    }
                }

                else
                    if (i == 5 || i == 6) {
                    int k = (int) (Math.random() * 2);

                    if (k == 0) {
                        item = getArmor();
                    }

                    if (k == 1) {
                        item = getFood();
                    }
                }

            }

            inv.setItem(slot, item);
            added.add(item);
        }
    }

    private ItemStack getPrimaryWeapon() {
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();

        items.add(new ItemCreator(Material.IRON_SWORD).addEnchant(Enchantment.FIRE_ASPECT, 2).toItemStack());
        items.add(new ItemCreator(Material.IRON_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 1).addEnchant(Enchantment.FIRE_ASPECT, 1).toItemStack());
        items.add(new ItemCreator(Material.IRON_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 3).toItemStack());
        items.add(new ItemCreator(Material.DIAMOND_SWORD).addEnchant(Enchantment.FIRE_ASPECT, 1).toItemStack());
        items.add(new ItemCreator(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 2).toItemStack());
        items.add(new ItemCreator(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 2).addEnchant(Enchantment.FIRE_ASPECT, 1).toItemStack());
        items.add(new ItemCreator(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 3).toItemStack());

        return items.get((int) (Math.random() * items.size()));
    }

    private ItemStack getSecondaryWeapon() {
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();

        items.add(new ItemCreator(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 2).toItemStack());
        items.add(new ItemCreator(Material.BOW).addEnchant(Enchantment.ARROW_FIRE, 1).toItemStack());
        items.add(new ItemCreator(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 3).toItemStack());
        items.add(new ItemCreator(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 4).toItemStack());

        return items.get((int) (Math.random() * items.size()));
    }

    private ItemStack getArmor() {
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();

        items.add(new ItemCreator(Material.IRON_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack());
        items.add(new ItemCreator(Material.IRON_HELMET).addEnchant(Enchantment.PROTECTION_PROJECTILE, 1).toItemStack());
        items.add(new ItemCreator(Material.IRON_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).toItemStack());
        items.add(new ItemCreator(Material.IRON_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack());
        items.add(new ItemCreator(Material.IRON_CHESTPLATE).addEnchant(Enchantment.PROTECTION_PROJECTILE, 2).toItemStack());
        items.add(new ItemCreator(Material.IRON_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).toItemStack());
        items.add(new ItemCreator(Material.IRON_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack());
        items.add(new ItemCreator(Material.IRON_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack());
        items.add(new ItemCreator(Material.IRON_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack());
        items.add(new ItemCreator(Material.IRON_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack());
        items.add(new ItemCreator(Material.DIAMOND_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack());
        items.add(new ItemCreator(Material.DIAMOND_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack());
        items.add(new ItemCreator(Material.DIAMOND_HELMET).addEnchant(Enchantment.PROTECTION_PROJECTILE, 1).toItemStack());
        items.add(new ItemCreator(Material.DIAMOND_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack());
        items.add(new ItemCreator(Material.DIAMOND_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack());
        items.add(new ItemCreator(Material.DIAMOND_CHESTPLATE).addEnchant(Enchantment.PROTECTION_PROJECTILE, 1).toItemStack());
        items.add(new ItemCreator(Material.DIAMOND_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack());
        items.add(new ItemCreator(Material.DIAMOND_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack());
        items.add(new ItemCreator(Material.DIAMOND_LEGGINGS).addEnchant(Enchantment.PROTECTION_PROJECTILE, 1).toItemStack());
        items.add(new ItemCreator(Material.DIAMOND_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack());
        items.add(new ItemCreator(Material.DIAMOND_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack());
        items.add(new ItemCreator(Material.DIAMOND_BOOTS).addEnchant(Enchantment.PROTECTION_PROJECTILE, 1).toItemStack());
        return items.get((int) (Math.random() * items.size()));
    }

    private ItemStack getFood() {
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();

        items.add(new ItemCreator(Material.COOKED_BEEF).setAmount(64).toItemStack());
        items.add(new ItemCreator(Material.COOKED_CHICKEN).setAmount(32).toItemStack());
        items.add(new ItemCreator(Material.GRILLED_PORK).setAmount(16).toItemStack());

        return items.get((int) (Math.random() * items.size()));
    }

    private ItemStack getMisc() {
        ArrayList<ItemStack> items = new ArrayList<>();

        items.add(new ItemCreator(Material.ARROW).setAmount(32).toItemStack());
        items.add(new ItemCreator(Material.GOLDEN_APPLE).setName(ChatColor.GOLD + "Golden Head").setAmount(3).toItemStack());
        items.add(new ItemCreator(Material.GOLDEN_APPLE).setAmount(6).toItemStack());

        return items.get((int) (Math.random() * items.size()));
    }
}