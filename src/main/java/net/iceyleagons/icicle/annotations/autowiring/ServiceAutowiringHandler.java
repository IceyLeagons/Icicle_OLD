package net.iceyleagons.icicle.annotations.autowiring;

import net.iceyleagons.icicle.annotations.Autowired;
import net.iceyleagons.icicle.annotations.Service;
import net.iceyleagons.icicle.annotations.handlers.ServiceAnnotationHandler;
import net.iceyleagons.icicle.reflect.Reflections;

import java.lang.reflect.Field;

@AutowiringHandler(Service.class)
public class ServiceAutowiringHandler extends AbstractAutowiringHandler {

    @Autowired
    public ServiceAnnotationHandler serviceAnnotationHandler;

    @Override
    public boolean isSupported(Field field) {
        System.out.println("Yes");
        return field.getType().isAnnotationPresent(Service.class);
    }

    @Override
    public void inject(Field field, Object object) {
        System.out.println("Yes2");
        Class<?> type = field.getType();
        if (serviceAnnotationHandler.getServices().containsKey(type)) {
            System.out.println("Yes3");
            Object service = serviceAnnotationHandler.getServices().get(type);
            Reflections.set(field, object, service);
        }
    }
}
