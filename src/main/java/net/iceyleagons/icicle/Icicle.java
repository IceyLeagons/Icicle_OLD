/*
 * MIT License
 *
 * Copyright (c) 2020 IceyLeagons (Tamás Tóth and Márton Kissik) and Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.iceyleagons.icicle;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * This class doesn't really have functions, only update checkers.
 * Only purpose is version checking, with all sorts of metadata information.
 *
 * @author TOTHTOMI
 * @version 1.2.0
 * @since 1.0.0-SNAPSHOT
 */
public class Icicle {

    //TODO Smart schedulers with TimeUnits

    /**
     * Whether or not the module is initialized
     */
    private static boolean initialized = false;

    /**
     * This needs to be called before everything else.
     *
     * @param plugin          the registrar plugin
     * @param enabledFeatures the enabled features of the module
     */
    public static void init(JavaPlugin plugin, IcicleFeatures... enabledFeatures) {
        if (!initialized) {
            for (IcicleFeatures enabledFeature : enabledFeatures)
                enabledFeature.getConsumer().accept(plugin);

            initialized = true;
        }
    }

    /**
     * @return the version of the current icicle library. Our versions use the Semantic versioning.
     */
    public static String getVersion() {
        return "1.2.0-SNAPSHOT";
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
