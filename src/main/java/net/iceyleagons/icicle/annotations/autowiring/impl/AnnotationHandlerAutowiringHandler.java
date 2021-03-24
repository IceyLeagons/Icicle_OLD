package net.iceyleagons.icicle.annotations.autowiring;

<<<<<<< HEAD:core/src/main/java/net/iceyleagons/icicle/annotations/autowiring/AnnotationHandlerAutowiringHandler.java
import net.iceyleagons.icicle.api.annotations.handlers.AbstractAutowiringHandler;
import net.iceyleagons.icicle.api.annotations.handlers.AutowiringHandler;
import net.iceyleagons.icicle.api.annotations.handlers.AnnotationHandler;
=======
import net.iceyleagons.icicle.annotations.autowiring.AbstractAutowiringHandler;
import net.iceyleagons.icicle.annotations.autowiring.AutowiringHandler;
import net.iceyleagons.icicle.annotations.handlers.AnnotationHandler;
>>>>>>> parent of e77d82b (:package: multi module):src/main/java/net/iceyleagons/icicle/annotations/autowiring/impl/AnnotationHandlerAutowiringHandler.java
import net.iceyleagons.icicle.reflect.Reflections;

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
