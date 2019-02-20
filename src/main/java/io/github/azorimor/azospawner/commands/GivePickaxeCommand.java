package io.github.azorimor.azospawner.commands;

import io.github.azorimor.azospawner.recipe.SpawnerPickaxeRecipe;
import io.github.azorimor.azospawner.utils.MessageHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GivePickaxeCommand implements CommandExecutor {


    private MessageHandler messageHandler;

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(commandSender instanceof Player){

            if(commandSender.hasPermission("azospawner.command.givepickaxe")){

                if(args.length <=1){

                    Player player = (Player) commandSender;

                    if(args.length == 0){
                        //TODO Item geben
                    } else {
                        Player target = Bukkit.getPlayer(args[0]);
                        if(target != null){
                            //TODO item geben
                        } else {
                            //TODO Nachricht Spieler offline
                        }
                    }

                }else {
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
