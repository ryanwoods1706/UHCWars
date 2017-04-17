package me.noreach.uhcwars.locations;

import me.noreach.uhcwars.UHCWars;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Ryan on 17/04/2017.
 */
public class ObjectiveManager {

    private UHCWars uhcWars;
    private Map<UUID, List<Objective>> objectiveCache = new HashMap<>();

    public ObjectiveManager(UHCWars uhcWars){
        this.uhcWars = uhcWars;
    }



    public Map<UUID, List<Objective>> getObjectiveCache(){ return this.objectiveCache;}


    /**
     * SO ON COMAMND GIVE PLAYER AXE TO SELECT OBJECTIVES
     * ON LEFT AND RIGHT CLICK CREATE NEW OBJECTIVE AND STORE THEM
     * ON COMMAND ./SAVE OBJECTIVES, GET OBJECTIVES FROM STORAGE AND SAVE THEM
     * ADD
     */
    /**
     * How to store?!
     *
     */


}
