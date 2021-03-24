<<<<<<< HEAD:api/src/main/java/net/iceyleagons/icicle/api/annotations/handlers/AnnotationHandler.java
package net.iceyleagons.icicle.api.annotations.handlers;

import net.iceyleagons.icicle.api.annotations.handlers.AbstractAnnotationHandler;
=======
package net.iceyleagons.icicle.annotations.handlers;
>>>>>>> parent of e77d82b (:package: multi module):src/main/java/net/iceyleagons/icicle/annotations/handlers/AnnotationHandler.java

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
