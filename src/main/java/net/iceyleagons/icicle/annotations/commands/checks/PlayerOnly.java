package net.iceyleagons.icicle.annotations.commands.checks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Methods annotated with this annotation can only be executed by a player.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PlayerOnly {

    /**
     * Used for returning a raw error message or the localization ID.
     * If localization is used please set {@link PlayerOnly#usingLocalization()} to true.
     *
     * @return the error message or localization ID
     */
    String error() default "&cWe're sorry, but only players can execute this command!";

    /**
     * @return true if {@link PlayerOnly#error()} returns a localization ID, otherwise false.
     */
    boolean usingLocalization() default false;
}
