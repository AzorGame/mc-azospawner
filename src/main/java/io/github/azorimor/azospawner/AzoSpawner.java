package io.github.azorimor.azospawner;

import io.github.azorimor.azospawner.commands.GivePickaxeCommand;
import io.github.azorimor.azospawner.commands.GiveSpawnerCommand;
import io.github.azorimor.azospawner.commands.KillMobsCommand;
import io.github.azorimor.azospawner.commands.PluginHelpCommand;
import io.github.azorimor.azospawner.files.PluginFile;
import io.github.azorimor.azospawner.listeners.*;
import io.github.azorimor.azospawner.listeners.CraftPickaxeListener;
import io.github.azorimor.azospawner.listeners.PlaceSpawnerListener;
import io.github.azorimor.azospawner.listeners.PlayerJoinListener;
import io.github.azorimor.azospawner.recipe.SpawnerPickaxeRecipe;
import io.github.azorimor.azospawner.utils.MessageHandler;
import io.github.azorimor.azospawner.utils.SpawnerManager;
import io.github.azorimor.azospawner.utils.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class AzoSpawner extends JavaPlugin {

    private PluginFile pluginFile;
    private MessageHandler messageHandler;
    private UpdateChecker updateChecker;

    private SpawnerPickaxeRecipe recipe;

    private SpawnerManager spawnerManager;

    @Override
    public void onEnable() {
        super.onEnable();

        this.pluginFile = new PluginFile(this);
        this.messageHandler = new MessageHandler(pluginFile);
        this.spawnerManager = new SpawnerManager();

        checkUpdates();

        registerListeners();
        registerCommands();
        registerRecipes();
    }



    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void registerListeners(){
        getServer().getPluginManager().registerEvents(new PlaceSpawnerListener(this),this);
        getServer().getPluginManager().registerEvents(new BreakSpawnerListener(messageHandler,this),this);

        getServer().getPluginManager().registerEvents(new CraftPickaxeListener(messageHandler,this),this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(messageHandler,this),this);
        getServer().getPluginManager().registerEvents(new PlayerInteractsSpawnerListener(this),this);

    }

    private void registerCommands(){
        GiveSpawnerCommand giveSpawnerCommand = new GiveSpawnerCommand(messageHandler, this);
        getCommand("givespawner").setExecutor(giveSpawnerCommand);
        getCommand("givespawner").setTabCompleter(giveSpawnerCommand);

        GivePickaxeCommand givePickaxeCommand = new GivePickaxeCommand(messageHandler,this);
        getCommand("givepickaxe").setExecutor(givePickaxeCommand);
        getCommand("givepickaxe").setTabCompleter(givePickaxeCommand);

        PluginHelpCommand pluginHelpCommand = new PluginHelpCommand(messageHandler,this);
        getCommand("azospawnerhelp").setExecutor(pluginHelpCommand);
        getCommand("azospawnerhelp").setTabCompleter(pluginHelpCommand);

        KillMobsCommand killMobsCommand = new KillMobsCommand(this);
        getCommand("killmobs").setExecutor(killMobsCommand);
        getCommand("killmobs").setTabCompleter(killMobsCommand);
    }

    private void registerRecipes(){
        this.recipe = new SpawnerPickaxeRecipe(this,pluginFile);
        Bukkit.addRecipe(recipe.getRecipe());
    }

    private void checkUpdates() {
        getLogger().info("Checking for updates...");
        try {
            this.updateChecker = new UpdateChecker(65072,this);
            if(updateChecker.checkForUpdate()){
                getLogger().info("Updates found. Please visit the website to download it.");
                getLogger().info(updateChecker.getResourceUrl());
            } else {
                getLogger().info("No updates found. You are up to date.");
            }
        } catch (IOException e) {
            getLogger().info("Could not check for updates. Please check your internet connection.");
        }
    }

    public PluginFile getPluginFile() {
        return pluginFile;
    }


    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    public SpawnerPickaxeRecipe getRecipe() {
        return recipe;
    }

    public UpdateChecker getUpdateChecker() {
        return updateChecker;
    }


    public SpawnerManager getSpawnerManager() {
        return spawnerManager;
    }
}
