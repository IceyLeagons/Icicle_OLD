package net.iceyleagons.icicle.annotations.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Configuration {

    /**
     * @return the filepath (the filepath is "counted up" from the plugin's data folder, must contain either .yml or .yaml)
     */
    String filePath();

}
