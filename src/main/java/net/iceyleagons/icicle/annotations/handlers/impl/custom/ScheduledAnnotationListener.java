package net.iceyleagons.icicle.annotations.handlers.impl.custom;

import net.iceyleagons.icicle.RegisteredIciclePlugin;
import net.iceyleagons.icicle.annotations.Scheduled;
import net.iceyleagons.icicle.annotations.handlers.CustomAnnotationHandler;
import net.iceyleagons.icicle.annotations.handlers.CustomAnnotationHandlerListener;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@CustomAnnotationHandler(Scheduled.class)
public class ScheduledAnnotationListener implements CustomAnnotationHandlerListener {

    @Override
    public void postRegistered(Object object, RegisteredIciclePlugin registeredIciclePlugin) {
        registeredIciclePlugin.getClassScanner().getMethodsAnnotatedWithInsideClazz(object.getClass(), Scheduled.class).forEach(method -> {
            Scheduled annotation = method.getAnnotation(Scheduled.class);

            Bukkit.getScheduler().runTaskTimer(registeredIciclePlugin.getJavaPlugin(), task -> handleMethodInvoking(method, object, task), annotation.delay(), annotation.interval());
        });
    }

    private void handleMethodInvoking(Method method, Object o, BukkitTask bukkitTask) {
        method.setAccessible(true);
        if (method.getParameterTypes().length == 1 && method.getParameterTypes()[0] == BukkitTask.class) {
            try {
                method.invoke(o, bukkitTask);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IllegalStateException("Could not invoke method.", e);
            }
        } else {
            try {
                method.invoke(o);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IllegalStateException("Could not invoke method.", e);
            }
        }
    }
}
