package net.iceyleagons.icicle.common.annotations.autowiring.impl;

import net.iceyleagons.icicle.common.annotations.Autowired;
import net.iceyleagons.icicle.common.annotations.autowiring.AbstractAutowiringHandler;
import net.iceyleagons.icicle.common.annotations.autowiring.AutowiringHandler;
import net.iceyleagons.icicle.common.commands.annotations.CommandContainer;
import net.iceyleagons.icicle.common.annotations.handlers.impl.commands.CommandContainerAnnotationHandler;
import net.iceyleagons.icicle.common.reflect.Reflections;

import java.lang.reflect.Field;

@AutowiringHandler(CommandContainer.class)
public class CommandContainerAutowiringHandler extends AbstractAutowiringHandler {

    @Autowired
    public CommandContainerAnnotationHandler annotationHandler;

    @Override
    public boolean isSupported(Class<?> clazz) {
        return clazz.isAnnotationPresent(CommandContainer.class);
    }

    @Override
    public void inject(Field field, Object object) {
        Class<?> type = field.getType();
        if (annotationHandler.getCommandContainers().containsKey(type)) {
            Object service = annotationHandler.getCommandContainers().get(type);
            Reflections.set(field, object, service);
        }
    }

    @Override
    public Object get(Class<?> type) {
        if (annotationHandler.getCommandContainers().containsKey(type)) {
            return annotationHandler.getCommandContainers().get(type);
        }

        return null;
    }
}

