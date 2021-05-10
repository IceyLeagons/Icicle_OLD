package net.iceyleagons.icicle.common.annotations.handlers.impl;

import lombok.Getter;
import net.iceyleagons.icicle.common.annotations.Service;
import net.iceyleagons.icicle.common.annotations.handlers.AbstractAnnotationHandler;
import net.iceyleagons.icicle.common.annotations.handlers.AnnotationHandler;
import net.iceyleagons.icicle.common.annotations.handlers.HandlerClass;
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
    public void scanAndHandleClasses(List<HandlerClass> classes) {
        classes.forEach(service -> {
            try {
                if (service.isNormalClass()) {
                    Optional<Constructor<?>> constructor = service.getEmptyConstructor();
                    if (constructor.isPresent()) {
                        Object serviceObject = constructor.get().newInstance();

                        this.services.put(service.getClazz(), serviceObject);
                    } else {
                        Optional<Constructor<?>> autowiredConstructor = service.getAutowiredConstructor();
                        if (autowiredConstructor.isPresent()) {
                            this.services.put(service.getClazz(), createObjectAndAutowireFromConstructor(autowiredConstructor.get()));
                        } else {
                            getLogger().warning(String.format("Class named %s does not have an empty constructor!", service.getName()));
                        }
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
