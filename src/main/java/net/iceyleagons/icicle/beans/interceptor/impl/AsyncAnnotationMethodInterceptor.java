package net.iceyleagons.icicle.beans.interceptor.impl;

import net.iceyleagons.icicle.RegisteredIciclePlugin;
import net.iceyleagons.icicle.annotations.bukkit.Async;
import net.iceyleagons.icicle.beans.RegisteredBeanDictionary;
import net.iceyleagons.icicle.beans.interceptor.MethodInterceptorAnnotationHandler;
import net.sf.cglib.proxy.MethodProxy;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;

public class AsyncAnnotationMethodInterceptor implements MethodInterceptorAnnotationHandler {


    @Override
    public Object handleAnnotation(Object obj, Method method, Object[] args, MethodProxy proxy, RegisteredBeanDictionary registeredBeanDictionary, RegisteredIciclePlugin registeredIciclePlugin) throws Throwable {
        JavaPlugin javaPlugin = registeredIciclePlugin.getJavaPlugin();

        if (method.getReturnType().equals(Void.TYPE)) {
            javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> {
                try {
                    proxy.invokeSuper(obj, args);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            });

            return Void.TYPE;
        }

        CompletableFuture<Object> objectCompletableFuture = new CompletableFuture<>();
        javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> {
            try {
                objectCompletableFuture.complete(proxy.invokeSuper(obj, args));
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        return objectCompletableFuture.join();
    }

    @Override
    public Class<? extends Annotation> getAnnotation() {
        return Async.class;
    }
}
