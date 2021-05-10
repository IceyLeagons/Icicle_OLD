package net.iceyleagons.icicle.common.annotations.handlers.impl;

import lombok.Getter;
import net.iceyleagons.icicle.common.annotations.EventListener;
import net.iceyleagons.icicle.common.annotations.handlers.AbstractAnnotationHandler;
import net.iceyleagons.icicle.common.annotations.handlers.AnnotationHandler;
import net.iceyleagons.icicle.common.annotations.handlers.HandlerClass;
import net.iceyleagons.icicle.common.event.Events;
import net.iceyleagons.icicle.common.event.packets.PacketListener;
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
    public void scanAndHandleClasses(List<HandlerClass> classes) {
        classes.forEach(listener -> {
            try {
                if (listener.isNormalClass()) {
                    Optional<Constructor<?>> constructor = listener.getEmptyConstructor();
                    if (constructor.isPresent()) {
                        Object serviceObject = constructor.get().newInstance();

                        if (serviceObject instanceof Listener) {
                            this.eventListeners.put(listener.getClazz(), serviceObject);
                            Bukkit.getPluginManager().registerEvents((Listener) serviceObject, super.getRegisteredPlugin().getJavaPlugin());
                        } else if (serviceObject instanceof PacketListener)
                            Events.registerPacketListener((PacketListener) serviceObject);
                        else
                            getLogger().warning(String.format("Class named %s does not implement Listener or PacketListener while using the EventListener annotation!", listener.getName()));

                    } else {
                        Optional<Constructor<?>> autowiredConstructor = listener.getAutowiredConstructor();
                        if (autowiredConstructor.isPresent()) {
                            Object serviceObject = super.createObjectAndAutowireFromConstructor(autowiredConstructor.get());

                            if (serviceObject instanceof Listener) {
                                this.eventListeners.put(listener.getClazz(), serviceObject);
                                Bukkit.getPluginManager().registerEvents((Listener) serviceObject, super.getRegisteredPlugin().getJavaPlugin());
                            } else if (serviceObject instanceof PacketListener)
                                Events.registerPacketListener((PacketListener) serviceObject);
                            else
                                getLogger().warning(String.format("Class named %s does not implement Listener or PacketListener while using the EventListener annotation!", listener.getName()));

                        } else {
                            getLogger().warning(String.format("Class named %s does not have an empty constructor!", listener.getName()));
                        }
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
