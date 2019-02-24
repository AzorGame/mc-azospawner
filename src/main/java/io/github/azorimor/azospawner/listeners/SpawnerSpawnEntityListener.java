package io.github.azorimor.azospawner.listeners;

import io.github.azorimor.azospawner.AzoSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SpawnerSpawnEvent;

public class SpawnerSpawnEntityListener implements Listener {

    private AzoSpawner instance;

    public SpawnerSpawnEntityListener(AzoSpawner instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onSpawn(SpawnerSpawnEvent event){
        if(instance.isSpawnerEnabled()){
            event.setCancelled(true);
        } else {
            //TODO alles so ändern, dass man bestimmte entitys deaktivieren kann
        }
    }
}
