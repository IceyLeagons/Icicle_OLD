package net.iceyleagons.icicle.api;

import com.google.common.base.Preconditions;
import net.iceyleagons.icicle.api.plugin.RegisteredPlugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * Statically accessible class to register and bootstrap plugins built with Icicle
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 2.0.0-SNAPSHOT
 */
public class IciclePluginBootstrapper {

    private static final Logger logger = Logger.getLogger(IciclePluginBootstrapper.class.getName());

    private static IciclePlugin instance = null;

    /**
     * @param javaPlugin  the plugin
     * @param rootPackage the rootPackage of your plugin, it's usually: groupName.artifactName
     */
    public static RegisteredPlugin bootstrap(JavaPlugin javaPlugin, String rootPackage) {
        Preconditions.checkNotNull(instance, new IllegalStateException("Icicle is not yet enabled. (Did you set it up as a dependency in your plugin.yml?)"));
        Preconditions.checkArgument(instance.getIciclePluginManager().get(javaPlugin) != null, new IllegalStateException("Plugin already registered!")) ;

        RegisteredPlugin registeredPlugin = new RegisteredPlugin(javaPlugin, javaPlugin.getClass(), rootPackage);
        instance.getIciclePluginManager().register(registeredPlugin);
        return registeredPlugin;

    }

    public static RegisteredPlugin get(JavaPlugin javaPlugin) {
        Preconditions.checkNotNull(instance, new IllegalStateException("Icicle is not yet enabled. (Did you set it up as a dependency in your plugin.yml?)"));

        return instance.getIciclePluginManager().get(javaPlugin);
    }

    public static void registerIcicleInstance(IciclePlugin iciclePlugin) {
        Preconditions.checkArgument(instance == null, String.format("An Icicle instance is already registered from %s",
                iciclePlugin.getPlugin().getName()));

        logger.info(String.format("[->] Registering Icicle instance provider from %s", iciclePlugin.getPlugin().getName()));
        instance = iciclePlugin;
    }

    public static Optional<IciclePlugin> getIcicleInstance() {
        return Optional.ofNullable(instance);
    }

}
