package net.iceyleagons.icicle.common.annotations.autowiring.impl;

import net.iceyleagons.icicle.common.annotations.Autowired;
import net.iceyleagons.icicle.common.annotations.EventListener;
import net.iceyleagons.icicle.common.annotations.autowiring.AbstractAutowiringHandler;
import net.iceyleagons.icicle.common.annotations.autowiring.AutowiringHandler;
import net.iceyleagons.icicle.common.annotations.handlers.impl.EventListenerAnnotationHandler;
import net.iceyleagons.icicle.common.reflect.Reflections;

import java.lang.reflect.Field;

@AutowiringHandler(EventListener.class)
public class EventListenerAutowiringHandler extends AbstractAutowiringHandler {

    @Autowired
    public EventListenerAnnotationHandler annotationHandler;

    @Override
    public boolean isSupported(Class<?> clazz) {
        return clazz.isAnnotationPresent(EventListener.class);
    }

    @Override
    public void inject(Field field, Object object) {
        Class<?> type = field.getType();
        if (annotationHandler.getEventListeners().containsKey(type)) {
            Object service = annotationHandler.getEventListeners().get(type);
            Reflections.set(field, object, service);
        }
    }

    @Override
    public Object get(Class<?> type) {
        if (annotationHandler.getEventListeners().containsKey(type)) {
            return annotationHandler.getEventListeners().get(type);
        }
        return null;
    }
}
