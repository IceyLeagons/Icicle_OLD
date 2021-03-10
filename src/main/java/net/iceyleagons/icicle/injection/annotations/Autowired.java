package net.iceyleagons.icicle.injection.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * If a class is annotated with this, the handler will automatically generate a new instance of it,
 * and inject the necessary stuff into it. This requires that the class having this annotation should
 * always have an empty constructor, otherwise the handler will throw an exception.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {
}
