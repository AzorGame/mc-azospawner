package io.github.azorimor.azospawner.recipe;

import io.github.azorimor.azospawner.AzoSpawner;
import io.github.azorimor.azospawner.files.PluginFile;
import io.github.azorimor.azospawner.utils.PickaxeItemTagType;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class SpawnerPickaxeRecipe {

    private PluginFile pluginFile;
    private ItemStack pickaxe;
    private AzoSpawner instance;
    private NamespacedKey key;
    private ShapedRecipe recipe;

    public SpawnerPickaxeRecipe(AzoSpawner instance, PluginFile pluginFile) {
        this.instance = instance;
        this.pluginFile = pluginFile;
        this.key = new NamespacedKey(instance,"spawner_pickaxe");

        pickaxe = new ItemStack(Material.GOLDEN_PICKAXE);
        ItemMeta pickMeta = pickaxe.getItemMeta();
        pickMeta.setDisplayName(pluginFile.getTranslatedString("crafting.pickaxe.itemName").replace('&','§'));
        pickMeta.setLore(pluginFile.getTranslatedStringList("crafting.pickaxe.itemLore"));

        pickMeta.getCustomTagContainer().setCustomTag(new NamespacedKey(instance,"breakspawner"),new PickaxeItemTagType(),true);

        pickaxe.setItemMeta(pickMeta);

        this.recipe = new ShapedRecipe(key,pickaxe);

        recipe.shape("NNN"," O "," O ");
        recipe.setIngredient('N',Material.NETHER_STAR);
        recipe.setIngredient('O',Material.OBSIDIAN);

    }

    public ShapedRecipe getRecipe() {
        return recipe;
    }

    public ItemStack getPickaxe() {
        return pickaxe;
    }
}
