package io.github.azorimor.azospawner.utils;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UpdateChecker {

    private int projectID;
    private URL checkURL;
    private String newVersion;
    private JavaPlugin plugin;

    public UpdateChecker(int projectID, JavaPlugin plugin) {
        this.projectID = projectID;
        this.plugin = plugin;
        this.newVersion = plugin.getDescription().getVersion();
        try {
            checkURL = new URL("https://api.spigotmc.org/legacy/update.php?resource="+projectID);
        } catch (MalformedURLException e) {
            plugin.getLogger().warning("Could not connect to the Spigot API. Plugin will not be disabled, but you can't recive update news now.");
        }
    }

    public String getResourceUrl(){
        return "https://spigotmc.org/resources/"+projectID;
    }
    public boolean checkForUpdate() throws IOException {
        URLConnection con = checkURL.openConnection();
        newVersion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
        return !plugin.getDescription().getVersion().equals(newVersion);
    }
}
