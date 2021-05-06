package net.iceyleagons.icicle.common.annotations.autowiring.impl;

import net.iceyleagons.icicle.common.annotations.Autowired;
import net.iceyleagons.icicle.common.annotations.autowiring.AbstractAutowiringHandler;
import net.iceyleagons.icicle.common.annotations.autowiring.AutowiringHandler;
import net.iceyleagons.icicle.common.annotations.handlers.impl.ConfigurationAnnotationHandler;
import net.iceyleagons.icicle.common.config.annotations.Configuration;
import net.iceyleagons.icicle.common.config.AbstractConfiguration;
import net.iceyleagons.icicle.common.reflect.Reflections;

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
