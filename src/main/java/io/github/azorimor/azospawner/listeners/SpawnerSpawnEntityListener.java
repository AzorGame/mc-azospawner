package io.github.azorimor.azospawner.listeners;

import io.github.azorimor.azospawner.AzoSpawner;
import io.github.azorimor.azospawner.utils.SpawnerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SpawnerSpawnEvent;

public class SpawnerSpawnEntityListener implements Listener {

    private AzoSpawner instance;
    private SpawnerManager spawnerManager;

    public SpawnerSpawnEntityListener(AzoSpawner instance) {
        this.instance = instance;
        this.spawnerManager = instance.getSpawnerManager();
    }

    @EventHandler
    public void onSpawn(SpawnerSpawnEvent event){
        if(spawnerManager.isDisallowed(event.getEntityType())){
            event.setCancelled(true);
        }
    }
}
