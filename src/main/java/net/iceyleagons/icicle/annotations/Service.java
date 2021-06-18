package net.iceyleagons.icicle.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is an auto-creation annotation, which means, that the class marked with this annotation will be automatically created and managed by Icicle.
 */
@AutoCreation
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {
}
