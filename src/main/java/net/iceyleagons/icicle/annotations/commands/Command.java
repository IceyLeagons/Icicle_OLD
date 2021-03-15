package net.iceyleagons.icicle.annotations.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @version 1.0.0
 * @since 2.0.0
 * @author TOTHTOMI
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    /**
     * @return the command name
     */
    String value();

    /**
     * @return the aliases for the command
     */
    String[] aliases() default {};

    /**
     * @return the description of the command
     */
    String description() default "";

    /**
     * @return the usage of the command
     */
    String usage() default "";

    /**
     * @return the permission required to run this command
     */
    String permission() default "";

    /**
     * @return true if the command should only be accessible for players
     */
    boolean playerOnly() default false;

}
