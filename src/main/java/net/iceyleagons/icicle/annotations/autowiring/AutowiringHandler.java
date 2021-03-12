package net.iceyleagons.icicle.annotations.autowiring;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutowiringHandler {

    /**
     * @return the type this handler is responsible of.
     */
    Class<? extends Annotation> value();

}
