package net.iceyleagons.icicle.common.scheduling;

import net.iceyleagons.icicle.common.reflect.AutowiringHandler;
import net.iceyleagons.icicle.common.reflect.Reflections;
import net.iceyleagons.icicle.common.plugin.RegisteredPlugin;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Handles the @{@link Scheduled} annotation.
 *
 * @version 1.0.0
 * @since 2.0.0
 * @author TOTHTOMI
 */
public class SchedulerService {

    private final RegisteredPlugin registeredPlugin;

    public SchedulerService(RegisteredPlugin registeredPlugin) {
        this.registeredPlugin = registeredPlugin;
    }

    /**
     * Registers an object, and scans for any Scheduled annotated methods.
     * Internally called from {@link AutowiringHandler}
     *
     * @param o the object to scan
     */
    public void registerObject(Object o) {
        Arrays.stream(o.getClass().getMethods())
                .filter(m -> m.isAnnotationPresent(Scheduled.class))
                .forEach(method -> {
                    Scheduled scheduled = method.getAnnotation(Scheduled.class);

                    if (scheduled.async()) {
                        Bukkit.getScheduler().runTaskTimerAsynchronously(registeredPlugin.getJavaPlugin(), task -> invokeMethod(o, method, task),
                                scheduled.delay(), scheduled.interval());
                    } else {
                        Bukkit.getScheduler().runTaskTimer(registeredPlugin.getJavaPlugin(), task -> invokeMethod(o, method, task),
                                scheduled.delay(), scheduled.interval());
                    }
                });
    }

    private static void invokeMethod(Object object, Method method, BukkitTask bukkitTask) {
        if (method.getParameterTypes()[0] == BukkitTask.class) {
            Reflections.invoke(method, Void.class, object, bukkitTask);
        } else {
            Reflections.invoke(method, Void.class, object);
        }
    }
}
