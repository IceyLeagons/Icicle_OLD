package net.iceyleagons.icicle.other;

/**
 * A simple utility class for benchmarking your code.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Stopwatch {

    /**
     * Measures the elapsed time of the supplied runnable in milliseconds.
     *
     * @param runnable the runnable to measure
     * @return the elapsed time in milliseconds
     */
    public static long measureTimeMillis(Runnable runnable) {
        long start = System.currentTimeMillis();
        runnable.run();
        long end = System.currentTimeMillis();
        return end - start;
    }
}
