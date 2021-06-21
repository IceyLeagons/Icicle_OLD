package net.iceyleagons.icicle.annotations.commands;

import net.iceyleagons.icicle.annotations.AutoCreation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
@AutoCreation
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    /**
     * @return the command name
     */
    String value();

}