package io.github.azorimor.azospawner.commands;

import io.github.azorimor.azospawner.AzoSpawner;
import io.github.azorimor.azospawner.utils.MessageHandler;
import io.github.azorimor.azospawner.utils.SpawnerDataUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MobSpawningCommand implements CommandExecutor, TabCompleter {

    // /mobspawning <allow|disallow|info> [entityType]

    private MessageHandler messageHandler;
    private AzoSpawner instance;
    private List<String> arg1Options;
    private SpawnerDataUtils spawnerDataUtils;
    private List<String> empty;

    public MobSpawningCommand(AzoSpawner instance) {
        this.instance = instance;
        this.messageHandler = instance.getMessageHandler();

        this.arg1Options = new ArrayList<String>(Arrays.asList(new String[]{"allow","disallow","info"}));
        spawnerDataUtils = new SpawnerDataUtils();
        this.empty = new ArrayList<String>(1);
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender.hasPermission("azospawner.command.mobspawning")) {

            if (args.length >= 1) {

                if (args[0].equalsIgnoreCase("allow")) {

                    if (args.length == 2) {

                        try {
                            EntityType allowed = EntityType.valueOf(args[1].toUpperCase());
                            if (instance.getSpawnerManager().allowSpawning(allowed)) {
                                messageHandler.sendPluginMessage(commandSender, "Du hast den Spawntyp aktiviert.");
                            } else {
                                messageHandler.sendPluginMessage(commandSender, "Der Typ war bereits aktiviert.");
                            }
                        } catch (IllegalArgumentException e) {
                            messageHandler.sendNoEntityType(commandSender, args[1]);
                        }

                    } else if (args.length == 1) {

                        if (instance.getSpawnerManager().enableAll()) {
                            messageHandler.sendPluginMessage(commandSender, "Alle wurden aktiviert.");
                        } else {
                            messageHandler.sendPluginMessage(commandSender, "Alle waren bereits aktiviert.");
                        }

                    } else {
                        messageHandler.sendWrongCommandUsage(commandSender, command);
                    }

                } else if (args[0].equalsIgnoreCase("disallow")) {


                    if (args.length == 2) {

                        try {
                            EntityType allowed = EntityType.valueOf(args[1].toUpperCase());
                            if (instance.getSpawnerManager().disAllowSpawning(allowed)) {
                                messageHandler.sendPluginMessage(commandSender, "Du hast den Spawntyp deaktiviert.");
                            } else {
                                messageHandler.sendPluginMessage(commandSender, "Der Typ war bereits deaktiviert.");
                            }
                        } catch (IllegalArgumentException e) {
                            messageHandler.sendNoEntityType(commandSender, args[1]);
                        }

                    } else if (args.length == 1) {

                        if (instance.getSpawnerManager().disableAll()) {
                            messageHandler.sendPluginMessage(commandSender, "Alle wurden deaktiviert.");
                        } else {
                            messageHandler.sendPluginMessage(commandSender, "Alle waren bereits deaktiviert.");
                        }

                    } else {
                        messageHandler.sendWrongCommandUsage(commandSender, command);
                    }


                } else if (args[0].equalsIgnoreCase("info")) {


                    messageHandler.sendPluginMessage(commandSender,"The following are disabled: "+ instance.getSpawnerManager().getForbiddenString());

                } else {
                    messageHandler.sendWrongCommandUsage(commandSender, command);
                }

            } else {
                messageHandler.sendWrongCommandUsage(commandSender, command);
            }

        } else {
            messageHandler.sendNoPermission(commandSender, command);
        }

        return true;
    }

    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {

        List<String> completions = new ArrayList<String>();

        switch (args.length){
            case 1:
                StringUtil.copyPartialMatches(args[0], arg1Options,completions);
                break;
            case 2:
                if(args[0].equalsIgnoreCase("info"))
                    return this.empty;
                StringUtil.copyPartialMatches(args[1],spawnerDataUtils.getAvailableMobsString(),completions);
                break;
        }
        return completions;
    }
}
