package net.iceyleagons.icicle.common.annotations.autowiring.impl;

import net.iceyleagons.icicle.common.annotations.autowiring.AbstractAutowiringHandler;
import net.iceyleagons.icicle.common.annotations.autowiring.AutowiringHandler;
import net.iceyleagons.icicle.common.annotations.handlers.AnnotationHandler;
import net.iceyleagons.icicle.common.reflect.Reflections;

import java.lang.reflect.Field;

@AutowiringHandler(AnnotationHandler.class)
public class AnnotationHandlerAutowiringHandler extends AbstractAutowiringHandler {

    @Override
    public boolean isSupported(Class<?> clazz) {
        return clazz.isAnnotationPresent(AnnotationHandler.class);
    }

    @Override
    public void inject(Field field, Object object) {
        if (super.getClassScanningHandler().getAnnotationHandlers().containsKey(field.getType())) {
            Reflections.set(field, object, super.getClassScanningHandler().getAnnotationHandlers().get(field.getType()));
        }
    }

    @Override
    public Object get(Class<?> type) {
        if (super.getClassScanningHandler().getAnnotationHandlers().containsKey(type)) {
           return super.getClassScanningHandler().getAnnotationHandlers().get(type);
        }
        return null;
    }
}
