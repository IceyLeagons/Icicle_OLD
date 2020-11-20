package net.iceyleagons.icicle;

/**
 * This class doesn't really have functions, only update checkers.
 * Only purpose is version checking, with all sorts of metadata information.
 *
 * # Installation
 * To install Icicle shade it into your plugin, or just simply download the source code.
 *
 * ## Maven
 * ```xml
 * <repositories>
 *     soon...
 * </repositories>
 *
 * <dependencies>
 *     <dependency>
 *         <groupId>net.iceyleagons</groupId>
 *         <artifactId>icicle</artifactId>
 *         <version>X.X.X</version>
 *     </dependency>
 * </dependencies>
 * ```
 *
 * ## Gradle
 * ```gradle
 * implementation 'net.iceyleagons:icicle:x.x.x'
 * ```
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public class Icicle {

    /**
     * @return the version of the current icicle library. Our versions use the Semantic versioning.
     */
    public static String getVersion() {
        return "1.0.0";
    }

    /**
     * Not yet implemented!
     *
     * @return will always return false atm
     */
    public static boolean checkForUpdates() {
        return false;
    }

    /**
     * This is optional to show us some love, by printing this out.
     * You'd ideally print this out to the console, or to the players.
     *
     * @return our copyright text
     */
    public static String getCopyrightText() {
        return "This project was built upon IceyLeagons' Icicle Library v" + getVersion() +
                " (Licensed under the terms of MIT License)";
    }

}
