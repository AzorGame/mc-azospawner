package io.github.azorimor.azospawner.commands;

import io.github.azorimor.azospawner.AzoSpawner;
import io.github.azorimor.azospawner.utils.MessageHandler;
import io.github.azorimor.azospawner.utils.SpawnerDataUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class KillMobsCommand implements CommandExecutor, TabCompleter {

    private AzoSpawner instance;
    private MessageHandler messageHandler;

    private List<String> worldsString;
    private SpawnerDataUtils spawnerDataUtils;

    public KillMobsCommand(AzoSpawner instance) {
        this.instance = instance;
        this.messageHandler = instance.getMessageHandler();

        this.worldsString = new ArrayList<String>(Bukkit.getWorlds().size());
        for (World world :
                Bukkit.getWorlds()) {
            worldsString.add(world.getName());
        }
        this.spawnerDataUtils = new SpawnerDataUtils();
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {


        if (commandSender.hasPermission("azospawner.command.killmobs")) {


            int count = 0;

            switch (args.length) { // killmobs [world] [entity]
                case 0:
                    for (World world :
                            Bukkit.getWorlds()) {
                        for (LivingEntity entity :
                                world.getLivingEntities()) {
                            if (entity instanceof Player)
                                continue;
                            entity.setHealth(0);
                            count++;
                        }
                        messageHandler.sendCommandKillMobsSuccess(commandSender,count,world.getName());
                        count = 0;
                    }
                    break;
                case 1:
                    World world = Bukkit.getWorld(args[0]);
                    if (world != null) {
                        for (LivingEntity entity :
                                world.getLivingEntities()) {
                            if (entity instanceof Player)
                                continue;
                            entity.setHealth(0);
                            count++;
                        }
                        messageHandler.sendCommandKillMobsSuccess(commandSender,count,world.getName());
                    } else {
                        messageHandler.sendNoWorld(commandSender,args[0]);
                    }
                    break;
                case 2:
                    world = Bukkit.getWorld(args[0]);
                    try {
                        EntityType toKill = EntityType.valueOf(args[1].toUpperCase());

                        if (world != null) {
                            for (LivingEntity entity :
                                    world.getLivingEntities()) {
                                if (entity instanceof Player)
                                    continue;

                                if (entity.getType() != toKill)
                                    continue;
                                entity.setHealth(0);
                                count++;
                            }
                            messageHandler.sendCommandKillMobsSuccess(commandSender,count,world.getName());
                        } else {
                            messageHandler.sendNoWorld(commandSender,args[0]);
                        }

                    } catch (IllegalArgumentException e) {
                        messageHandler.sendNoEntityType(commandSender, args[1]);
                    }
                    break;
                default:
                    messageHandler.sendWrongCommandUsage(commandSender, command);
                    break;
            }

        } else {
            messageHandler.sendNoPermission(commandSender, command);
        }

        return true;
    }

    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {

        List<String> completions = new ArrayList<String>();

        switch (args.length) {
            case 1:
                StringUtil.copyPartialMatches(args[0], worldsString, completions);
                break;
            case 2:
                StringUtil.copyPartialMatches(args[1], spawnerDataUtils.getAvailableMobsString(), completions);
                break;
        }
        return completions;
    }
}
