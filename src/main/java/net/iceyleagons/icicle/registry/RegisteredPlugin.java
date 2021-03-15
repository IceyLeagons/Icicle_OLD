package net.iceyleagons.icicle.registry;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.iceyleagons.icicle.commands.system.PluginCommandManager;
import net.iceyleagons.icicle.reflect.AutowiringHandler;
import net.iceyleagons.icicle.reflect.ClassScanningHandler;
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
