package io.github.azorimor.azospawner.listeners;

import io.github.azorimor.azospawner.AzoSpawner;
import io.github.azorimor.azospawner.files.PluginFile;
import io.github.azorimor.azospawner.utils.MessageHandler;
import io.github.azorimor.azospawner.utils.SpawnerDataUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerInteractsSpawnerListener implements Listener {


    private AzoSpawner instance;
    private List<String> availableTypesString;
    private List<ItemStack> spawnEggs;
    private MessageHandler messageHandler;
    private SpawnerDataUtils spawnerDataUtils;
    private PluginFile config;

    private String guiName;
    private String spawnEggName;
    private List<String> spawnEggMeta;

    public PlayerInteractsSpawnerListener(AzoSpawner instance) {
        this.instance = instance;
        this.messageHandler = instance.getMessageHandler();
        this.spawnerDataUtils = new SpawnerDataUtils();
        this.config = instance.getPluginFile();

        List<EntityType> availableTypes = new ArrayList<EntityType>(Arrays.asList(EntityType.values()));

        availableTypesString = new ArrayList<String>(availableTypes.size());
        for (EntityType type :
                availableTypes) {
            availableTypesString.add(type.toString());
        }

        this.guiName = config.getTranslatedString("spawner.gui.name");
        this.spawnEggName = config.getTranslatedString("spawner.gui.item.egg.name");
        this.spawnEggMeta = config.getTranslatedStringList("spawner.gui.item.egg.meta");
        loadSpawnEggsStacks();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block clickedBlock = event.getClickedBlock();
            if (clickedBlock.getType() == Material.SPAWNER) {
                Player player = event.getPlayer();
                if (player.hasPermission("azospawner.interact.spawner") && !player.isSneaking()) {
                    event.setCancelled(true);
                    openSpawnerGUI(player);
                    player.setMetadata("spawnerChangeLocation", new FixedMetadataValue(instance, clickedBlock));
                }
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory clickedInv = event.getClickedInventory();
        if (clickedInv != null && clickedInv.getName().equalsIgnoreCase(guiName)) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem.getType() != Material.AIR && player.hasMetadata("spawnerChangeLocation")) {
                Block spawnBlock = (Block) player.getMetadata("spawnerChangeLocation").get(0).value();
                CreatureSpawner spawner = (CreatureSpawner) spawnBlock.getState();
                EntityType oldType = spawner.getSpawnedType();
                spawner.setSpawnedType(EntityType.valueOf(clickedItem.getType().toString().replace("_SPAWN_EGG", "").toUpperCase()));
                spawner.update();
                player.closeInventory();
                messageHandler.sendSpawnerTypeChanged(player,oldType,spawner.getSpawnedType());
            }
        }
    }


    private void openSpawnerGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 54, guiName);

        for (ItemStack egg :
                spawnEggs) {
            String spawnType = egg.getType().toString().replace("_SPAWN_EGG", "").toLowerCase();
            if (player.hasPermission("azospawner.interact.spawner." + spawnType)) {
                gui.addItem(egg);
            }
        }

        player.openInventory(gui);
    }

    private void loadSpawnEggsStacks() {
        this.spawnEggs = new ArrayList<ItemStack>();
        for (Material type :
                spawnerDataUtils.getSpawnEggs()) {
            String spawnType = type.toString().replace("_SPAWN_EGG", "");

            ItemStack egg = new ItemStack(type);
            ItemMeta eggMeta = egg.getItemMeta();
            eggMeta.setDisplayName(spawnEggName.replace("%type%",spawnType));
            eggMeta.setLore(spawnEggMeta);
            egg.setItemMeta(eggMeta);

            spawnEggs.add(egg);
        }
    }
}
