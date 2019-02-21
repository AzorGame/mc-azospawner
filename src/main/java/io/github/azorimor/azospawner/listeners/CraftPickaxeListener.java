package io.github.azorimor.azospawner.listeners;

import io.github.azorimor.azospawner.AzoSpawner;
import io.github.azorimor.azospawner.utils.MessageHandler;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class CraftPickaxeListener implements Listener {

    private MessageHandler messageHandler;
    private AzoSpawner instance;

    public CraftPickaxeListener(MessageHandler messageHandler, AzoSpawner instance) {
        this.messageHandler = messageHandler;
        this.instance = instance;
    }

    @EventHandler
    public void onCraft(PrepareItemCraftEvent event){

        Recipe recipe = event.getRecipe();
        if(recipe != null && recipe.getResult() != null){
            if(instance.getRecipe().getPickaxe().equals(recipe.getResult())){
                for (HumanEntity humanEntity : event.getViewers()){
                    if(!humanEntity.hasPermission("azospawner.crafting.spawnerpickaxe")){
                        if(humanEntity instanceof Player){
                            Player player = (Player) humanEntity;
                            event.getInventory().setResult(new ItemStack(Material.AIR));
                            messageHandler.sendRecipeNoPermission(player,recipe);
                        }
                    }
                }
            }
        }
    }

}
