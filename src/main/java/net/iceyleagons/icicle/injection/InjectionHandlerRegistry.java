package net.iceyleagons.icicle.injection;

import lombok.Getter;
import net.iceyleagons.icicle.injection.annotations.Autowired;
import net.iceyleagons.icicle.injection.annotations.InjectionHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.logging.Logger;

@Getter
public class InjectionHandlerRegistry {

    private Reflections rootPackage;
    private final JavaPlugin plugin;
    private final Logger logger = Logger.getLogger(InjectionHandlerRegistry.class.getName());
    private final Map<Class<? extends Annotation>, AbstractInjectionHandler> injectionHandlers = new HashMap();

    /**
     * Creates an instance of {@link InjectionHandlerRegistry}
     * In order for it to do something you should call {@link #init()}
     *
     * @param javaPlugin the {@link JavaPlugin}
     * @param rootPackage the main package of your plugin, so the group and artifact name ex. net.iceyleagons.icicle
     */
    public InjectionHandlerRegistry(JavaPlugin javaPlugin, String rootPackage) {
        this.rootPackage = new Reflections(rootPackage);
        this.rootPackage = this.rootPackage.merge(new Reflections("net.iceyleagons.icicle.injection")); //contributors! we don't want to add the entirety of Icicle here, to save time!
        this.rootPackage = this.rootPackage.merge(new Reflections("net.iceyleagons.icicle.config")); //   /\

        this.plugin = javaPlugin;
    }

    /**
     * Initializes the {@link InjectionHandlerRegistry}, with this all handlers will be loaded, and the
     * objects marked with @{@link Autowired} are automatically created and injected.
     */
    public void init() {
        loadHandlers();
        injectInjectableObjects();
    }

    /**
     * Will load in and register all the {@link InjectionHandler}s
     */
    private void loadHandlers() {
        Set<Class<?>> classes = rootPackage.getTypesAnnotatedWith(InjectionHandler.class);

        classes.forEach(clazz -> {
            if (clazz.getSuperclass().equals(AbstractInjectionHandler.class)) {
                Constructor<?> constructor = net.iceyleagons.icicle.reflect.Reflections.getConstructor(clazz, true);
                if (constructor == null) {
                    if (!clazz.isAnnotation())
                        logger.severe("Could not find empty constructor for AbstractInjectionHandler named " + clazz.getName());
                } else {
                    try {
                        AbstractInjectionHandler abstractInjectionHandler = (AbstractInjectionHandler) constructor.newInstance();

                        abstractInjectionHandler.setJavaPlugin(plugin);
                        abstractInjectionHandler.setInjectionHandlerRegistry(this);

                        InjectionHandler annotation = clazz.getAnnotation(InjectionHandler.class);
                        abstractInjectionHandler.setInjectionHandler(annotation);

                        injectionHandlers.put(annotation.value(), abstractInjectionHandler);
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        logger.severe("Could not initialize AbstractInjectionHandler named " + clazz.getName() + " due to an error!");
                        e.printStackTrace();
                    }
                }
            } else
                logger.warning(clazz.getName() + " is annotated with @InjectionHandler, but does not extend AbstractInjectionHandler");
        });
        injectionHandlers.values().forEach(AbstractInjectionHandler::postInitialization);
    }

    /**
     * Used to inject an object, that is not created by this handler.
     * Ideally you would call this first in a constructor.
     *
     * @param o the {@link Object} to inject
     */
    public void injectObject(Object o) {
        injectObject(o, o.getClass());
    }

    /**
     * Will create an inject all classes marked with @{@link Autowired}
     */
    private void injectInjectableObjects() {
        Set<Class<?>> classes = rootPackage.getTypesAnnotatedWith(Autowired.class);

        classes.forEach(clazz -> {
            Constructor<?> constructor = net.iceyleagons.icicle.reflect.Reflections.getConstructor(clazz, true);
            if (constructor == null) {
                if (!clazz.isAnnotation())
                    logger.severe("Could not find empty constructor for Injectable named " + clazz.getName());
            } else {
                try {
                    Object object = constructor.newInstance();
                    injectObject(object, clazz);
                } catch (Exception e) {
                    logger.severe("Could not initialize class " + clazz.getName());
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Used to inject an object. This is a helper/common function.
     *
     * @param o the object to inject
     * @param clazz the class of the object
     */
    private void injectObject(Object o, Class<?> clazz) {
        for (Field declaredField : clazz.getDeclaredFields()) {
            Optional<Annotation> annotationOptional = Arrays.stream(declaredField.getAnnotations())
                    .filter(annotation -> injectionHandlers.containsKey(annotation.annotationType()))
                    .findFirst();

            try {
                annotationOptional.ifPresent(annotation -> injectionHandlers.get(annotation.annotationType()).inject(o));
            } catch (Exception e) {
                logger.severe("Could not inject field " + declaredField.getName() + " in " + clazz.getName());
                e.printStackTrace();
            }

        }
    }

}
