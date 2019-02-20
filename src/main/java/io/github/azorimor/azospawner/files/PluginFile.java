package io.github.azorimor.azospawner.files;

import io.github.azorimor.azospawner.AzoSpawner;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

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

        saveFile();
    }

    public String getString(String path){
        return cfg.getString(path);
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
