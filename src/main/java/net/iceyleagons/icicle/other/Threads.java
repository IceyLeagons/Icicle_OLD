package net.iceyleagons.icicle.other;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Utility class containing useful methods regarding {@link Thread}s
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public class Threads {

    public static final ExecutorService executorService = Executors.newCachedThreadPool();

    public static Thread launchNativeThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();

        return thread;
    }

    public static void launchCachedThread(Runnable runnable) {
        executorService.execute(runnable);
    }
}
