package io.github.azorimor.azospawner.files;

import io.github.azorimor.azospawner.AzoSpawner;
import io.github.azorimor.azospawner.utils.RecipeValues;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PluginFile {

    private File file;
    private FileConfiguration cfg;

    private AzoSpawner instance;

    public PluginFile(AzoSpawner instance) {
        this.instance = instance;
        this.file = new File(instance.getDataFolder(),"config.yml");
        this.cfg = YamlConfiguration.loadConfiguration(file);
        copyDefaults();
    }

    /**
     * Saves all default values for specific keys.
     */
    public void copyDefaults(){
        cfg.options().copyDefaults(true);

        cfg.addDefault("prefix","&7[&cAzo&bSpawner&7] &r");
        cfg.addDefault("spawner.color","&3");

        cfg.addDefault("command.message.noPlayer","Only players can perform this command.");
        cfg.addDefault("command.message.noPermission","&7You don't have enought permissions for the command &b/%command%&7.");
        cfg.addDefault("command.message.wrongCommandUsage","&7You used the command &b/%command% &7the wrong way. Just try something similar to &b/%usage%&7.");
        cfg.addDefault("command.message.noNumber","&7Please enter a valid number instead of &b%wrongArgument%&7.");
        cfg.addDefault("command.message.giveSpawner","&7You recived &b%amount% &7spawner(s), which spawns &b%type%&7.");
        cfg.addDefault("command.message.giveSpawnerOther","&7You gave &b%target%&7 &b%amount% &b%type% &7spawner(s).");
        cfg.addDefault("command.message.noEntityType","&7Please enter a valid entitytype instead of &b%wrongType%&7.");
        cfg.addDefault("command.message.playerOffline","&7The player &b%player% &7is offline.");
        cfg.addDefault("command.message.givePickaxe","&7You recived the &bSpawner Pickaxe&7.");
        cfg.addDefault("command.message.givePickaxeOther","&7The player &b%target%&7 recived the &bSpawner Pickaxe&7.");


        cfg.addDefault("crafting.message.noPermission","&7You do not have enought permissions to craft &b%item%&7.");



        cfg.addDefault("crafting.pickaxe.itemName","&cSpawnerPick");
        cfg.addDefault("crafting.pickaxe.itemMaterial","GOLDEN_PICKAXE");
        ArrayList<String> pickLore = new ArrayList<String>();
        pickLore.add("&7&m                         ");
        pickLore.add("&6You can destroy Spawners");
        pickLore.add("&6by using this pickaxe.");
        pickLore.add("&7&m                         ");
        cfg.addDefault("crafting.pickaxe.itemLore", pickLore);
        cfg.addDefault("crafting.pickaxe.damage", 30);

        cfg.addDefault("crafting.pickaxe.recipe.firstrow","NNN");
        cfg.addDefault("crafting.pickaxe.recipe.secondrow","_O_");
        cfg.addDefault("crafting.pickaxe.recipe.thirdrow","_O_");
        cfg.addDefault("crafting.pickaxe.recipe.values.N","NETHER_STAR");
        cfg.addDefault("crafting.pickaxe.recipe.values.O","OBSIDIAN");

        saveFile();
    }


    /**
     * Generates a {@link RecipeValues} Object from the data, which is saved in this {@link PluginFile}.
     * @param path The path to the required values. It must be the whole path to the item. So for example
     *             <code>crafting.pickaxe.recipe</code> and the path must not end with an dot.
     * @return The generated {@link RecipeValues} Object with values of this {@link PluginFile}
     */
    public RecipeValues getRecipeInformation(String path){ //f.e path: crafting.pickaxe.recipe<relevant information>
        RecipeValues recipe = new RecipeValues(cfg.getString(path+".firstrow"),cfg.getString(path+".secondrow"),cfg.getString(path+".thirdrow"));

        HashMap<String, Object> values = (HashMap<String, Object>) cfg.getConfigurationSection(path+".values").getValues(false);

        for (String key: values.keySet()){
            recipe.addKeyPair(key.charAt(0), (String) values.get(key));
        }

        return recipe;

    }

    /**
     * Looks for a {@link String} at a specific path. Return the {@link String}, but with translated Color Codes, so that
     * minecraft can display them. As alternative color code the {@link Character} <code>'&'</code> is used.
     * @param path The path, where the requested {@link String} is located at.
     * @return {@link String} with translated color codes.
     */
    public String getTranslatedString(String path){
        return ChatColor.translateAlternateColorCodes('&',cfg.getString(path));
    }


    /**
     * Looks for a {@link List} at a specific path.
     * @param path The path, where the requested {@link List} is located.
     * @return {@link List} with translated Color Codes. The alternative color code is <code>'&'</code>
     */
    public List<String> getTranslatedStringList(String path){
        List<String> list = cfg.getStringList(path);
        List<String> editedList = new ArrayList<String>();
        for (String s :
                list) {
            editedList.add(ChatColor.translateAlternateColorCodes('&',s));
        }
        return editedList;
    }


    public int getInt(String path){
        return cfg.getInt(path);
    }

    public Material getMaterial(String path){
        Material material = Material.AIR;
        try {
            material = Material.valueOf(cfg.getString(path).toUpperCase());
        } catch (IllegalArgumentException e) {
            instance.getLogger().warning("The Material on the path '"+path+"' is invalid. The plugin won't work properly. So the plugin gets disabled");
            Bukkit.getPluginManager().disablePlugin(instance);
        }
        return material;
    }

    /**
     * Saves this {@link PluginFile} to the disk at the plugin datafolder location.
     */
    public void saveFile(){
        try {
            this.cfg.save(file);
        } catch (IOException e) {
            instance.getLogger().warning("The file "+file.getAbsolutePath()+" could not be saved.");
            e.printStackTrace();
        }
    }
}
