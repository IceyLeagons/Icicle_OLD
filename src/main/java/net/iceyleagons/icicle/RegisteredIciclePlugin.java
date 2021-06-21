package net.iceyleagons.icicle;

import lombok.Getter;
import net.iceyleagons.icicle.beans.BeanCreator;
import net.iceyleagons.icicle.beans.ClassScanner;
import net.iceyleagons.icicle.beans.RegisteredBeanDictionary;
import net.iceyleagons.icicle.commands.inject.CommandInjector;
import net.iceyleagons.icicle.utils.Asserts;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RegisteredIciclePlugin {

    private final JavaPlugin javaPlugin;
    private final Reflections reflections;
    private final ClassScanner classScanner;
    private final RegisteredBeanDictionary registeredBeanDictionary;
    private final BeanCreator beanCreator;
    private final CommandInjector commandInjector;

    private final List<Runnable> onDisabledRunnables = new ArrayList<>();

    public RegisteredIciclePlugin(JavaPlugin javaPlugin, String mainPackage) {
        Asserts.notNull(javaPlugin, "JavaPlugin must not be null!");
        Asserts.notEmpty(mainPackage, "Main package must not be empty or null!");

        this.javaPlugin = javaPlugin;
        this.reflections = new Reflections(mainPackage).merge(new Reflections("net.iceyleagons.icicle"));
        this.registeredBeanDictionary = createDictionary();

        this.classScanner = new ClassScanner(reflections, registeredBeanDictionary);
        this.beanCreator = new BeanCreator(this);
        this.commandInjector = new CommandInjector(this);



        init();
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
