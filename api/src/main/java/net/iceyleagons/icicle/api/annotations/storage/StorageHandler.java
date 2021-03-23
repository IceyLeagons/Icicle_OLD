package net.iceyleagons.icicle.api.annotations.storage;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface StorageHandler {

    /**
     * This is used to identify the storage handler.
     * Note, that this will be used to search for it aka. used to choose it in config!
     *
     * @return the name of the database type ex. mysql.
     */
    String value();

}
