package net.iceyleagons.icicle.annotations.bukkit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * All methods inside a registered or icicle-managed object annotated with this annotation, will
 * be called when the IciclePlugin starts shutting down.
 *
 * Optionally you can specify an order, in which these methods will be called.
 * Methods with the same order-number will be called in no specific order.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnDisable {

    int value() default 0;

}
