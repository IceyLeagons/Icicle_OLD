package net.iceyleagons.icicle.api.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public interface IciclePluginManager {

    void register(RegisteredPlugin registeredPlugin);
    RegisteredPlugin get(JavaPlugin javaPlugin);
    void unregister(JavaPlugin registeredPlugin);
    void shutdown();

}
