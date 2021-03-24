package net.iceyleagons.icicle.annotations.autowiring;

<<<<<<< HEAD:core/src/main/java/net/iceyleagons/icicle/annotations/autowiring/CommandContainerAutowiringHandler.java
import net.iceyleagons.icicle.api.annotations.Autowired;
import net.iceyleagons.icicle.api.annotations.handlers.AbstractAutowiringHandler;
import net.iceyleagons.icicle.api.annotations.handlers.AutowiringHandler;
import net.iceyleagons.icicle.api.annotations.commands.CommandContainer;
import net.iceyleagons.icicle.annotations.handlers.commands.CommandContainerAnnotationHandler;
=======
import net.iceyleagons.icicle.annotations.Autowired;
import net.iceyleagons.icicle.annotations.autowiring.AbstractAutowiringHandler;
import net.iceyleagons.icicle.annotations.autowiring.AutowiringHandler;
import net.iceyleagons.icicle.annotations.commands.CommandContainer;
import net.iceyleagons.icicle.annotations.handlers.impl.commands.CommandContainerAnnotationHandler;
>>>>>>> parent of e77d82b (:package: multi module):src/main/java/net/iceyleagons/icicle/annotations/autowiring/impl/CommandContainerAutowiringHandler.java
import net.iceyleagons.icicle.reflect.Reflections;

import java.lang.reflect.Field;

@AutowiringHandler(CommandContainer.class)
public class CommandContainerAutowiringHandler extends AbstractAutowiringHandler {

    @Autowired
    public CommandContainerAnnotationHandler annotationHandler;

    @Override
    public boolean isSupported(Field field) {
        return field.getType().isAnnotationPresent(CommandContainer.class);
    }

    @Override
    public void inject(Field field, Object object) {
        Class<?> type = field.getType();
        if (annotationHandler.getCommandContainers().containsKey(type)) {
            Object service = annotationHandler.getCommandContainers().get(type);
            Reflections.set(field, object, service);
        }
    }
}

