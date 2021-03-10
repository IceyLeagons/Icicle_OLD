package net.iceyleagons.icicle.injection.annotations;

import java.lang.annotation.*;

/**
 * Classes marked with this annotation should have an empty constructor, since it will be created by the handler.
 * Classes marked with this annotation also need to extend {@link net.iceyleagons.icicle.injection.AbstractInjectionHandler}.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectionHandler {

    /**
     * @return the Annotation this injection handler supports
     */
    Class<? extends Annotation> value();

}
