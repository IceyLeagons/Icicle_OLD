package net.iceyleagons.icicle.api.reflect;

import net.iceyleagons.icicle.api.annotations.handlers.AbstractAutowiringHandler;

import java.util.Map;

public interface AutowiringHandler {

    Map<Class<?>, Object> getBeans();

    void autowireObjects(Object... objects);

    /**
     * A special autowiring method just for AutowiringHandlers, so they can access AnnotationHandlers.
     *
     * @param autowiringHandler the {@link AbstractAutowiringHandler} to autowire
     */
    void autowireAutowiringHandler(AbstractAutowiringHandler autowiringHandler);

    /**
     * Manual autowiring.
     *
     * @param object the object to autowire
     */
    void autowireObject(Object object);

}
