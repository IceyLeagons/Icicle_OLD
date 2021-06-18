package net.iceyleagons.icicle;

import net.iceyleagons.icicle.utils.Asserts;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Utility class for easier and faster bootstrapping of Icicle plugins, with static access, aka. no need to check whether Icicle is properly
 * enabled, this class checks that automatically.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public class IcicleBootstrapper {

    /**
     * Bootstraps the Icicle plugin.
     *
     * @param javaPlugin the {@link JavaPlugin}
     * @param mainPackage the root package of the plugin (ex.: net.iceyleagons.icicle or net.iceyleagons.bingo)
     * @return the {@link RegisteredIciclePlugin} (you can also always use {@link IciclePluginManager#get(JavaPlugin)})
     */
    public static RegisteredIciclePlugin bootstrap(JavaPlugin javaPlugin, String mainPackage) {
        Asserts.isTrue(Icicle.getInstance().isPresent(), "Icicle instance is not registered!");

        Icicle icicle = Icicle.getInstance().get();
        Asserts.isTrue(icicle.isEnabled(), "Icicle is not enabled!");

        return icicle.getIciclePluginManager().register(javaPlugin, mainPackage);
    }
}
