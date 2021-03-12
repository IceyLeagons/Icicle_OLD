package net.iceyleagons.icicle.reflect;

import lombok.RequiredArgsConstructor;
import net.iceyleagons.icicle.annotations.autowiring.AbstractAutowiringHandler;
import net.iceyleagons.icicle.annotations.handlers.AbstractAnnotationHandler;
import net.iceyleagons.icicle.registry.RegisteredPlugin;

import java.lang.reflect.Field;
import java.util.Arrays;

@RequiredArgsConstructor
public class AutowiringHandler {

    private final ClassScanningHandler classScanningHandler;

    public void autowireObjects(Object... objects) {
        Arrays.stream(objects).forEach(this::autowireObject);
    }

    /**
     * A special autowiring method just for AutowiringHandlers, so they can access AnnotationHandlers.
     *
     * @param autowiringHandler the {@link AbstractAutowiringHandler} to autowire
     */
    public void autowireAutowiringHandler(AbstractAutowiringHandler autowiringHandler) {
        Arrays.stream(autowiringHandler.getClass().getDeclaredFields())
                .forEach(field -> {
                    if (classScanningHandler.getAnnotationHandlers().containsKey(field.getType())) {
                        AbstractAnnotationHandler abstractAnnotationHandler = classScanningHandler.getAnnotationHandlers().get(field.getType());
                        if (field.getType().isInstance(abstractAnnotationHandler)) {
                            Reflections.set(field, autowiringHandler, abstractAnnotationHandler);
                        }
                    } //TODO warning
                });
    }


    /**
     * Manual autowiring.
     *
     * @param object the object to autowire
     */
    public void autowireObject(Object object) {
        Arrays.stream(object.getClass().getDeclaredFields())
                .peek(field -> field.setAccessible(true))
                .forEach(field -> {
                    classScanningHandler.getAutowiringHandlers().values().forEach(autowiringHandler -> {
                        if (autowiringHandler.isSupported(field)) {
                            autowiringHandler.inject(field, object);
                        }
                    });
                });
    }

}
