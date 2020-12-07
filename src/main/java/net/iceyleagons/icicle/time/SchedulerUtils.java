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

package net.iceyleagons.icicle.time;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.2.0-SNAPSHOT
 */
public class SchedulerUtils {

    /**
     * {@link org.bukkit.scheduler.BukkitScheduler#runTaskTimer(Plugin, Consumer, long, long)}, but with {@link TimeUnit}
     * but only with the interval, delay is always 0
     *
     * @param javaPlugin the plugin
     * @param consumer   the task consumer
     * @param time       the time
     * @param timeUnit   the unit of the time
     */
    public static void runTaskTimer(JavaPlugin javaPlugin, Consumer<BukkitTask> consumer,
                                    long time, TimeUnit timeUnit) {
        javaPlugin.getServer().getScheduler().runTaskTimer(javaPlugin, consumer, 0L, calculateTicks(time, timeUnit));
    }


    /**
     * {@link org.bukkit.scheduler.BukkitScheduler#runTaskTimer(Plugin, Consumer, long, long)}, but with {@link TimeUnit}
     *
     * @param javaPlugin       the plugin
     * @param consumer         the task consumer
     * @param interval         the interval
     * @param intervalTimeUnit unit of the interval
     * @param delay            the delay
     * @param delayTimeUnit    the unit of the delay
     */
    public static void runTaskTimer(JavaPlugin javaPlugin, Consumer<BukkitTask> consumer,
                                    long interval, TimeUnit intervalTimeUnit,
                                    long delay, TimeUnit delayTimeUnit) {
        javaPlugin.getServer().getScheduler().runTaskTimer(javaPlugin, consumer,
                calculateTicks(delay, delayTimeUnit),
                calculateTicks(interval, intervalTimeUnit));
    }

    /**
     * {@link org.bukkit.scheduler.BukkitScheduler#runTaskTimerAsynchronously(Plugin, Consumer, long, long)}, but with {@link TimeUnit}
     * but only with the interval, delay is always 0
     *
     * @param javaPlugin the plugin
     * @param consumer   the task consumer
     * @param time       the time
     * @param timeUnit   the unit of the time
     */
    public static void runTaskTimerAsynchronously(JavaPlugin javaPlugin, Consumer<BukkitTask> consumer,
                                                  long time, TimeUnit timeUnit) {
        javaPlugin.getServer().getScheduler().runTaskTimerAsynchronously(javaPlugin, consumer,
                0L, calculateTicks(time, timeUnit));
    }

    /**
     * {@link org.bukkit.scheduler.BukkitScheduler#runTaskTimerAsynchronously(Plugin, Consumer, long, long)}, but with {@link TimeUnit}
     *
     * @param javaPlugin       the plugin
     * @param consumer         the task consumer
     * @param interval         the interval
     * @param intervalTimeUnit unit of the interval
     * @param delay            the delay
     * @param delayTimeUnit    the unit of the delay
     */
    public static void runTaskTimerAsynchronously(JavaPlugin javaPlugin, Consumer<BukkitTask> consumer,
                                                  long interval, TimeUnit intervalTimeUnit,
                                                  long delay, TimeUnit delayTimeUnit) {
        javaPlugin.getServer().getScheduler().runTaskTimerAsynchronously(javaPlugin, consumer,
                calculateTicks(delay, delayTimeUnit),
                calculateTicks(interval, intervalTimeUnit));

    }

    /**
     * {@link org.bukkit.scheduler.BukkitScheduler#runTaskLater(Plugin, Consumer, long)} (Plugin, Consumer, long, long)}, but with {@link TimeUnit}
     *
     * @param javaPlugin the plugin
     * @param consumer   the task consumer
     * @param time       the amount of delay
     * @param timeUnit   unit of the delay
     */
    public static void runTaskLater(JavaPlugin javaPlugin, Consumer<BukkitTask> consumer,
                                    long time, TimeUnit timeUnit) {
        javaPlugin.getServer().getScheduler().runTaskLater(javaPlugin, consumer, calculateTicks(time, timeUnit));
    }

    /**
     * {@link org.bukkit.scheduler.BukkitScheduler#runTaskLaterAsynchronously(Plugin, Consumer, long)} (Plugin, Consumer, long, long)}, but with {@link TimeUnit}
     *
     * @param javaPlugin the plugin
     * @param consumer   the task consumer
     * @param time       the amount of delay
     * @param timeUnit   unit of the delay
     */
    public static void runTaskLaterAsynchronously(JavaPlugin javaPlugin, Consumer<BukkitTask> consumer,
                                                  long time, TimeUnit timeUnit) {
        javaPlugin.getServer().getScheduler().runTaskLaterAsynchronously(javaPlugin, consumer,
                calculateTicks(time, timeUnit));
    }

    /**
     * Calculates the ticks from the given time and {@link TimeUnit}
     * <p>Mainly ment for things above 1 second,
     * but you can provide less then one second, but it may produce a tick of 0</p>
     *
     * @param time     the time
     * @param timeUnit the unit of the time
     * @return the calculated ticks
     */
    private static strictfp Long calculateTicks(final long time, final TimeUnit timeUnit) {
        long seconds = timeUnit.toMillis(time); //Using this because of issues with toSeconds when entering milliseconds
        return Math.round((double) seconds / 1000 * 20);
    }

}
