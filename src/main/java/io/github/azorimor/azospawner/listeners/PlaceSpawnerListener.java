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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlaceSpawnerListener implements Listener {

    private AzoSpawner instance;

    private MessageHandler messageHandler;

    public PlaceSpawnerListener(AzoSpawner instance, MessageHandler messageHandler) {
        this.instance = instance;
        this.messageHandler = messageHandler;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){

        if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
            Player player = event.getPlayer();

            if(player.getInventory().getItemInMainHand().getType() == Material.SPAWNER){

                ItemStack spawnerItem = player.getInventory().getItemInMainHand();
                ItemMeta spawnerMeta = spawnerItem.getItemMeta();
                if(spawnerMeta.getCustomTagContainer().hasCustomTag(new NamespacedKey(instance, "mobspawntype"),
                        new SpawnerItemTagType())){
                    EntityType spawnerType = spawnerMeta.getCustomTagContainer().getCustomTag(new NamespacedKey(instance, "mobspawntype"),
                            new SpawnerItemTagType());
                    Block spawnBlock = event.getClickedBlock().getLocation().add(event.getBlockFace().getDirection()).getBlock();
                    spawnBlock.setType(Material.SPAWNER);

                    CreatureSpawner spawner = (CreatureSpawner) spawnBlock.getState();
                    spawner.setSpawnedType(spawnerType);
                    spawner.update();
                }
            }
        }
    }
}
