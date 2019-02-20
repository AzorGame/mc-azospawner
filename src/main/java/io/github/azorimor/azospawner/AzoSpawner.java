package io.github.azorimor.azospawner;

import io.github.azorimor.azospawner.commands.GiveSpawnerCommand;
import io.github.azorimor.azospawner.files.PluginFile;
import io.github.azorimor.azospawner.listeners.BreakSpawnerListener;
import io.github.azorimor.azospawner.listeners.PlaceSpawnerListener;
import io.github.azorimor.azospawner.utils.MessageHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class AzoSpawner extends JavaPlugin {

    private PluginFile pluginFile;
    private MessageHandler messageHandler;

    @Override
    public void onEnable() {
        super.onEnable();

        this.pluginFile = new PluginFile(this);
        this.messageHandler = new MessageHandler(pluginFile);

        registerListeners();
        registerCommands();
    }


    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void registerListeners(){
        getServer().getPluginManager().registerEvents(new PlaceSpawnerListener(this),this);
        getServer().getPluginManager().registerEvents(new BreakSpawnerListener(messageHandler,this),this);
    }

    private void registerCommands(){
        getCommand("givespawner").setExecutor(new GiveSpawnerCommand(messageHandler, this));
    }

}
