package io.github.azorimor.azospawner.commands;

import io.github.azorimor.azospawner.AzoSpawner;
import io.github.azorimor.azospawner.utils.MessageHandler;
import io.github.azorimor.azospawner.utils.SpawnerItemTagType;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GiveSpawnerCommand implements CommandExecutor {


    private MessageHandler messageHandler;
    private AzoSpawner instance;

    public GiveSpawnerCommand(MessageHandler messageHandler, AzoSpawner instance) {
        this.messageHandler = messageHandler;
        this.instance = instance;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {


        if (commandSender instanceof Player) {

            if (commandSender.hasPermission("azospawner.command.givespawner")) {
                Player p = (Player) commandSender;

                if (args.length >= 1) {
                    // Syntax: /givespawner <entityType> [amount]

                    EntityType spawnMob = null;
                    System.out.println("Args[0]: "+args[0]);
                    try {
                        spawnMob = EntityType.valueOf(args[0].toUpperCase());
                    } catch (IllegalArgumentException e) {
                        messageHandler.sendNoEntityType(commandSender,args[0]);
                        return true;
                    }

                    if (args.length == 2) {
                        try {
                            int amount = Integer.valueOf(args[1]);

                            giveSpawnerToPlayer(p, spawnMob, amount);

                        } catch (NumberFormatException e) {
                            messageHandler.sendNoNumber(commandSender, args[1]);
                        }
                    } else {
                        giveSpawnerToPlayer(p, spawnMob, 1);
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

    private void giveSpawnerToPlayer(Player target, EntityType entityType, int amount) {
        ItemStack spawner = new ItemStack(Material.SPAWNER, amount);

        ItemMeta spawnerMeta = spawner.getItemMeta();

        spawnerMeta.getCustomTagContainer().setCustomTag(new NamespacedKey(instance, "mobspawntype"),
                new SpawnerItemTagType(), entityType);
        spawnerMeta.setDisplayName("§3"+entityType.toString());

        spawner.setItemMeta(spawnerMeta);

        target.getInventory().addItem(spawner);

        messageHandler.sendCommandGiveSpawnerSuccess(target, entityType, amount);
    }

}
