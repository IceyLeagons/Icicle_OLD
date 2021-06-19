package net.iceyleagons.icicle;

import net.iceyleagons.icicle.annotations.Autowired;
import net.iceyleagons.icicle.beans.AutowiringUtils;
import net.iceyleagons.icicle.beans.auto.TestService;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public class IcicleTestingPlugin extends JavaPlugin {

    @Autowired
    private TestService testService;

    public IcicleTestingPlugin() {
        super();
    }

    protected IcicleTestingPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    public String test() {
        return testService.hello();
    }


    @Override
    public void onEnable() {
        RegisteredIciclePlugin registeredIciclePlugin = IcicleBootstrapper.bootstrap(this, "net.iceyleagons.icicle");
        AutowiringUtils.autowireObject(this, registeredIciclePlugin.getRegisteredBeanDictionary());
    }
}
