package net.iceyleagons.icicle.annotations.handlers;

import lombok.Data;
import net.iceyleagons.icicle.reflect.ClassScanningHandler;
import net.iceyleagons.icicle.registry.RegisteredPlugin;
import org.reflections.Reflections;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

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
     * Called right after initialization, but before {@link #scanAndHandleClasses(Reflections)},
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
     * @param reflections the reflections you can use to scan for the classes
     */
    public abstract void scanAndHandleClasses(Reflections reflections);

}
