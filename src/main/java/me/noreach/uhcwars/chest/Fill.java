package me.noreach.uhcwars.chest;

import java.util.ArrayList;
import java.util.List;

import me.noreach.uhcwars.UHCWars;
import me.noreach.uhcwars.util.ItemCreator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Fill {

    private UHCWars uhcWars;

    public Fill(UHCWars uhcWars){
        this.uhcWars = uhcWars;
    }

    private final int NUM_ITEMS = 8;

    public void fillChest(Inventory inv) {
        List<ItemStack> added = new ArrayList<ItemStack>();

        for (int i = 0; i < NUM_ITEMS; i++) {
            int slot = (int) (Math.random() * 27);
            ItemStack itemStack = null;

            while (itemStack == null || added.contains(itemStack)) {

                if (i == 0) {
                    int c = (int) (Math.random() * 3);

                    if (c == 0) {
                        itemStack = getPrimaryWeapon();
                    } else {
                        itemStack = getArmor();
                    }
                }

                else if (i == 1) {
                    int c = (int) (Math.random() * 3);

                    if (c == 0) {
                        itemStack = getFood();
                    }

                    if (c == 1) {
                        itemStack = getSecondaryWeapon();
                    }

                    if (c == 2) {
                        itemStack = getMisc();
                    }

                }

                else if (i == 2) {
                    itemStack = getFood();
                }

                else if (i == 3 || i == 4) {
                    int c = (int) (Math.random() * 2);

                    if (c == 0) {
                        itemStack = getArmor();
                    }

                    if (c == 1) {
                        itemStack = getMisc();
                    }
                }

                else if (i == 5 || i == 6) {
                    int c = (int) (Math.random() * 2);

                    if (c == 0) {
                        itemStack = getArmor();
                    }

                    if (c == 1) {
                        itemStack = getFood();
                    }
                }

                else if (i == 7) {
                    int c = (int) (Math.random() * 3);

                    if (c == 0) {
                        itemStack = getFood();
                    }

                    if (c == 2) {
                        itemStack = getMisc();
                    }

                    if (c == 3) {
                        itemStack = getArmor();
                    }
                }
            }

            inv.setItem(slot, itemStack);
            added.add(itemStack);
        }
    }

    private ItemStack getPrimaryWeapon() {
        ArrayList<ItemStack> weapons = new ArrayList<ItemStack>();

        weapons.add(new ItemCreator(Material.IRON_SWORD).addEnchant(Enchantment.FIRE_ASPECT, 1).toItemStack());
        weapons.add(new ItemCreator(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 2).toItemStack());
        weapons.add(new ItemCreator(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 1).addEnchant(Enchantment.FIRE_ASPECT, 1).toItemStack());
        weapons.add(new ItemCreator(Material.IRON_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 3).toItemStack());
        weapons.add(new ItemCreator(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 3).toItemStack());

        return weapons.get((int) (Math.random() * weapons.size()));
    }

    private ItemStack getSecondaryWeapon() {
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();

        items.add(new ItemCreator(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 3).toItemStack());
        items.add(new ItemStack(Material.LAVA_BUCKET));

        return items.get((int) (Math.random() * items.size()));
    }

    private ItemStack getArmor() {
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();

        items.add(new ItemCreator(Material.DIAMOND_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack());
        items.add(new ItemCreator(Material.DIAMOND_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack());
        items.add(new ItemCreator(Material.DIAMOND_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack());
        items.add(new ItemCreator(Material.DIAMOND_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack());
        items.add(new ItemCreator(Material.DIAMOND_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack());
        items.add(new ItemCreator(Material.DIAMOND_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack());
        items.add(new ItemCreator(Material.DIAMOND_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack());
        items.add(new ItemCreator(Material.DIAMOND_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack());
        items.add(new ItemCreator(Material.IRON_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack());
        items.add(new ItemCreator(Material.IRON_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack());
        items.add(new ItemCreator(Material.IRON_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack());
        items.add(new ItemCreator(Material.IRON_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack());
        items.add(new ItemCreator(Material.IRON_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack());
        items.add(new ItemCreator(Material.IRON_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack());
        items.add(new ItemCreator(Material.IRON_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack());
        items.add(new ItemCreator(Material.IRON_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack());

        return items.get((int) (Math.random() * items.size()));
    }

    private ItemStack getFood() {
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();

        items.add(new ItemCreator(Material.COOKED_BEEF).setAmount(64).toItemStack());

        return items.get((int) (Math.random() * items.size()));
    }

    private ItemStack getMisc() {
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();

        items.add(new ItemCreator(Material.GOLDEN_APPLE).setAmount(4).toItemStack());
        items.add(new ItemCreator(Material.GOLDEN_APPLE).setName(ChatColor.GOLD + "Golden Head").setAmount(2).toItemStack());
        items.add(new ItemCreator(Material.ARROW).setAmount(32).toItemStack());
        items.add(new ItemCreator(Material.WOOD).setAmount(64).toItemStack());
        items.add(new ItemCreator(Material.WATER_BUCKET).toItemStack());
        items.add(new ItemCreator(Material.LAVA_BUCKET).toItemStack());

        return items.get((int) (Math.random() * items.size()));
    }
}