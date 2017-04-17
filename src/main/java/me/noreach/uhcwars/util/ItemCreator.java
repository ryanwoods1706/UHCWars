package me.noreach.uhcwars.util;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;


public class ItemCreator {
    private ItemStack item;


    public ItemCreator(Material material) {
        this(material, 1);
    }

    public ItemCreator(ItemStack item) {
        this.item = item;
    }


    public ItemCreator(Material material, int amount) {
        item = new ItemStack(material, amount);
    }


    public ItemCreator setDurability(short dur) {
        item.setDurability(dur);
        return this;
    }


    public ItemCreator setName(String name) {
        if (name == null) {
            return this;
        }
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(name);
        item.setItemMeta(im);
        return this;
    }


    public ItemCreator setSkullOwner(String owner) {
        ItemMeta meta = item.getItemMeta();
        if (!(meta instanceof SkullMeta)) {
            return this;
        }
        SkullMeta im = (SkullMeta) meta;
        item.setDurability((short) 3);
        im.setOwner(owner);
        item.setItemMeta(im);
        return this;
    }

    public ItemCreator addEnchant(Enchantment enchantment, int level) {
        ItemMeta im = item.getItemMeta();
        im.addEnchant(enchantment, level, true);
        item.setItemMeta(im);
        return this;
    }

    public ItemCreator setLore(String... lore) {
        if (lore == null) {
            return this;
        }
        ItemMeta im = item.getItemMeta();
        im.setLore(Arrays.asList(lore));
        item.setItemMeta(im);
        return this;
    }


    @SuppressWarnings("deprecation")
    public ItemCreator setWoolColour(DyeColor colour) {
        if (!item.getType().equals(Material.WOOL)) {
            return this;
        }
        this.item.setDurability(colour.getData());
        return this;
    }

    public ItemCreator setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }


    public ItemCreator setUnbreakable(boolean unbreakable) {
        ItemMeta meta = item.getItemMeta();
        meta.spigot().setUnbreakable(unbreakable);
        item.setItemMeta(meta);
        return this;
    }

    public ItemStack toItemStack() {
        return item;
    }

}