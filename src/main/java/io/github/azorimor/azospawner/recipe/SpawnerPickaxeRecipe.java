package io.github.azorimor.azospawner.recipe;

import io.github.azorimor.azospawner.AzoSpawner;
import io.github.azorimor.azospawner.files.PluginFile;
import io.github.azorimor.azospawner.utils.PickaxeItemTagType;
import io.github.azorimor.azospawner.utils.RecipeValues;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class SpawnerPickaxeRecipe {

    private PluginFile pluginFile;
    private ItemStack pickaxe;
    private AzoSpawner instance;
    private NamespacedKey key;
    private ShapedRecipe recipe;
    private RecipeValues recipeValues;

    public SpawnerPickaxeRecipe(AzoSpawner instance, PluginFile pluginFile) {
        this.instance = instance;
        this.pluginFile = pluginFile;
        this.key = new NamespacedKey(instance,"spawner_pickaxe");

        pickaxe = new ItemStack(Material.GOLDEN_PICKAXE);
        ItemMeta pickMeta = pickaxe.getItemMeta();
        pickMeta.setDisplayName(pluginFile.getTranslatedString("crafting.pickaxe.itemName").replace('&','§'));
        pickMeta.setLore(pluginFile.getTranslatedStringList("crafting.pickaxe.itemLore"));

        pickMeta.getCustomTagContainer().setCustomTag(new NamespacedKey(instance,"breakspawner"),new PickaxeItemTagType(),true);

        ((Damageable)pickMeta).setDamage(30);
        pickaxe.setItemMeta(pickMeta);

        this.recipe = new ShapedRecipe(key,pickaxe);

        this.recipeValues = pluginFile.getRecipeInformation("crafting.pickaxe.recipe");

        recipe.shape(recipeValues.getFirstRow(),recipeValues.getSecondRow(),recipeValues.getThirdRow());
        for (Character key : recipeValues.getValues().keySet()){
            recipe.setIngredient(key,recipeValues.getValues().get(key));
        }
    }

    public ShapedRecipe getRecipe() {
        return recipe;
    }

    public ItemStack getPickaxe() {
        return pickaxe;
    }
}
