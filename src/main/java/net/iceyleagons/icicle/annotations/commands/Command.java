package net.iceyleagons.icicle.annotations.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    /**
     * @return the command name
     */
    String value();

    String[] aliases() default {};

    String permission() default "";

    boolean playerOnly() default false;

}
