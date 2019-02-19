package io.github.azorimor.azospawner;

import org.bukkit.plugin.java.JavaPlugin;

public class AzoSpawner extends JavaPlugin {

    

    @Override
    public void onEnable() {
        super.onEnable();

        registerListeners();
        registerCommands();
    }


    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void registerListeners(){

    }

    private void registerCommands(){

    }

}
