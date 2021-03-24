<<<<<<< HEAD:api/src/main/java/net/iceyleagons/icicle/api/plugin/RegisteredPlugin.java
package net.iceyleagons.icicle.api.plugin;
=======
package net.iceyleagons.icicle.registry;
>>>>>>> parent of e77d82b (:package: multi module):src/main/java/net/iceyleagons/icicle/registry/RegisteredPlugin.java

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
<<<<<<< HEAD:api/src/main/java/net/iceyleagons/icicle/api/plugin/RegisteredPlugin.java
import net.iceyleagons.icicle.api.commands.PluginCommandManager;
import net.iceyleagons.icicle.api.reflect.AutowiringHandler;
import net.iceyleagons.icicle.api.reflect.ClassScanningHandler;
=======
import net.iceyleagons.icicle.commands.system.PluginCommandManager;
import net.iceyleagons.icicle.reflect.AutowiringHandler;
import net.iceyleagons.icicle.reflect.ClassScanningHandler;
>>>>>>> parent of e77d82b (:package: multi module):src/main/java/net/iceyleagons/icicle/registry/RegisteredPlugin.java
import org.bukkit.plugin.java.JavaPlugin;

@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public class RegisteredPlugin {

    private final JavaPlugin javaPlugin;
    private final Class<?> mainClass;
    private final String rootPackageName;

    @Setter
    private ClassScanningHandler classScanningHandler;
    @Setter
    private AutowiringHandler autowiringHandler;
    @Setter
    private PluginCommandManager pluginCommandManager;

}
