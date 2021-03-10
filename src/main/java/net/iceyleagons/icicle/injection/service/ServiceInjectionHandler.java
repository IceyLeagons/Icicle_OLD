package net.iceyleagons.icicle.injection.service;

import net.iceyleagons.icicle.injection.AbstractInjectionHandler;
import net.iceyleagons.icicle.injection.annotations.InjectionHandler;
import net.iceyleagons.icicle.reflect.Reflections;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class handles registering and injection @Service classes.
 */
@InjectionHandler(ServiceField.class)
public class ServiceInjectionHandler extends AbstractInjectionHandler {

    private final Map<Class<?>, Object> services = new HashMap<>();

    @Override
    public void postInitialization() {
        Set<Class<?>> classes = super.getInjectionHandlerRegistry().getRootPackage().getTypesAnnotatedWith(Service.class);
        classes.forEach(clazz -> {
            Constructor<?> constructor = Reflections.getConstructor(clazz, true);
            if (constructor == null) {
                super.getInjectionHandlerRegistry().getLogger().severe("Cannot initialize @Service named " + clazz.getName() + ", since it does not have an empty constructor!");
            } else {
                try {
                    Object instance = constructor.newInstance();
                    services.put(clazz, instance);
                } catch (Exception e) {
                    super.getInjectionHandlerRegistry().getLogger().severe("Cannot initialize @Service named " + clazz.getName() + ", due to an error!");
                    e.printStackTrace();
                }
            }
        });

        //We inject the service later, so we can inject other service into it, this way we're making sure those services have already been registered
        services.values().forEach(service -> super.getInjectionHandlerRegistry().injectObject(service));
    }

    @Override
    public void inject(Object o) {
        Arrays.stream(o.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(ServiceField.class))
                .peek(field -> field.setAccessible(true))
                .forEach(field -> {
                    Class<?> type = field.getType();
                    if (services.containsKey(type)) {
                        Reflections.set(field, o, services.get(type));
                    } else {
                        super.getInjectionHandlerRegistry().getLogger().warning("Cannot inject Service in @ServiceField in class "
                                + o.getClass().getName() + " because there's no @Service with that type.");
                    }
                });
    }
}
