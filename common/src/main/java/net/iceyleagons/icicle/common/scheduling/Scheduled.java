package net.iceyleagons.icicle.common.scheduling;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Scheduled {

    /**
     * @return the interval in ticks.
     */
    long interval();

    /**
     * @return the delay in ticks
     */
    long delay() default 0L;

    /**
     * @return true if should be run asynchronously
     */
    boolean async() default false;

}
