package io.github.azorimor.azospawner.listeners;

import io.github.azorimor.azospawner.AzoSpawner;
import io.github.azorimor.azospawner.utils.MessageHandler;
import io.github.azorimor.azospawner.utils.SpawnerItemTagType;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlaceSpawnerListener implements Listener {

    private AzoSpawner instance;

    public PlaceSpawnerListener(AzoSpawner instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        Block spawnBlock = event.getBlockPlaced();
        ItemStack spawnerItem = event.getItemInHand();
        if (spawnBlock.getState() instanceof CreatureSpawner && spawnerItem.hasItemMeta()) {
            ItemMeta meta = spawnerItem.getItemMeta();
            Player player = event.getPlayer();

            ItemMeta spawnerMeta = spawnerItem.getItemMeta();
            if(spawnerMeta.getCustomTagContainer().hasCustomTag(new NamespacedKey(instance, "mobspawntype"),
                    new SpawnerItemTagType())){
                EntityType spawnerType = spawnerMeta.getCustomTagContainer().getCustomTag(new NamespacedKey(instance, "mobspawntype"),
                        new SpawnerItemTagType());
                spawnBlock.setType(Material.SPAWNER);

                CreatureSpawner spawner = (CreatureSpawner) spawnBlock.getState();
                spawner.setSpawnedType(spawnerType);
                spawner.update();
            }
        }
    }
}
