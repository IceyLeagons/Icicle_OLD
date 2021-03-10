package net.iceyleagons.icicle.config.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigField {
    /**
     * They config name and the key for the value in that config in this specific form:
     * <config-name>$<key-for-value>
     *
     * @return the path for the config value in the described form
     */
    String value();
}
