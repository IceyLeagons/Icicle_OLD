package net.iceyleagons.icicle.reflect.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.iceyleagons.icicle.api.annotations.handlers.AbstractAutowiringHandler;
import net.iceyleagons.icicle.api.annotations.handlers.AbstractAnnotationHandler;
import net.iceyleagons.icicle.api.reflect.AutowiringHandler;
import net.iceyleagons.icicle.reflect.Reflections;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class AutowiringHandlerImpl implements AutowiringHandler {

    private final ClassScanningHandlerImpl classScanningHandler;
    @Getter
    private final Map<Class<?>, Object> beans = new HashMap<>();

    public void autowireObjects(Object... objects) {
        Arrays.stream(objects).forEach(this::autowireObject);
    }

    /**
     * A special autowiring method just for AutowiringHandlers, so they can access AnnotationHandlers.
     *
     * @param autowiringHandler the {@link AbstractAutowiringHandler} to autowire
     */
    @Override
    public void autowireAutowiringHandler(AbstractAutowiringHandler autowiringHandler) {
        Arrays.stream(autowiringHandler.getClass().getDeclaredFields())
                .forEach(field -> {
                    injectSpecialCases(autowiringHandler, field);
                    if (classScanningHandler.getAnnotationHandlers().containsKey(field.getType())) {
                        AbstractAnnotationHandler abstractAnnotationHandler = classScanningHandler.getAnnotationHandlers().get(field.getType());
                        if (field.getType().isInstance(abstractAnnotationHandler)) {
                            Reflections.set(field, autowiringHandler, abstractAnnotationHandler);
                        }
                    } //TODO warning
                });
    }

    /**
     * Used for autowiring special objects like: Main class, Beans
     *
     * @param object the object to inject to
     * @param field  the field to inject to
     */
    private void injectSpecialCases(Object object, Field field) {
        if (field.getType().equals(classScanningHandler.getRegisteredPlugin().getMainClass())) {
            Reflections.set(field, object, classScanningHandler.getRegisteredPlugin().getJavaPlugin());
        } else if (beans.containsKey(field.getType())) {
            Reflections.set(field, object, beans.get(field.getType()));
        }
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
                    injectSpecialCases(object, field);

                    classScanningHandler.getAutowiringHandlers().values().forEach(autowiringHandler -> {
                        if (autowiringHandler.isSupported(field)) {
                            autowiringHandler.inject(field, object);
                        }
                    });
                });
    }

}