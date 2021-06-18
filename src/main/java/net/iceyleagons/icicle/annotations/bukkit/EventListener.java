package net.iceyleagons.icicle.annotations.bukkit;

import net.iceyleagons.icicle.annotations.AutoCreation;

import java.lang.annotation.*;

/**
 * This annotation is a auto-creation annotation, which means, that the class marked with this annotation will be automatically created and managed by Icicle.
 *
 * Classes annotated with this annotation will be automatically to the plugin listener. The following rules apply:
 *      - the class must implement {@link org.bukkit.event.Listener}
 *
 *      - the class may be auto-wired, please check the documentation of @{@link net.iceyleagons.icicle.annotations.Autowired}
 *      - if you decide not-to autowire, please have an empty public constructor!
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
@AutoCreation
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventListener {
}
