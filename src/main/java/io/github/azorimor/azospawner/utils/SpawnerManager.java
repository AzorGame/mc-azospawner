package io.github.azorimor.azospawner.utils;


import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpawnerManager {

    private List<EntityType> forbiddenTypes;
    private SpawnerDataUtils spawnerDataUtils;

    private boolean allDisabled;

    public SpawnerManager() {
        this.forbiddenTypes = new ArrayList<EntityType>();
        this.spawnerDataUtils = new SpawnerDataUtils();
    }

    public boolean disAllowSpawning(EntityType type){
        if(allDisabled){
            return false;
        }

        if(!forbiddenTypes.contains(type)){
            this.forbiddenTypes.add(type);
            return true;
        }
        return false;
    }

    public boolean allowSpawning(EntityType type){
        if(allDisabled){
            allDisabled = false;
        }
        if(forbiddenTypes.contains(type)){
            forbiddenTypes.remove(type);
            return true;
        }
        return false;
    }

    public boolean isDisallowed(EntityType type){
        return forbiddenTypes.contains(type);
    }

    public boolean disableAll(){
        if (!allDisabled) {
            for (EntityType type :
                    spawnerDataUtils.getAvailableMobs()) {
                forbiddenTypes.add(type);
            }
            allDisabled = true;
            return true;
        }
        return false;
    }

    public boolean enableAll(){
        if(allDisabled){
            forbiddenTypes.clear();
            allDisabled = false;
            return true;
        }
        return false;
    }

    public String getForbiddenString(){
        if(allDisabled)
            return "ALL";

        List<String> forbiddenStrings = new ArrayList<String>();
        for (EntityType type :
                forbiddenTypes) {
            forbiddenStrings.add(type.toString());
        }
        Collections.sort(forbiddenStrings);

        return forbiddenStrings.toString();
    }

}
