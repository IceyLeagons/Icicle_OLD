package net.iceyleagons.icicle;

import net.iceyleagons.icicle.api.plugin.IciclePluginManager;
import net.iceyleagons.icicle.api.plugin.RegisteredPlugin;
import net.iceyleagons.icicle.commands.system.PluginCommandManagerImpl;
import net.iceyleagons.icicle.reflect.impl.ClassScanningHandlerImpl;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class IciclePluginManagerImpl implements IciclePluginManager {

    private final Map<JavaPlugin, RegisteredPlugin> registeredPluginList = new HashMap<>();

    @Override
    public void register(RegisteredPlugin registeredPlugin) {
        registeredPlugin.setPluginCommandManager(new PluginCommandManagerImpl(registeredPlugin));
        registeredPlugin.setClassScanningHandler(new ClassScanningHandlerImpl(registeredPlugin));
        registeredPluginList.put(registeredPlugin.getJavaPlugin(), registeredPlugin);

    }

    @Override
    public RegisteredPlugin get(JavaPlugin javaPlugin) {
        return registeredPluginList.get(javaPlugin);
    }

    @Override
    public void unregister(JavaPlugin registeredPlugin) {
        registeredPluginList.remove(registeredPlugin);
    }

    @Override
    public void shutdown() {
        registeredPluginList.values().forEach(plugin -> {

        });
    }
}
