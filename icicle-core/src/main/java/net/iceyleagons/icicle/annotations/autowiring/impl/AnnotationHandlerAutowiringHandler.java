package net.iceyleagons.icicle.annotations.autowiring.impl;

import net.iceyleagons.icicle.annotations.autowiring.AbstractAutowiringHandler;
import net.iceyleagons.icicle.annotations.autowiring.AutowiringHandler;
import net.iceyleagons.icicle.annotations.handlers.AnnotationHandler;
import net.iceyleagons.icicle.utils.Reflections;

import java.lang.reflect.Field;

@AutowiringHandler(AnnotationHandler.class)
public class AnnotationHandlerAutowiringHandler extends AbstractAutowiringHandler {


    @Override
    public boolean isSupported(Field field) {
        return field.getType().isAnnotationPresent(AnnotationHandler.class);
    }

    @Override
    public void inject(Field field, Object object) {
        if (super.getClassScanningHandler().getAnnotationHandlers().containsKey(field.getType())) {
            Reflections.set(field, object, super.getClassScanningHandler().getAnnotationHandlers().get(field.getType()));
        }
    }
}
