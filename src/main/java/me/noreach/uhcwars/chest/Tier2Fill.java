package me.noreach.uhcwars.chest;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Tier2Fill {
    private final static int NUM_ITEMS = 7;

    public static void fillChest(Inventory inv){
        List<ItemStack> added = new ArrayList<ItemStack>();

        for(int i = 0; i < NUM_ITEMS; i++){
            int slot = (int) (Math.random()*27);
            ItemStack item = null;

            while(item == null || added.contains(item)){

                if(i == 0){
                    int k = (int) (Math.random()*3);

                    if(k == 0){
                        item = getPrimaryWeapon();
                    }

                    else{
                        item = getArmor();
                    }
                }

                if(i == 1){
                    int k = (int) (Math.random()*3);

                    if(k == 0){
                        item = getFood();
                    }

                    if(k == 1){
                        item = getSecondaryWeapon();
                    }

                    if(k == 2){
                        item = getMisc();
                    }

                }

                if(i == 2){
                    item = getFood();
                }

                if(i == 3 || i == 4){
                    int k = (int) (Math.random()*2);

                    if(k == 0){
                        item = getArmor();
                    }

                    if(k == 1){
                        item = getMisc();
                    }
                }

                if(i == 5 || i == 6){
                    int k = (int) (Math.random()*2);

                    if(k == 0){
                        item = getArmor();
                    }

                    if(k == 1){
                        item = getFood();
                    }
                }

                if(i == 7){
                    int k = (int) (Math.random()*3);

                    if(k == 0){
                        item = getFood();
                    }

                    if(k == 2){
                        item = getMisc();
                    }

                    if(k == 3){
                        item = getArmor();
                    }
                }
            }

            inv.setItem(slot, item);
            added.add(item);
        }
    }

    private static ItemStack getPrimaryWeapon(){
        ArrayList <ItemStack> items = new ArrayList<ItemStack>();

        items.add(new ItemStack(Material.STONE_SWORD));

        return items.get((int) (Math.random()*items.size()));
    }

    private static ItemStack getSecondaryWeapon(){
        ArrayList <ItemStack> items = new ArrayList<ItemStack>();

        items.add(new ItemStack(Material.BOW));
        items.add(new ItemStack(Material.FLINT_AND_STEEL));

        return items.get((int) (Math.random()*items.size()));
    }

    private static ItemStack getArmor(){
        ArrayList <ItemStack> items = new ArrayList<ItemStack>();

        items.add(new ItemStack(Material.GOLD_BOOTS));
        items.add(new ItemStack(Material.GOLD_CHESTPLATE));
        items.add(new ItemStack(Material.GOLD_LEGGINGS));
        items.add(new ItemStack(Material.GOLD_BOOTS));
        items.add(new ItemStack(Material.CHAINMAIL_HELMET));
        items.add(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        items.add(new ItemStack(Material.CHAINMAIL_LEGGINGS));
        items.add(new ItemStack(Material.CHAINMAIL_BOOTS));
        items.add(new ItemStack(Material.IRON_HELMET));
        items.add(new ItemStack(Material.IRON_CHESTPLATE));
        items.add(new ItemStack(Material.IRON_LEGGINGS));
        items.add(new ItemStack(Material.IRON_BOOTS));

        return items.get((int) (Math.random()*items.size()));
    }

    private static ItemStack getFood(){
        ArrayList <ItemStack> items = new ArrayList<ItemStack>();

        items.add(new ItemStack(Material.MUSHROOM_SOUP));
        items.add(new ItemStack(Material.COOKED_BEEF));
        items.add(new ItemStack(Material.COOKED_CHICKEN));
        items.add(new ItemStack(Material.GRILLED_PORK));
        items.add(new ItemStack(Material.APPLE));
        items.add(new ItemStack(Material.GOLDEN_APPLE));
        items.add(new ItemStack(Material.GOLDEN_CARROT));
        items.add(new ItemStack(Material.BREAD));
        items.add(new ItemStack(Material.BAKED_POTATO));
        items.add(new ItemStack(Material.PUMPKIN_PIE));

        return items.get((int) (Math.random()*items.size()));
    }

    private static ItemStack getMisc(){
        ArrayList <ItemStack> items = new ArrayList<ItemStack>();

        items.add(new ItemStack(Material.ARROW, 5));
        items.add(new ItemStack(Material.ARROW, 5));
        items.add(new ItemStack(Material.STICK));
        items.add(new ItemStack(Material.DIAMOND));
        items.add(new ItemStack(Material.IRON_INGOT));
        items.add(new ItemStack(Material.IRON_INGOT));
        items.add(new ItemStack(Material.GOLD_INGOT));
        items.add(new ItemStack(Material.GOLD_INGOT));
        items.add(new ItemStack(Material.BOAT));

        return items.get((int) (Math.random()*items.size()));
    }
}
