package io.github.azorimor.azospawner.commands;

import io.github.azorimor.azospawner.AzoSpawner;
import io.github.azorimor.azospawner.utils.MessageHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PluginHelpCommand implements CommandExecutor, TabCompleter {

    private MessageHandler messageHandler;
    private final List<String> messages;
    private final List<String> completions;

    public PluginHelpCommand(MessageHandler messageHandler, AzoSpawner instance) {
        this.messageHandler = messageHandler;

        this.messages = new ArrayList<String>();

        PluginDescriptionFile descriptionFile = instance.getDescription();
        this.messages.add("&7«&m                                                  &r&7»");
        this.messages.add("&7Plugin: &b"+instance.getName());
        this.messages.add("&7Version: &b"+ descriptionFile.getVersion());
        try {
            if(instance.getUpdateChecker().checkForUpdate()){
                this.messages.add("&7Update: &bavailable");
                this.messages.add("&7(&b "+instance.getUpdateChecker().getResourceUrl()+" &7)");
            } else {
                this.messages.add("&7Update: &bnone");
            }
        } catch (IOException e) {
            this.messages.add("&7Updates: &bCan not check for updates.");
        }
        this.messages.add("&7Author: &b"+ descriptionFile.getAuthors().get(0));
        this.messages.add("&7Website: &b"+ descriptionFile.getWebsite());
        this.messages.add("&7«&m                                                  &r&7»");

        this.completions = new ArrayList<String>(1);
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(commandSender.hasPermission("azospawner.command.help")){

            messageHandler.sendPluginMessage(commandSender,messages);

        } else {
            messageHandler.sendNoPermission(commandSender,command);
        }

        return true;
    }

    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {

        return completions;
    }
}
