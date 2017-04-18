package me.noreach.uhcwars.player;

import org.bukkit.inventory.ItemStack;

/**
 * Created by Ryan on 16/04/2017.
 */
public class Stat {

    private ItemStack itemStack;
    private int amount;

    public Stat(ItemStack itemStack, int amount){
        this.itemStack = itemStack;
        this.amount = amount;
    }

    public ItemStack getItemStack(){ return this.itemStack;}

    public int getAmount(){ return this.amount;}

    public void incrementValue(){ this.amount +=1;}

    public void setAmount(int amount){ this.amount = amount;}
}
