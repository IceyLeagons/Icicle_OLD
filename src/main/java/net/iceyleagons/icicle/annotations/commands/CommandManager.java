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
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandManager {

    /**
     * @return the main command name
     */
    String value();

    String invalidCommand() default "&cInvalid command!";
    String tooFewArgumentsText() default "&cNot enough arguments, usage: {usage}";
    String tooManyArgumentsText() default "&cTo many arguments, usage: {usage}";

}
