package net.iceyleagons.icicle.annotations.commands.checks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Methods annotated with this annotation can only be executed
 * if the command sender has the appropriate permissions.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionCheck {

    /**
     * @return the required permission
     */
    String value();

    /**
     * Used for returning a raw error message or the localization ID.
     * If localization is used please set {@link PlayerOnly#usingLocalization()} to true.
     *
     * @return the error message or localization ID
     */
    String error() default "&cWe're sorry, but you don't have enough permissions to execute this command!";

    /**
     * @return true if {@link PlayerOnly#error()} returns a localization ID, otherwise false.
     */
    boolean usingLocalization() default false;

}
