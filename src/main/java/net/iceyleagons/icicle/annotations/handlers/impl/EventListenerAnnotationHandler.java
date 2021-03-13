package net.iceyleagons.icicle.annotations.handlers.impl;

import lombok.Getter;
import net.iceyleagons.icicle.annotations.EventListener;
import net.iceyleagons.icicle.annotations.handlers.AbstractAnnotationHandler;
import net.iceyleagons.icicle.annotations.handlers.AnnotationHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.*;

@Getter
@AnnotationHandler(EventListener.class)
public class EventListenerAnnotationHandler extends AbstractAnnotationHandler {

    private final Map<Class<?>, Object> eventListeners = new HashMap<>();

    @Override
    public List<Object> getObjects() {
        return new ArrayList<>(eventListeners.values());
    }

    @Override
    public void scanAndHandleClasses(Reflections reflections) {
        Set<Class<?>> listeners = reflections.getTypesAnnotatedWith(EventListener.class);
        listeners.forEach(listener -> {
            try {
                if (!listener.isAnnotation() && !listener.isInterface()) {
                    Constructor<?> constructor = net.iceyleagons.icicle.reflect.Reflections.getConstructor(listener, true);
                    if (constructor != null) {
                        Object serviceObject = constructor.newInstance();
                        if (serviceObject instanceof Listener) {
                            this.eventListeners.put(listener, serviceObject);
                            Bukkit.getPluginManager().registerEvents((Listener) serviceObject, super.getRegisteredPlugin().getJavaPlugin());

                        } else {
                            getLogger().warning(String.format("Class named %s does not implement Listener while using annotation EventListener!", listener.getName()));
                        }
                    } else {
                        getLogger().warning(String.format("Class named %s does not have an empty constructor!", listener.getName()));
                    }
                } else {
                    getLogger().warning(String.format("Class named %s is not supported for annotation: EventListener!", listener.getName()));
                }
            } catch (Exception e) {
                getLogger().warning(String.format("An exception happened while tried to load in EventListener named %s.", listener.getName()));
                e.printStackTrace();
            }
        });
    }
}
