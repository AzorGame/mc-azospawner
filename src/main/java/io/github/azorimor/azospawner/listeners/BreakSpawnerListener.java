package io.github.azorimor.azospawner.listeners;

import io.github.azorimor.azospawner.AzoSpawner;
import io.github.azorimor.azospawner.utils.MessageHandler;
import io.github.azorimor.azospawner.utils.PickaxeItemTagType;
import io.github.azorimor.azospawner.utils.SpawnerItemTagType;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BreakSpawnerListener implements Listener {

    private MessageHandler messageHandler;
    private AzoSpawner instance;

    private final String spawnerColor;

    public BreakSpawnerListener(MessageHandler messageHandler, AzoSpawner instance) {
        this.messageHandler = messageHandler;
        this.instance = instance;

        this.spawnerColor = instance.getPluginFile().getTranslatedString("spawner.color");
    }


    @EventHandler
    public void onBreak(BlockBreakEvent event){

        if(event.getBlock().getType() == Material.SPAWNER){

            CreatureSpawner creatureSpawner = (CreatureSpawner) event.getBlock().getState();

            ItemStack breakTool = event.getPlayer().getInventory().getItemInMainHand();
            ItemMeta breakMeta = breakTool.getItemMeta();

            if(breakMeta != null && breakMeta.getCustomTagContainer().hasCustomTag(new NamespacedKey(instance,"breakspawner"),new PickaxeItemTagType())){


                ItemStack spawner = new ItemStack(Material.SPAWNER);
                ItemMeta spawnerMeta = spawner.getItemMeta();

                spawnerMeta.getCustomTagContainer().setCustomTag(new NamespacedKey(instance, "mobspawntype"),
                        new SpawnerItemTagType(), creatureSpawner.getSpawnedType());

                spawnerMeta.setDisplayName(spawnerColor+creatureSpawner.getSpawnedType().toString());
                spawner.setItemMeta(spawnerMeta);

                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(),spawner);
                event.setExpToDrop(0);
            }
        }
    }
}
