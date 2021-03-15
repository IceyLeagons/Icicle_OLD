package net.iceyleagons.icicle.reflect;

import com.google.common.base.Preconditions;
import lombok.Getter;
import net.iceyleagons.icicle.annotations.Bean;
import net.iceyleagons.icicle.annotations.autowiring.AbstractAutowiringHandler;
import net.iceyleagons.icicle.annotations.handlers.AbstractAnnotationHandler;
import net.iceyleagons.icicle.annotations.handlers.AnnotationHandler;
import net.iceyleagons.icicle.registry.RegisteredPlugin;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.logging.Logger;

@Getter
public class ClassScanningHandler {

    private final RegisteredPlugin registeredPlugin;
    private final AutowiringHandler autowiringHandler;
    private final Reflections rootPackage;
    private final Logger logger = Logger.getLogger(ClassScanningHandler.class.getName());

    private final Map<Class<?>, AbstractAnnotationHandler> annotationHandlers = new HashMap<>();
    private final Map<Class<?>, AbstractAutowiringHandler> autowiringHandlers = new HashMap<>();


    private boolean initialized = false;

    public ClassScanningHandler(RegisteredPlugin registeredPlugin) {
        this.registeredPlugin = registeredPlugin;
        this.registeredPlugin.setClassScanningHandler(this);

        this.autowiringHandler = new AutowiringHandler(this);
        this.registeredPlugin.setAutowiringHandler(this.autowiringHandler);

        this.rootPackage = new Reflections(registeredPlugin.getRootPackageName()).merge(new Reflections("net.iceyleagons.icicle"));
        initialize();
    }


    public void initialize() {
        Preconditions.checkArgument(!initialized, new IllegalStateException("ClassScanningHandler has already been initialized!"));

        initialized = true;
        loadAnnotationHandlers();
        loadAutowiringHandlers();
        autowire();
    }

    private void autowire() {
        //TODO does not work in some cases, needs the ordering system!!!!
        annotationHandlers.values().forEach(annotationHandler -> {
            List<Object> objects = annotationHandler.getObjects();
            objects.forEach(autowiringHandler::autowireObject);

        });
    }

    private void loadAnnotationHandlers() {
        Set<Class<?>> classes = rootPackage.getTypesAnnotatedWith(AnnotationHandler.class);
        classes.forEach(clazz -> {
            try {
                if (clazz.getSuperclass().equals(AbstractAnnotationHandler.class) && !clazz.isAnnotation() && !clazz.isInterface()) {
                    Constructor<?> constructor = net.iceyleagons.icicle.reflect.Reflections.getConstructor(clazz, true);
                    if (constructor != null) {
                        AbstractAnnotationHandler abstractAnnotationHandler = (AbstractAnnotationHandler) constructor.newInstance();

                        abstractAnnotationHandler.setRegisteredPlugin(registeredPlugin);
                        abstractAnnotationHandler.setClassScanningHandler(this);
                        abstractAnnotationHandler.setAnnotation(clazz.getAnnotation(AnnotationHandler.class));
                        annotationHandlers.put(clazz, abstractAnnotationHandler);
                    } else {
                        logger.warning(String.format("Class named %s does not have an empty constructor!", clazz.getName()));
                    }
                } else {
                    logger.warning(String.format("Class named %s is annotated with AnnotationHandler, but does not extend AbstractAnnotationHandler or the type is unsupported!", clazz.getName()));
                }
            } catch (Exception e) {
                logger.warning(String.format("An exception happened while tried to load in AnnotationHandler named %s.", clazz.getName()));
                e.printStackTrace();
            }
        });

        annotationHandlers.values().forEach(annotationHandler -> {
            annotationHandler.postInitialization();
            annotationHandler.scanAndHandleClasses(this.rootPackage);
        });
    }

    private void loadAutowiringHandlers() {
        Set<Class<?>> classes = rootPackage.getTypesAnnotatedWith(net.iceyleagons.icicle.annotations.autowiring.AutowiringHandler.class);
        classes.forEach(clazz -> {
            try {
                if (clazz.getSuperclass().equals(AbstractAutowiringHandler.class) && !clazz.isAnnotation() && !clazz.isInterface()) {
                    Constructor<?> constructor = net.iceyleagons.icicle.reflect.Reflections.getConstructor(clazz, true);
                    if (constructor != null) {
                        AbstractAutowiringHandler abstractAutowiringHandler = (AbstractAutowiringHandler) constructor.newInstance();

                        abstractAutowiringHandler.setRegisteredPlugin(registeredPlugin);
                        abstractAutowiringHandler.setClassScanningHandler(this);
                        abstractAutowiringHandler.setAnnotation(clazz.getAnnotation(net.iceyleagons.icicle.annotations.autowiring.AutowiringHandler.class));

                        this.autowiringHandler.autowireAutowiringHandler(abstractAutowiringHandler);
                        autowiringHandlers.put(clazz, abstractAutowiringHandler);
                    } else {
                        logger.warning(String.format("Class named %s does not have an empty constructor!", clazz.getName()));
                    }
                } else {
                    logger.warning(String.format("Class named %s is annotated with AutowiringHandler, but does not extend AbstractAutowiringHandler or the type is unsupported!", clazz.getName()));
                }
            } catch (Exception e) {
                logger.warning(String.format("An exception happened while tried to load in AutowiringHandler named %s.", clazz.getName()));
                e.printStackTrace();
            }
        });

        autowiringHandlers.values().forEach(AbstractAutowiringHandler::postInitialization);
    }

    public void registerBeansInObject(Object object) {
        Arrays.stream(object.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Bean.class))
                .peek(method -> method.setAccessible(true))
                .forEach(method -> {
                    if (method.getReturnType().equals(Void.class)) {
                        getLogger().warning("Unsupported bean type in class " + object.getClass().getName() + ", named " + method.getName());
                    } else {
                        try {
                            if (method.getParameterTypes().length != 0) {
                                getLogger().warning("Bean must not have parameters in class " + object.getClass().getName() + ", named " + method.getName());
                            } else {
                                Object obj = method.invoke(object);
                                getAutowiringHandler().getBeans().put(method.getReturnType(), obj);
                            }
                        } catch (Exception e) {
                            getLogger().warning("Could not register bean in class " + object.getClass().getName() + ", named " + method.getName());
                            e.printStackTrace();
                        }
                    }
                });

    }


}
