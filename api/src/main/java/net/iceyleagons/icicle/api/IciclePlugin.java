package net.iceyleagons.icicle.api;

import net.iceyleagons.icicle.api.plugin.IciclePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public interface IciclePlugin {

    JavaPlugin getPlugin();
    IciclePluginManager getIciclePluginManager();
    String getVersion();
    String getCopyrightText();

}
