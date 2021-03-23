package net.iceyleagons.icicle.api.reflect;

import net.iceyleagons.icicle.api.annotations.handlers.AbstractAnnotationHandler;
import net.iceyleagons.icicle.api.annotations.handlers.AbstractAutowiringHandler;

import java.util.Map;

public interface ClassScanningHandler {

    void initialize();
    void registerBeansInObject(Object object);
    Map<Class<?>, AbstractAnnotationHandler> getAnnotationHandlers();
    Map<Class<?>, AbstractAutowiringHandler> getAutowiringHandlers();

}
