package net.iceyleagons.icicle;

import net.iceyleagons.icicle.utils.Asserts;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class IciclePluginManager {

    private Map<JavaPlugin, RegisteredIciclePlugin> plugins = new HashMap<>();

    public RegisteredIciclePlugin register(JavaPlugin javaPlugin, String mainPackage) {
        Asserts.isTrue(!plugins.containsKey(javaPlugin), "Plugin already registered!");
        RegisteredIciclePlugin registeredIciclePlugin = new RegisteredIciclePlugin(javaPlugin, mainPackage);

        plugins.put(javaPlugin, registeredIciclePlugin);

        return registeredIciclePlugin;
    }

    public Optional<RegisteredIciclePlugin> get(JavaPlugin javaPlugin) {
        return Optional.ofNullable(plugins.get(javaPlugin));
    }
}
