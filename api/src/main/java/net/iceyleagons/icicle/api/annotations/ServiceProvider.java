package net.iceyleagons.icicle.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Autowiring equivalent {@link org.bukkit.plugin.ServicesManager#getRegistration(Class)}
 *
 * @version 1.0.0
 * @since 2.0.0
 * @author TOTHTOMI
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceProvider {

    /**
     * @return the service type, you would write in {@link org.bukkit.plugin.ServicesManager#getRegistration(Class)}
     */
    Class<?> value();
    
}
