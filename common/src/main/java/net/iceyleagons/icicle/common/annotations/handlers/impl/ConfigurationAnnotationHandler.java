package net.iceyleagons.icicle.common.annotations.handlers.impl;

import lombok.Getter;
import net.iceyleagons.icicle.common.annotations.handlers.HandlerClass;
import net.iceyleagons.icicle.common.config.annotations.ConfigHeader;
import net.iceyleagons.icicle.common.config.annotations.Configuration;
import net.iceyleagons.icicle.common.annotations.handlers.AbstractAnnotationHandler;
import net.iceyleagons.icicle.common.annotations.handlers.AnnotationHandler;
import net.iceyleagons.icicle.common.config.AbstractConfiguration;
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
    public void scanAndHandleClasses(List<HandlerClass> classes) {
        classes.forEach(handlerClass -> {
            Class<?> config = handlerClass.getClazz();
            try {
                if (handlerClass.isNormalClass()) {
                    if (handlerClass.isSuperClassAssignable(AbstractConfiguration.class)) {
                        Optional<Constructor<?>> constructorOpt = handlerClass.getEmptyConstructor();
                        if (constructorOpt.isPresent()) {

                            Configuration configuration = config.getAnnotation(Configuration.class);
                            Object object = constructorOpt.get().newInstance();
                            handleConfig(config, configuration, object);
                        } else {
                            Optional<Constructor<?>> autowiredConstructor = handlerClass.getAutowiredConstructor();
                            if (autowiredConstructor.isPresent()) {
                                Configuration configuration = config.getAnnotation(Configuration.class);
                                Object object = super.createObjectAndAutowireFromConstructor(autowiredConstructor.get());
                                handleConfig(config, configuration, object);
                            } else {
                                getLogger().warning(String.format("Class named %s does not have an empty constructor!", config.getName()));
                            }
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

    private void handleConfig(Class<?> config, Configuration configuration, Object object) {
        AbstractConfiguration configObject = (AbstractConfiguration) object;

        if (config.isAnnotationPresent(ConfigHeader.class)) {
            configObject.setHeader(config.getAnnotation(ConfigHeader.class).value());
        }

        configObject.setOrigin(object);
        configObject.setPathToFile(configuration.value());
        configObject.setRegisteredPlugin(super.getRegisteredPlugin());
        configObject.init();


        configs.put(config, configObject);
    }
}

