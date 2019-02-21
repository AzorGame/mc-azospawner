package io.github.azorimor.azospawner.commands;

import io.github.azorimor.azospawner.AzoSpawner;
import io.github.azorimor.azospawner.utils.MessageHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GivePickaxeCommand implements CommandExecutor {


    private MessageHandler messageHandler;
    private AzoSpawner instance;

    public GivePickaxeCommand(MessageHandler messageHandler, AzoSpawner instance) {
        this.messageHandler = messageHandler;
        this.instance = instance;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender instanceof Player) {

            if (commandSender.hasPermission("azospawner.command.givepickaxe")) {

                if (args.length <= 1) {

                    Player player = (Player) commandSender;

                    if (args.length == 0) {
                        player.getInventory().addItem(instance.getRecipe().getPickaxe());
                        messageHandler.sendCommandGivePickaxeSuccess(commandSender);
                    } else {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            target.getInventory().addItem(instance.getRecipe().getPickaxe());
                            messageHandler.sendCommandGivePickaxeOtherSuccess(commandSender, target);
                        } else {
                            messageHandler.sendPlayerOffline(commandSender, args[0]);
                        }
                    }

                } else {
                    messageHandler.sendWrongCommandUsage(commandSender, command);
                }

            } else {
                messageHandler.sendNoPermission(commandSender, command);
            }

        } else {
            messageHandler.sendNoPlayer(commandSender);
        }
        return true;
    }
}
