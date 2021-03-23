package net.iceyleagons.icicle.annotations.handlers;

import lombok.Getter;
import net.iceyleagons.icicle.api.annotations.Service;
import net.iceyleagons.icicle.api.annotations.handlers.AbstractAnnotationHandler;
import net.iceyleagons.icicle.api.annotations.handlers.AnnotationHandler;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.*;

@Getter
@AnnotationHandler(Service.class)
public class ServiceAnnotationHandler extends AbstractAnnotationHandler {

    private final Map<Class<?>, Object> services = new HashMap<>();

    @Override
    public List<Object> getObjects() {
        return new ArrayList<>(services.values());
    }

    @Override
    public void scanAndHandleClasses(Reflections reflections) {
        Set<Class<?>> services = reflections.getTypesAnnotatedWith(Service.class);
        services.forEach(service -> {
            try {
                if (!service.isAnnotation() && !service.isInterface()) {
                    Constructor<?> constructor = net.iceyleagons.icicle.reflect.Reflections.getConstructor(service, true);
                    if (constructor != null) {
                        Object serviceObject = constructor.newInstance();
                        this.services.put(service, serviceObject);
                    } else {
                        getLogger().warning(String.format("Class named %s does not have an empty constructor!", service.getName()));
                    }
                } else {
                    getLogger().warning(String.format("Class named %s is not supported for annotation: Service!", service.getName()));
                }
            } catch (Exception e) {
                getLogger().warning(String.format("An exception happened while tried to load in Service named %s.", service.getName()));
                e.printStackTrace();
            }
        });

    }
}
