package net.iceyleagons.icicle.annotations.handlers.impl;

import lombok.Getter;
import net.iceyleagons.icicle.annotations.config.Configuration;
import net.iceyleagons.icicle.annotations.handlers.AbstractAnnotationHandler;
import net.iceyleagons.icicle.annotations.handlers.AnnotationHandler;
import net.iceyleagons.icicle.config.AbstractConfiguration;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.*;

@Getter
@AnnotationHandler(Configuration.class)
public class ConfigurationAnnotationHandler extends AbstractAnnotationHandler {

    private final Map<Class<?>, AbstractConfiguration> configs = new HashMap<>();

    @Override
    public List<Object> getObjects() {
        return new ArrayList<>(configs.values());
    }

    @Override
    public void scanAndHandleClasses(Reflections reflections) {
        Set<Class<?>> configurations = reflections.getTypesAnnotatedWith(Configuration.class);
        configurations.forEach(config -> {
            try {
                if (!config.isAnnotation() && !config.isInterface()) {
                    if (config.getSuperclass().equals(AbstractConfiguration.class)) {
                        Constructor<?> constructor = net.iceyleagons.icicle.reflect.Reflections.getConstructor(config, true);
                        if (constructor != null) {
                            Configuration configuration = config.getAnnotation(Configuration.class);

                            Object object = constructor.newInstance();
                            AbstractConfiguration configObject = (AbstractConfiguration) object;

                            configObject.setOrigin(object);
                            configObject.setPathToFile(configuration.value());
                            configObject.setRegisteredPlugin(super.getRegisteredPlugin());
                            configObject.init();


                            configs.put(config, configObject);
                        } else {
                            getLogger().warning(String.format("Class named %s does not have an empty constructor!", config.getName()));
                        }
                    } else {
                        getLogger().warning(String.format("Class named %s is annotated with @Configuration but it does not extend AbstractConfiguration!", config.getName()));
                    }
                } else {
                    getLogger().warning(String.format("Class named %s is not supported for annotation: Configuration!", config.getName()));
                }
            } catch (Exception e) {
                getLogger().warning(String.format("An exception happened while tried to load in Configuration named %s.", config.getName()));
                e.printStackTrace();
            }
        });

        configs.values().forEach(container ->
                super.getRegisteredPlugin().getPluginCommandManager().registerCommandContainer(container));
    }
}

