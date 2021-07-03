package net.iceyleagons.icicle;

import lombok.Getter;
import lombok.Setter;
import net.iceyleagons.icicle.beans.BeanCreator;
import net.iceyleagons.icicle.beans.ClassScanner;
import net.iceyleagons.icicle.beans.RegisteredBeanDictionary;
import net.iceyleagons.icicle.commands.inject.CommandInjector;
import net.iceyleagons.icicle.utils.Asserts;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
public class RegisteredIciclePlugin {

    @Setter
    private boolean debug = false;

    private final JavaPlugin javaPlugin;
    private final Reflections reflections;
    private final ClassScanner classScanner;
    private final RegisteredBeanDictionary registeredBeanDictionary;
    private final BeanCreator beanCreator;
    private final CommandInjector commandInjector;
    private final Logger logger;

    private final List<Runnable> onDisabledRunnables = new ArrayList<>();

    public RegisteredIciclePlugin(JavaPlugin javaPlugin, String mainPackage) {
        Asserts.notNull(javaPlugin, "JavaPlugin must not be null!");
        Asserts.notEmpty(mainPackage, "Main package must not be empty or null!");

        this.javaPlugin = javaPlugin;
        this.logger = Logger.getLogger(javaPlugin.getName());
        //passing shit ton of classloader because Reflections is a piece of shit
        this.reflections = new Reflections(mainPackage,
                getClass().getClassLoader(),
                javaPlugin.getPluginLoader().getClass().getClassLoader(),
                javaPlugin.getClass().getClassLoader(),
                Icicle.class.getClassLoader(),
                Bukkit.getServer().getClass().getClassLoader()).merge(new Reflections("net.iceyleagons.icicle"));
        this.registeredBeanDictionary = createDictionary();

        this.classScanner = new ClassScanner(reflections, registeredBeanDictionary);
        this.beanCreator = new BeanCreator(this);
        this.commandInjector = new CommandInjector(this);


        init();
    }

    public void debug(String debugMsg) {
        logger.info("[DEBUG] " + debugMsg);
    }

    private RegisteredBeanDictionary createDictionary() {
        RegisteredBeanDictionary registeredBeanDictionary = new RegisteredBeanDictionary();

        registeredBeanDictionary.registerBean(this);
        registeredBeanDictionary.registerBean(javaPlugin);
        registeredBeanDictionary.registerBean(javaPlugin.getServer());
        registeredBeanDictionary.registerBean(javaPlugin.getServer().getPluginManager());

        return registeredBeanDictionary;
    }

    private void init() {
        this.beanCreator.scanAndCreateAutoCreationClasses();
    }

    /**
     * Should be called under {@link JavaPlugin#onDisable()}
     */
    public void onDisabled() {
        onDisabledRunnables.forEach(Runnable::run);
    }

}
