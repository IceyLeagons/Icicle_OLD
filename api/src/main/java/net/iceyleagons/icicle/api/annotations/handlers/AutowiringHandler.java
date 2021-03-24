package net.iceyleagons.icicle.api.annotations.handlers;

import net.iceyleagons.icicle.api.annotations.handlers.AbstractAutowiringHandler;

import java.lang.annotation.*;

/**
 * Used as a flag to register {@link AbstractAutowiringHandler}s.
 * You also specify what annotation this handler is responsible for.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 2.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutowiringHandler {

    /**
     * @return the type this handler is responsible of.
     */
    Class<? extends Annotation> value();

}
