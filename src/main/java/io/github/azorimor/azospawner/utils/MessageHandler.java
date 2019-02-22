package io.github.azorimor.azospawner.utils;

import io.github.azorimor.azospawner.files.PluginFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Recipe;

import java.util.List;


public class MessageHandler {

    private String prefix;

    private PluginFile pluginFile;


    private String noPlayer;
    private String noPermission;
    private String wrongCommandUsage;
    private String noNumber;
    private String commandGiveSpawner;
    private String commandGiveSpawnerOther;
    private String noEntityType;
    private String playerOffline;
    private String commandGivePickaxe;
    private String commandGivePickaxeOther;
    private String recipeNoPermission;

    public MessageHandler(PluginFile pluginFile) {
        this.pluginFile = pluginFile;

        loadDefaults();
    }

    private void loadDefaults() {
        this.prefix = translateColorCodes(pluginFile.getTranslatedString("prefix"));

        this.noPlayer = pluginFile.getTranslatedString("command.message.noPlayer");
        this.noPermission = pluginFile.getTranslatedString("command.message.noPermission");
        this.wrongCommandUsage = pluginFile.getTranslatedString("command.message.wrongCommandUsage");
        this.noNumber = pluginFile.getTranslatedString("command.message.noNumber");
        this.commandGiveSpawner = pluginFile.getTranslatedString("command.message.giveSpawner");
        this.commandGiveSpawnerOther = pluginFile.getTranslatedString("command.message.giveSpawnerOther");
        this.noEntityType = pluginFile.getTranslatedString("command.message.noEntityType");
        this.playerOffline = pluginFile.getTranslatedString("command.message.playerOffline");
        this.commandGivePickaxe = pluginFile.getTranslatedString("command.message.givePickaxe");
        this.commandGivePickaxeOther = pluginFile.getTranslatedString("command.message.givePickaxeOther");
        this.recipeNoPermission = pluginFile.getTranslatedString("crafting.message.noPermission");
    }

    /**
     * Translates the alternative Color Codes from spigot/minecraft. So the <code>'&'</code> are replaced, so
     * the messaage will be colored.
     *
     * @param string Message, which should be translated
     * @return translated Message
     */
    public String translateColorCodes(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    /**
     * Sends the {@link CommandSender} the message, which contains, that he must be a
     * {@link Player} to perform this action.
     * The message is loaded from the configuration file.
     *
     * @param sender {@link CommandSender} which should recieve the message.
     */
    public void sendNoPlayer(CommandSender sender) {
        sender.sendMessage(prefix + noPlayer);
    }

    /**
     * Sends a {@link CommandSender} the message, that he does not have the permission
     * to perform a specific {@link Command}.
     * The message is loaded from the configuration file.
     *
     * @param sender  {@link CommandSender} which should recieve the message.
     * @param command The {@link Command} the {@link CommandSender} does not have enought
     *                permissions for.
     */
    public void sendNoPermission(CommandSender sender, Command command) {
        sender.sendMessage(prefix + noPermission.replace("%command%", command.getName()));
    }

    /**
     * Sends a {@link CommandSender} the message, that he did not use a {@link Command} the right way.
     * The message is loaded from the configuration file.
     *
     * @param sender  {@link CommandSender} which should recieve the message.
     * @param command {@link Command} which was used the wrong way.
     */
    public void sendWrongCommandUsage(CommandSender sender, Command command) {
        sender.sendMessage(prefix + wrongCommandUsage.replace("%command%", command.getName()).replace("%usage%", command.getUsage()));
    }

    //TODO comment
    public void sendPluginMessage(CommandSender sender, String message) {
        sender.sendMessage(prefix + translateColorCodes(message));
    }

    public void sendPluginMessage(CommandSender sender, List<String> messages) {
        for (String msg :
                messages) {
            sendPluginMessage(sender, msg);
        }
    }

    public void sendTeamBroadcast(String message) {
        for (Player team :
                Bukkit.getOnlinePlayers()) {
            if (team.hasPermission("azospawner.team.member") || team.hasPermission("azospawner.team.admin")) {
                team.sendMessage(prefix + translateColorCodes(message));
            }
        }
    }

    /**
     * Sends a {@link CommandSender} the message, that he need to use a number as a specifig argument in a {@link Command}
     *
     * @param sender        {@link CommandSender} which should recieve the message.
     * @param wrongArgument The wrong Argument, which needs to be a number. This values is used as feedback to the {@link CommandSender}
     */
    public void sendNoNumber(CommandSender sender, String wrongArgument) {
        sender.sendMessage(prefix + noNumber.replace("%wrongArgument%", wrongArgument));
    }

    //TODO comment
    public void sendCommandGiveSpawnerSuccess(Player player, EntityType entityType, int amount) {
        player.sendMessage(prefix + commandGiveSpawner.replace("%type%", entityType.toString()).replace("%amount%", String.valueOf(amount)));
    }


    public void sendCommandGiveSpawnerOtherSuccess(CommandSender sender, EntityType type, int amount, Player target) {
        sender.sendMessage(prefix + commandGiveSpawnerOther
                .replace("%target%", target.getDisplayName())
                .replace("%amount%", String.valueOf(amount))
                .replace("%type%", type.toString()));
    }


    public void sendNoEntityType(CommandSender commandSender, String noEntityType) {
        commandSender.sendMessage(prefix + this.noEntityType.replace("%wrongType%", noEntityType));
    }

    public void sendPlayerOffline(CommandSender sender, String targetPlayerName) {
        sender.sendMessage(prefix + playerOffline.replace("%player%", targetPlayerName));
    }

    public void sendCommandGivePickaxeSuccess(CommandSender sender) {
        sender.sendMessage(prefix + commandGivePickaxe);
    }

    public void sendCommandGivePickaxeOtherSuccess(CommandSender sender, Player target) {
        sender.sendMessage(prefix + commandGivePickaxeOther.replace("%target%", target.getDisplayName()));
    }

    public void sendRecipeNoPermission(Player player, Recipe recipe) {
        player.sendMessage(prefix + recipeNoPermission.replace("%item%", recipe.getResult().getItemMeta().getDisplayName()));
    }

    public void reloadValues() {
        //TODO add more functionallity.
        loadDefaults();
    }
}
