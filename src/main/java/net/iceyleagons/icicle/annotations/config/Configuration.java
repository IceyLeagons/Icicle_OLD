package net.iceyleagons.icicle.annotations.config;

import net.iceyleagons.icicle.annotations.AutoCreation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@AutoCreation
@Retention(RetentionPolicy.RUNTIME)
public @interface Configuration {

    /**
     * @return the filepath (the filepath is "counted up" from the plugin's data folder, must contain either .yml or .yaml)
     */
    String value();

    /**
     * @return the header, in a format where every element of the array represents a new line.
     */
    String[] headerLines() default {};
}
