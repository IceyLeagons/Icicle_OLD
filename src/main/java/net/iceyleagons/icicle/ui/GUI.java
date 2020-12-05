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

package net.iceyleagons.icicle.ui;

import org.bukkit.event.inventory.InventoryType;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * Annotate any GUI class with this annotation and register it with {@link GUIManager#registerGUI(GUITemplate)}
 *
 * @author TOTHTOMI, Gabe
 * @version 1.0.1
 * @since 1.2.0-SNAPSHOT
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface GUI {

    /**
     * MUST BE SET!
     *
     * @return the title of the inventory
     */
    String title();

    /**
     * Default: {@link InventoryType#CHEST}
     *
     * @return the type of the gui inventory
     */
    InventoryType type() default InventoryType.CHEST;

    /**
     * Whether or not to auto update with a scheduler.
     * <p>
     * Default: true
     *
     * @return whether or not to auto update with a scheduler
     */
    boolean autoUpdate() default true;

    /**
     * Only needed if {@link #type()} is set to CHEST.
     * <p>
     * Otherwise, is the height of the chest inventory.
     * Default: 3
     *
     * @return height of the inventory
     */
    int height() default 3;

    /**
     * Whether the GUI is displayed per player.
     * <p>
     * Default: false
     *
     * @return whether or not the GUI is displayed per player
     * @deprecated NOT YET IMPLEMENTED!
     */
    @Deprecated
    boolean perPlayer() default false;

    /**
     * Pretty self-explanatory, huh?
     * <p>
     * Default: {@link TimeUnit#SECONDS}
     *
     * @return the time unit of the update interval.
     */
    TimeUnit updateIntervalUnit() default TimeUnit.SECONDS;

    /**
     * The interval of the updates
     * <p>
     * Default: 1
     *
     * @return update interval
     */
    long updateInterval() default 1L;

}
