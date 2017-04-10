package me.noreach.uhcwars;

import me.noreach.uhcwars.util.References;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Ref;

/**
 * Created by Ryan on 10/04/2017.
 */
public class UHCWars extends JavaPlugin {


    private References references;

    @Override
    public void onEnable() {
        getConfig();
        this.references = new References(this);
    }

    @Override
    public void onDisable() {

    }


    private void registerCommands() {

    }

    private void registerEvents() {

    }


    public References getReferences(){
        return this.references;
    }
}
