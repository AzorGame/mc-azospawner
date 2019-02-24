package io.github.azorimor.azospawner.commands;

import io.github.azorimor.azospawner.AzoSpawner;
import io.github.azorimor.azospawner.utils.MessageHandler;
import io.github.azorimor.azospawner.utils.SpawnerDataUtils;
import io.github.azorimor.azospawner.utils.SpawnerItemTagType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GiveSpawnerCommand implements CommandExecutor, TabCompleter {


    private MessageHandler messageHandler;
    private AzoSpawner instance;
    private SpawnerDataUtils spawnerDataUtils;

    private List<String> availableTypesString;
    private List<String> digits;
    private String spawnerColor;

    public GiveSpawnerCommand(MessageHandler messageHandler, AzoSpawner instance) {
        this.messageHandler = messageHandler;
        this.instance = instance;
        this.spawnerDataUtils = new SpawnerDataUtils();
        List<EntityType> availableTypes = new ArrayList<EntityType>(Arrays.asList(EntityType.values()));
        availableTypesString = new ArrayList<String>(availableTypes.size());
        for (EntityType type :
                availableTypes) {
            availableTypesString.add(type.toString());
        }
        digits = new ArrayList<String>(Arrays.asList(new String[]{"1","2","3","4","5","6","7","8","9"}));
        this.spawnerColor = instance.getPluginFile().getTranslatedString("spawner.color");
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {


        if (commandSender instanceof Player) {

            if (commandSender.hasPermission("azospawner.command.givespawner")) {
                Player p = (Player) commandSender;

                if (args.length >= 1) {
                    // Syntax: /givespawner <entityType> [amount] [Player]

                    EntityType spawnMob = null;
                    try {
                        spawnMob = EntityType.valueOf(args[0].toUpperCase());
                    } catch (IllegalArgumentException e) {
                        messageHandler.sendNoEntityType(commandSender, args[0]);
                        return true;
                    }

                    if (args.length >= 2) {
                        try {
                            int amount = Integer.valueOf(args[1]);

                            if(amount <= 0){
                                amount = Math.abs(amount);
                                if(amount == 0)
                                    amount = 1;
                            }

                            if (args.length == 3) {
                                Player target = Bukkit.getPlayer(args[2]);
                                if (target != null) {
                                    giveSpawnerToPlayer(target, spawnMob, amount);
                                    messageHandler.sendCommandGiveSpawnerOtherSuccess(commandSender,spawnMob,amount,target);

                                } else {
                                    messageHandler.sendPlayerOffline(commandSender, args[2]);
                                }

                            } else {
                                giveSpawnerToPlayer(p, spawnMob, amount);
                            }

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

    /**
     * Gives the {@link Player} the requested spawner and sends him a message, which contains, that he
     * recived the spawner.
     * @param target The {@link Player} who should recive the spawner and the message.
     * @param entityType The {@link EntityType} which the spawner should spawn.
     * @param amount The amount of the spawner given to the player.
     */
    private void giveSpawnerToPlayer(Player target, EntityType entityType, int amount) {
        ItemStack spawner = new ItemStack(Material.SPAWNER, amount);

        ItemMeta spawnerMeta = spawner.getItemMeta();

        spawnerMeta.getCustomTagContainer().setCustomTag(new NamespacedKey(instance, "mobspawntype"),
                new SpawnerItemTagType(), entityType);
        spawnerMeta.setDisplayName(spawnerColor + entityType.toString());

        spawner.setItemMeta(spawnerMeta);

        target.getInventory().addItem(spawner);

        messageHandler.sendCommandGiveSpawnerSuccess(target, entityType, amount);
    }

    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {

        List<String> completions = new ArrayList<String>();

        switch (args.length){
            case 1:
                StringUtil.copyPartialMatches(args[0], spawnerDataUtils.getAvailableMobsString(),completions);
                break;
            case 2:
                StringUtil.copyPartialMatches(args[1],digits,completions);
                break;
            case 3:
                List<String> playernames = new ArrayList<String>(Bukkit.getOnlinePlayers().size());
                for (Player online :
                        Bukkit.getOnlinePlayers()) {
                    playernames.add(online.getName());
                }
                StringUtil.copyPartialMatches(args[2],playernames,completions);
                break;
        }
        return completions;
    }
}
