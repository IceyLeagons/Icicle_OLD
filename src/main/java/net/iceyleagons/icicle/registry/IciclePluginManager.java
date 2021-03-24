package net.iceyleagons.icicle;

<<<<<<< HEAD:core/src/main/java/net/iceyleagons/icicle/IciclePluginManagerImpl.java
import net.iceyleagons.icicle.api.plugin.IciclePluginManager;
import net.iceyleagons.icicle.api.plugin.RegisteredPlugin;
import net.iceyleagons.icicle.commands.system.PluginCommandManagerImpl;
import net.iceyleagons.icicle.reflect.impl.ClassScanningHandlerImpl;
=======
import net.iceyleagons.icicle.commands.system.PluginCommandManager;
import net.iceyleagons.icicle.reflect.ClassScanningHandler;
>>>>>>> parent of e77d82b (:package: multi module):src/main/java/net/iceyleagons/icicle/registry/IciclePluginManager.java
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class IciclePluginManager {

    private final Map<JavaPlugin, RegisteredPlugin> registeredPluginList = new HashMap<>();

    public void register(RegisteredPlugin registeredPlugin) {
        registeredPlugin.setPluginCommandManager(new PluginCommandManager(registeredPlugin));
        registeredPlugin.setClassScanningHandler(new ClassScanningHandler(registeredPlugin));
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
