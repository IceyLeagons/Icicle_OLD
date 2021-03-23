package net.iceyleagons.icicle.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Autowiring equivalent {@link org.bukkit.plugin.PluginManager#getPlugin(String)}
 *
 * @version 1.0.0
 * @since 2.0.0
 * @author TOTHTOMI
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Plugin {

    /**
     * @return the name of the plugin, you would write in {@link org.bukkit.plugin.PluginManager#getPlugin(String)}
     */
    String name();

    /**
     * @return the main class, so we can cast the plugin automatically for you.
     */
    Class<?> main();
    
}
