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

    private final int MAX_ITEMS = 7;
    private UHCWars uhcWars;

    public ChestFill(UHCWars uhcWars) {
        this.uhcWars = uhcWars;
    }


    public void fillChest(Inventory inv) {
        List<ItemStack> added = new ArrayList<ItemStack>();

        for (int i = 0; i < MAX_ITEMS; i++) {
            int slot = (int) (Math.random() * 27);
            ItemStack item = null;

            while (item == null || added.contains(item)) {

                switch (i) {
                    case 0:
                        int k = (int) (Math.random() * 3);

                        if (k == 0) {
                            item = getSword();
                        } else {
                            item = getArmor();
                        }
                        break;
                    case 1:
                        int l = (int) (Math.random() * 3);

                        if (l == 0) {
                            item = getFood();
                        }

                        if (l == 1) {
                            item = getBow();
                        }

                        if (l == 2) {
                            item = getMisc();
                        }
                        break;
                    case 2:
                        item = getFood();
                        break;
                    case 3:
                        int m = (int) (Math.random() * 2);

                        if (m == 0) {
                            item = getArmor();
                        }

                        if (m == 1) {
                            item = getMisc();
                        }
                        break;
                    case 4:
                        int n = (int) (Math.random() * 2);

                        if (n == 0) {
                            item = getArmor();
                        }

                        if (n == 1) {
                            item = getMisc();
                        }
                        break;
                    case 5:
                        int o = (int) (Math.random() * 2);

                        if (o == 0) {
                            item = getArmor();
                        }

                        if (o == 1) {
                            item = getFood();
                        }
                        break;
                    case 6:
                        int p = (int) (Math.random() * 2);

                        if (p == 0) {
                            item = getArmor();
                        }

                        if (p == 1) {
                            item = getFood();
                        }
                        break;


                }
            }
            inv.setItem(slot, item);
            added.add(item);
        }
    }

    private ItemStack getSword() {
        ArrayList<ItemStack> weaponItems = new ArrayList<ItemStack>();

        weaponItems.add(new ItemCreator(Material.IRON_SWORD).addEnchant(Enchantment.FIRE_ASPECT, 2).toItemStack());
        weaponItems.add(new ItemCreator(Material.IRON_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 1).addEnchant(Enchantment.FIRE_ASPECT, 1).toItemStack());
        weaponItems.add(new ItemCreator(Material.IRON_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 3).toItemStack());
        weaponItems.add(new ItemCreator(Material.DIAMOND_SWORD).addEnchant(Enchantment.FIRE_ASPECT, 1).toItemStack());
        weaponItems.add(new ItemCreator(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 2).toItemStack());
        weaponItems.add(new ItemCreator(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 2).addEnchant(Enchantment.FIRE_ASPECT, 1).toItemStack());
        weaponItems.add(new ItemCreator(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 3).toItemStack());

        return weaponItems.get((int) (Math.random() * weaponItems.size()));
    }

    private ItemStack getBow() {
        ArrayList<ItemStack> bowItems = new ArrayList<>();

        bowItems.add(new ItemCreator(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 2).toItemStack());
        bowItems.add(new ItemCreator(Material.BOW).addEnchant(Enchantment.ARROW_FIRE, 1).toItemStack());
        bowItems.add(new ItemCreator(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 3).toItemStack());
        bowItems.add(new ItemCreator(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 4).toItemStack());
        return bowItems.get((int) (Math.random() * bowItems.size()));
    }

    private ItemStack getArmor() {
        ArrayList<ItemStack> armorWeapons = new ArrayList<ItemStack>();

        armorWeapons.add(new ItemCreator(Material.IRON_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack());
        armorWeapons.add(new ItemCreator(Material.IRON_HELMET).addEnchant(Enchantment.PROTECTION_PROJECTILE, 1).toItemStack());
        armorWeapons.add(new ItemCreator(Material.IRON_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).toItemStack());
        armorWeapons.add(new ItemCreator(Material.IRON_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack());
        armorWeapons.add(new ItemCreator(Material.IRON_CHESTPLATE).addEnchant(Enchantment.PROTECTION_PROJECTILE, 2).toItemStack());
        armorWeapons.add(new ItemCreator(Material.IRON_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).toItemStack());
        armorWeapons.add(new ItemCreator(Material.IRON_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack());
        armorWeapons.add(new ItemCreator(Material.IRON_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack());
        armorWeapons.add(new ItemCreator(Material.IRON_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack());
        armorWeapons.add(new ItemCreator(Material.IRON_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack());
        armorWeapons.add(new ItemCreator(Material.DIAMOND_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack());
        armorWeapons.add(new ItemCreator(Material.DIAMOND_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack());
        armorWeapons.add(new ItemCreator(Material.DIAMOND_HELMET).addEnchant(Enchantment.PROTECTION_PROJECTILE, 1).toItemStack());
        armorWeapons.add(new ItemCreator(Material.DIAMOND_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack());
        armorWeapons.add(new ItemCreator(Material.DIAMOND_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack());
        armorWeapons.add(new ItemCreator(Material.DIAMOND_CHESTPLATE).addEnchant(Enchantment.PROTECTION_PROJECTILE, 1).toItemStack());
        armorWeapons.add(new ItemCreator(Material.DIAMOND_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack());
        armorWeapons.add(new ItemCreator(Material.DIAMOND_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack());
        armorWeapons.add(new ItemCreator(Material.DIAMOND_LEGGINGS).addEnchant(Enchantment.PROTECTION_PROJECTILE, 1).toItemStack());
        armorWeapons.add(new ItemCreator(Material.DIAMOND_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack());
        armorWeapons.add(new ItemCreator(Material.DIAMOND_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack());
        armorWeapons.add(new ItemCreator(Material.DIAMOND_BOOTS).addEnchant(Enchantment.PROTECTION_PROJECTILE, 1).toItemStack());
        return armorWeapons.get((int) (Math.random() * armorWeapons.size()));
    }

    private ItemStack getFood() {
        ArrayList<ItemStack> foodItems = new ArrayList<ItemStack>();

        foodItems.add(new ItemCreator(Material.COOKED_BEEF).setAmount(64).toItemStack());
        foodItems.add(new ItemCreator(Material.COOKED_CHICKEN).setAmount(32).toItemStack());
        foodItems.add(new ItemCreator(Material.GRILLED_PORK).setAmount(16).toItemStack());

        return foodItems.get((int) (Math.random() * foodItems.size()));
    }

    private ItemStack getMisc() {
        ArrayList<ItemStack> miscItems = new ArrayList<>();

        miscItems.add(new ItemCreator(Material.ARROW).setAmount(32).toItemStack());
        miscItems.add(new ItemCreator(Material.GOLDEN_APPLE).setName(ChatColor.GOLD + "Golden Head").setAmount(3).toItemStack());
        miscItems.add(new ItemCreator(Material.GOLDEN_APPLE).setAmount(6).toItemStack());

        return miscItems.get((int) (Math.random() * miscItems.size()));
    }
}