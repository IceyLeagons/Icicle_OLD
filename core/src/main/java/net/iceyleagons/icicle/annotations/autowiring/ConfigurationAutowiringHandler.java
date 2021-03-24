package net.iceyleagons.icicle.annotations.autowiring;

import net.iceyleagons.icicle.api.annotations.Autowired;
import net.iceyleagons.icicle.api.annotations.handlers.AbstractAutowiringHandler;
import net.iceyleagons.icicle.api.annotations.handlers.AutowiringHandler;
import net.iceyleagons.icicle.api.annotations.config.Configuration;
import net.iceyleagons.icicle.annotations.handlers.ConfigurationAnnotationHandler;
import net.iceyleagons.icicle.config.AbstractConfiguration;
import net.iceyleagons.icicle.reflect.Reflections;

import java.lang.reflect.Field;

@AutowiringHandler(Configuration.class)
public class ConfigurationAutowiringHandler extends AbstractAutowiringHandler {

    @Autowired
    public ConfigurationAnnotationHandler configurationAnnotationHandler;

    @Override
    public boolean isSupported(Field field) {
        return field.getType().isAnnotationPresent(Configuration.class);
    }

    @Override
    public void inject(Field field, Object object) {
        Class<?> type = field.getType();

        if (configurationAnnotationHandler.getConfigs().containsKey(type)) {
            AbstractConfiguration service = configurationAnnotationHandler.getConfigs().get(type);
            Reflections.set(field, object, service);
        }
    }
}