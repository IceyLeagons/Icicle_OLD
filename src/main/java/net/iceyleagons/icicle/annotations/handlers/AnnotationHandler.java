package net.iceyleagons.icicle.annotations.handlers;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotationHandler {

    /**
     * @return the annotation this handler is responsible of.
     */
    Class<? extends Annotation> value();

}
