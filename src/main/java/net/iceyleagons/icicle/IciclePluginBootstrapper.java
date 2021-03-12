package net.iceyleagons.icicle;

import com.google.common.base.Preconditions;
import net.iceyleagons.icicle.registry.RegisteredPlugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Statically accessible class to register and bootstrap plugins built with Icicle
 *
 * @version 1.0.0
 * @since 2.0.0-SNAPSHOT
 * @author TOTHTOMI
 */
public class IciclePluginBootstrapper {

    /**
     * @param javaPlugin the plugin
     * @param rootPackage the rootPackage of your plugin, it's usually: groupName.artifactName
     */
    public static void bootstrap(JavaPlugin javaPlugin, String rootPackage) {
        Preconditions.checkArgument(Icicle.enabled, new IllegalStateException("Icicle is not yet enabled. (Did you set it up as a dependency in your plugin.yml?)"));

        RegisteredPlugin registeredPlugin = new RegisteredPlugin(javaPlugin, javaPlugin.getClass(), rootPackage);
        Icicle.pluginRegistry.register(registeredPlugin);

    }

    public static RegisteredPlugin get(JavaPlugin javaPlugin) {
        Preconditions.checkArgument(Icicle.enabled, new IllegalStateException("Icicle is not yet enabled. (Did you set it up as a dependency in your plugin.yml?)"));

        return Icicle.pluginRegistry.get(javaPlugin);
    }
}
