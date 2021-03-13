package net.iceyleagons.icicle.annotations.autowiring;

import lombok.Data;
import net.iceyleagons.icicle.reflect.ClassScanningHandler;
import net.iceyleagons.icicle.registry.RegisteredPlugin;

import java.lang.reflect.Field;
import java.util.logging.Logger;

@Data
public abstract class AbstractAutowiringHandler {

    private final Logger logger = Logger.getLogger(AbstractAutowiringHandler.class.getName());

    private RegisteredPlugin registeredPlugin;
    private ClassScanningHandler classScanningHandler;
    private AutowiringHandler annotation;

    public void postInitialization() {}

    /**
     * Used to check whether this AutowiringHandler supports the field, to avoid unnecessary injection.
     *
     * @param field the {@link Field} to check
     * @return true if supported, false otherwise
     */
    public abstract boolean isSupported(Field field);

    /**
     * This is called after postInitialization, you'll need to write your own scanning logic, and you have to
     * manage those registered objects. We've decided on this structure for better modularity.
     *
     * @param field the field to inject
     * @param object the object to inject into
     */
    public abstract void inject(Field field, Object object);

}