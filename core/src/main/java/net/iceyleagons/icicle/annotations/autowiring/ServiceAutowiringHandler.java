package net.iceyleagons.icicle.annotations.autowiring;

import net.iceyleagons.icicle.api.annotations.Autowired;
import net.iceyleagons.icicle.api.annotations.Service;
import net.iceyleagons.icicle.api.annotations.handlers.AbstractAutowiringHandler;
import net.iceyleagons.icicle.api.annotations.handlers.AutowiringHandler;
import net.iceyleagons.icicle.annotations.handlers.ServiceAnnotationHandler;
import net.iceyleagons.icicle.reflect.Reflections;

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
