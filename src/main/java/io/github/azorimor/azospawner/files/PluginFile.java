package io.github.azorimor.azospawner.files;

import io.github.azorimor.azospawner.AzoSpawner;
import io.github.azorimor.azospawner.utils.RecipeValues;
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

    public void copyDefaults(){
        cfg.options().copyDefaults(true);

        cfg.addDefault("prefix","&7[&cAzo&3Spawner&7] &r");
        cfg.addDefault("command.message.noPlayer","Kein Spieler");
        cfg.addDefault("command.message.noPermission","Keine Rechte");
        cfg.addDefault("command.message.wrongCommandUsage","Falsche Befehlsverwendung");
        cfg.addDefault("command.message.noNumber","Gib eine Zahl ein");
        cfg.addDefault("command.message.giveSpawner","Du hast den spawner erhalten.");
        cfg.addDefault("command.message.noEntityType","Bitte gieb einen gültigen EntityType an.");
        cfg.addDefault("command.message.playerOffline","Der Spieler ist offline.");
        cfg.addDefault("command.message.givePickaxe","Du hast die Spitzhacke erhalten.");
        cfg.addDefault("command.message.givePickaxeOther","Du hast die Spitzhacke vergeben.");



        cfg.addDefault("crafting.pickaxe.itemName","SpawnerPick");
        ArrayList<String> pickLore = new ArrayList<String>();
        pickLore.add("&7--------------------");
        pickLore.add("&6You can destroy Spawners");
        pickLore.add("&6by using this pickaxe.");
        pickLore.add("&7--------------------");
        cfg.addDefault("crafting.pickaxe.itemLore", pickLore);
        cfg.addDefault("crafting.pickaxe.recipe.firstrow","NNN");
        cfg.addDefault("crafting.pickaxe.recipe.secondrow","_O_");
        cfg.addDefault("crafting.pickaxe.recipe.thirdrow","_O_");
        cfg.addDefault("crafting.pickaxe.recipe.values.N","NETHER_STAR");
        cfg.addDefault("crafting.pickaxe.recipe.values.O","OBSIDIAN");

        saveFile();
    }


    public RecipeValues getRecipeInformation(String path){ //f.e path: crafting.pickaxe.recipe<relevant information>
        RecipeValues recipe = new RecipeValues(cfg.getString(path+".firstrow"),cfg.getString(path+".secondrow"),cfg.getString(path+".thirdrow"));

        HashMap<String, Object> values = (HashMap<String, Object>) cfg.getConfigurationSection(path+".values").getValues(false);

        for (String key: values.keySet()){
            recipe.addKeyPair(key.charAt(0), (String) values.get(key));
        }

        return recipe;

    }

    public String getTranslatedString(String path){
        return ChatColor.translateAlternateColorCodes('&',cfg.getString(path));
    }


    public List<String> getTranslatedStringList(String path){
        List<String> list = cfg.getStringList(path);
        List<String> editedList = new ArrayList<String>();
        for (String s :
                list) {
            editedList.add(ChatColor.translateAlternateColorCodes('&',s));
        }
        return editedList;
    }


    public void saveFile(){
        try {
            this.cfg.save(file);
        } catch (IOException e) {
            instance.getLogger().warning("The file "+file.getAbsolutePath()+" could not be saved.");
            e.printStackTrace();
        }
    }
}
