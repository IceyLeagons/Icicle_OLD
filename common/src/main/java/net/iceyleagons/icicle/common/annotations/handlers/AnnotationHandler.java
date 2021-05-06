package net.iceyleagons.icicle.common.annotations.handlers;

import java.lang.annotation.*;

/**
 * Used as a flag to register {@link AbstractAnnotationHandler}s.
 * You also specify what annotation this handler is responsible for.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 2.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotationHandler {

    /**
     * @return the annotation this handler is responsible of.
     */
    Class<? extends Annotation> value();

}
