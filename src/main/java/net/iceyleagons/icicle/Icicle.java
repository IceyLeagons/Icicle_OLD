package net.iceyleagons.icicle;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public class Icicle extends JavaPlugin {

    public Icicle() {
        super();
    }

    protected Icicle(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    public static boolean enabled = false;

    public static String getVersion() {
        return "2.0.0-SNAPSHOT";
    }

    /**
     * This is optional to show us some love, by printing this out.
     * You'd ideally print this out to the console, or to the players.
     *
     * @return our copyright text
     */
    public static String getCopyrightText() {
        return "This project was built upon IceyLeagons' Icicle Library v" + getVersion() +
                " (Licensed under the terms of MIT License)";
    }

    @Override
    public void onEnable() {
        enabled = true;
    }
}
