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
    public boolean isSupported(Field field) {
        return field.getType().isAnnotationPresent(Service.class);
    }

    @Override
    public void inject(Field field, Object object) {
        Class<?> type = field.getType();
        if (serviceAnnotationHandler.getServices().containsKey(type)) {
            Object service = serviceAnnotationHandler.getServices().get(type);
            System.out.println(service);
            Reflections.set(field, object, service);
        }
    }
}
