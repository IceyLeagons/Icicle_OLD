package net.iceyleagons.icicle.common.plugin;

import net.iceyleagons.icicle.common.commands.system.PluginCommandManager;
import net.iceyleagons.icicle.common.scheduling.SchedulerService;
import net.iceyleagons.icicle.common.reflect.ClassScanningHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class IciclePluginManager {

    private final Map<JavaPlugin, RegisteredPlugin> registeredPluginList = new HashMap<>();

    public void register(RegisteredPlugin registeredPlugin) {
        registeredPlugin.setPluginCommandManager(new PluginCommandManager(registeredPlugin));
        registeredPlugin.setClassScanningHandler(new ClassScanningHandler(registeredPlugin));
        registeredPlugin.setSchedulerService(new SchedulerService(registeredPlugin));
        registeredPluginList.put(registeredPlugin.getJavaPlugin(), registeredPlugin);

    }

    public RegisteredPlugin get(JavaPlugin javaPlugin) {
        return registeredPluginList.get(javaPlugin);
    }

    public void unregister(JavaPlugin registeredPlugin) {
        registeredPluginList.remove(registeredPlugin);
    }

    public void shutdown() {
        registeredPluginList.values().forEach(plugin -> {

        });
    }
}
