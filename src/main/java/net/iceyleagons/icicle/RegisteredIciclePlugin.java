package net.iceyleagons.icicle;

import lombok.Getter;
import net.iceyleagons.icicle.beans.BeanCreator;
import net.iceyleagons.icicle.beans.ClassScanner;
import net.iceyleagons.icicle.beans.RegisteredBeanDictionary;
import net.iceyleagons.icicle.utils.Asserts;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

@Getter
public class RegisteredIciclePlugin {

    private final JavaPlugin javaPlugin;
    private final Reflections reflections;
    private final ClassScanner classScanner;
    private final RegisteredBeanDictionary registeredBeanDictionary;
    private final BeanCreator beanCreator;

    public RegisteredIciclePlugin(JavaPlugin javaPlugin, String mainPackage) {
        Asserts.notNull(javaPlugin, "JavaPlugin must not be null!");
        Asserts.notEmpty(mainPackage, "Main package must not be empty or null!");

        this.javaPlugin = javaPlugin;
        this.reflections = new Reflections(mainPackage).merge(new Reflections("net.iceyleagons.icicle"));
        this.classScanner = new ClassScanner(reflections);
        this.registeredBeanDictionary = new RegisteredBeanDictionary();
        this.beanCreator = new BeanCreator(this);

        this.registeredBeanDictionary.registerBean(this);
        this.registeredBeanDictionary.registerBean(javaPlugin);
        this.registeredBeanDictionary.registerBean(javaPlugin.getServer());
        this.registeredBeanDictionary.registerBean(javaPlugin.getServer().getPluginManager());

        init();
    }

    private void init() {
        this.beanCreator.scanAndCreateAutoCreationClasses();
    }

}
