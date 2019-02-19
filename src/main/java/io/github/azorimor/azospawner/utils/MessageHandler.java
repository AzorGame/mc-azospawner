package io.github.azorimor.azospawner.utils;

import io.github.azorimor.azospawner.files.PluginFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageHandler  {

    private String prefix;

    private PluginFile pluginFile;


    private String noPlayer;
    private String noPermission;
    private String wrongCommandUsage;

    public MessageHandler(PluginFile pluginFile) {
        this.pluginFile = pluginFile;

        loadDefaults();
    }

    private void loadDefaults(){
        this.prefix = translateColorCodes(pluginFile.getString("prefix"));

        this.noPlayer = translateColorCodes(pluginFile.getString("command.message.noPlayer"));
        this.noPermission = translateColorCodes(pluginFile.getString("command.message.noPermission"));
        this.wrongCommandUsage = translateColorCodes(pluginFile.getString("command.message.wrongCommandUsage"));
    }

    /**
     * Translates the alternative Color Codes from spigot/minecraft. So the <code>'&'</code> are replaced, so
     * the messaage will be colored.
     * @param string Message, which should be translated
     * @return translated Message
     */
    public String translateColorCodes(String string){
        return string.replace('&','§');
    }

    /**
     * Sends the {@link CommandSender} the message, which contains, that he must be a
     * {@link Player} to perform this action.
     * The message is loaded from the configuration file.
     * @param sender {@link CommandSender} which should recieve the message.
     */
    public void sendNoPlayer(CommandSender sender){
        sender.sendMessage(prefix + noPlayer);
    }

    /**
     * Sends a {@link CommandSender} the message, that he does not have the permission
     * to perform a specific {@link Command}.
     * The message is loaded from the configuration file.
     * @param sender {@link CommandSender} which should recieve the message.
     * @param command The {@link Command} the {@link CommandSender} does not have enought
     *                permissions for.
     */
    public void sendNoPermission(CommandSender sender, Command command){
        sender.sendMessage(prefix + noPermission.replace("%command%",command.getName()));
    }

    /**
     * Sends a {@link CommandSender} the message, that he did not use a {@link Command} the right way.
     * The message is loaded from the configuration file.
     * @param sender {@link CommandSender} which should recieve the message.
     * @param command {@link Command} which was used the wrong way.
     */
    public void sendWrongCommandUsage(CommandSender sender, Command command){
        sender.sendMessage(prefix + wrongCommandUsage.replace("%command%",command.getName()).replace("%usage%",command.getUsage()));
    }


    public void reloadValues(){
        //TODO add more functionallity.
        loadDefaults();
    }
}
