package net.iceyleagons.icicle.common.annotations.autowiring.impl;

import net.iceyleagons.icicle.common.annotations.Autowired;
import net.iceyleagons.icicle.common.annotations.Service;
import net.iceyleagons.icicle.common.annotations.autowiring.AbstractAutowiringHandler;
import net.iceyleagons.icicle.common.annotations.autowiring.AutowiringHandler;
import net.iceyleagons.icicle.common.annotations.handlers.impl.ServiceAnnotationHandler;
import net.iceyleagons.icicle.common.reflect.Reflections;

import java.lang.reflect.Field;

@AutowiringHandler(Service.class)
public class ServiceAutowiringHandler extends AbstractAutowiringHandler {

    @Autowired
    public ServiceAnnotationHandler serviceAnnotationHandler;

    @Override
    public boolean isSupported(Class<?> clazz) {
        return clazz.isAnnotationPresent(Service.class);
    }

    @Override
    public void inject(Field field, Object object) {
        Class<?> type = field.getType();
        if (serviceAnnotationHandler.getServices().containsKey(type)) {
            Object service = serviceAnnotationHandler.getServices().get(type);
            Reflections.set(field, object, service);
        }
    }

    @Override
    public Object get(Class<?> type) {
        if (serviceAnnotationHandler.getServices().containsKey(type)) {
            return serviceAnnotationHandler.getServices().get(type);
        }

        return null;
    }
}
