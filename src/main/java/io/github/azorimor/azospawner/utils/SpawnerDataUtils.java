package io.github.azorimor.azospawner.utils;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SpawnerDataUtils {

    private final List<EntityType> availableMobs;
    private final List<EntityType> availableEntitys;
    private final List<String> availableMobsString;
    private final List<String> availableEntitysString;

    private final List<Material> spawnEggs;


    public SpawnerDataUtils() {
        this.availableEntitys = new ArrayList<EntityType>(Arrays.asList(EntityType.values()));
        this.availableEntitysString = new ArrayList<String>(availableEntitys.size());
        for (EntityType entity :
                availableEntitys) {
            this.availableEntitysString.add(entity.toString());
        }

        this.availableMobs = new ArrayList<EntityType>();
        this.spawnEggs = new ArrayList<Material>();
        for (EntityType type :
                availableEntitys) {
            try {
                Material spawnegg = Material.valueOf(type + "_SPAWN_EGG");
                this.spawnEggs.add(spawnegg);
                this.availableMobs.add(type);
            } catch (IllegalArgumentException e) {
                continue;
            }
        }

        this.availableMobsString = new ArrayList<String>(availableMobs.size());

        for (EntityType spawnable :
                this.availableMobs) {
            this.availableMobsString.add(spawnable.toString());
        }
        Collections.sort(availableMobs);
        Collections.sort(spawnEggs);
    }

    public List<EntityType> getAvailableMobs() {
        return availableMobs;
    }

    public List<EntityType> getAvailableEntitys() {
        return availableEntitys;
    }

    public List<String> getAvailableMobsString() {
        return availableMobsString;
    }

    public List<String> getAvailableEntitysString() {
        return availableEntitysString;
    }

    public List<Material> getSpawnEggs() {
        return spawnEggs;
    }
}
