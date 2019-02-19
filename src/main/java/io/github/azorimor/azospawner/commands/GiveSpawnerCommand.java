package io.github.azorimor.azospawner.commands;

import io.github.azorimor.azospawner.utils.MessageHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveSpawnerCommand implements CommandExecutor {


    private MessageHandler messageHandler;

    public GiveSpawnerCommand(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {


        if(commandSender instanceof Player){

            if(commandSender.hasPermission("azospawner.command.givespawner")){
                Player p = (Player) commandSender;

                if(args.length >= 2){
                    //TODO command weitermachen
                } else {
                    messageHandler.sendWrongCommandUsage(commandSender,command);
                }


            } else {
                messageHandler.sendNoPermission(commandSender,command);
            }

        } else {
            messageHandler.sendNoPlayer(commandSender);
        }

        return true;
    }
}
