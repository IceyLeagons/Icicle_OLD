package net.iceyleagons.icicle.annotations.autowiring;

<<<<<<< HEAD:core/src/main/java/net/iceyleagons/icicle/annotations/autowiring/EventListenerAutowiringHandler.java
import net.iceyleagons.icicle.api.annotations.Autowired;
import net.iceyleagons.icicle.api.annotations.EventListener;
import net.iceyleagons.icicle.api.annotations.handlers.AbstractAutowiringHandler;
import net.iceyleagons.icicle.api.annotations.handlers.AutowiringHandler;
import net.iceyleagons.icicle.annotations.handlers.EventListenerAnnotationHandler;
=======
import net.iceyleagons.icicle.annotations.Autowired;
import net.iceyleagons.icicle.annotations.EventListener;
import net.iceyleagons.icicle.annotations.autowiring.AbstractAutowiringHandler;
import net.iceyleagons.icicle.annotations.autowiring.AutowiringHandler;
import net.iceyleagons.icicle.annotations.handlers.impl.EventListenerAnnotationHandler;
>>>>>>> parent of e77d82b (:package: multi module):src/main/java/net/iceyleagons/icicle/annotations/autowiring/impl/EventListenerAutowiringHandler.java
import net.iceyleagons.icicle.reflect.Reflections;

import java.lang.reflect.Field;

@AutowiringHandler(EventListener.class)
public class EventListenerAutowiringHandler extends AbstractAutowiringHandler {

    @Autowired
    public EventListenerAnnotationHandler annotationHandler;

    @Override
    public boolean isSupported(Field field) {
        return field.getType().isAnnotationPresent(EventListener.class);
    }

    @Override
    public void inject(Field field, Object object) {
        Class<?> type = field.getType();
        if (annotationHandler.getEventListeners().containsKey(type)) {
            Object service = annotationHandler.getEventListeners().get(type);
            Reflections.set(field, object, service);
        }
    }
}
