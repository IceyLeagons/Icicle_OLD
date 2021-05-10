package net.iceyleagons.icicle.common.annotations.handlers;

import lombok.Data;
import net.iceyleagons.icicle.common.reflect.ClassScanningHandler;
import net.iceyleagons.icicle.common.plugin.RegisteredPlugin;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Responsible for handling (scanning, registering and managing) a specific annotation.
 * Must be used with @{@link AnnotationHandler}
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 2.0.0
 */
@Data
public abstract class AbstractAnnotationHandler {

    private final Logger logger = Logger.getLogger(AbstractAnnotationHandler.class.getName());

    private RegisteredPlugin registeredPlugin;
    private ClassScanningHandler classScanningHandler;
    private AnnotationHandler annotation;

    /**
     * Called right after initialization, but before {@link #scanAndHandleClasses(List)},
     * use this as your constructor.
     */
    public void postInitialization() {
    }

    /**
     * If the annotation handles objects, that are autowirable, you must return them here.
     *
     * @return the {@link List} of registered objects.
     */
    public List<Object> getObjects() {
        return Collections.emptyList();
    }

    /**
     * This is called after postInitialization, you'll need to write your own scanning logic, and you have to
     * manage those registered objects. We've decided on this structure for better modularity.
     *
     * @param classes the classes flagged with the annotation present in {@link AnnotationHandler#value()}
     */
    public abstract void scanAndHandleClasses(List<HandlerClass> classes);

    public void handleClasses(Reflections reflections) {
        scanAndHandleClasses(reflections.getTypesAnnotatedWith(annotation).stream()
                .map(HandlerClass::new).collect(Collectors.toList()));
    }

    protected Object createObjectAndAutowireFromConstructor(Constructor<?> constructor) {
        return classScanningHandler.getAutowiringHandler().createObjectAndAutowireFromConstructor(constructor);
    }
}
