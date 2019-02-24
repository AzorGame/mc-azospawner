package io.github.azorimor.azospawner.listeners;

import io.github.azorimor.azospawner.AzoSpawner;
import io.github.azorimor.azospawner.utils.MessageHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;

public class PlayerJoinListener implements Listener {

    private MessageHandler messageHandler;
    private AzoSpawner instance;

    public PlayerJoinListener(MessageHandler messageHandler, AzoSpawner instance) {
        this.messageHandler = messageHandler;
        this.instance = instance;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();

        if(player.hasPermission("azospawner.team.admin")){
            try {
                if(instance.getUpdateChecker().checkForUpdate()){
                    messageHandler.sendPluginMessage(player,"&7Updates found. Please visit the website to download it.");
                    messageHandler.sendPluginMessage(player,"&7"+instance.getUpdateChecker().getResourceUrl());
                }
            } catch (IOException e) {
                messageHandler.sendPluginMessage(player,"&7Could not check for updates. Please check your internet connection.");
            }

        }

    }


}
