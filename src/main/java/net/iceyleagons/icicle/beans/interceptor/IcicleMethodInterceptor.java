package net.iceyleagons.icicle.beans;

import lombok.RequiredArgsConstructor;
import net.iceyleagons.icicle.annotations.Bean;
import net.iceyleagons.icicle.annotations.bukkit.Async;
import net.iceyleagons.icicle.annotations.bukkit.Sync;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@RequiredArgsConstructor
public class IcicleMethodInterceptor implements MethodInterceptor {

    private final RegisteredBeanDictionary registeredBeanDictionary;

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (method.isAnnotationPresent(Bean.class)) {
            if (!registeredBeanDictionary.contains(method.getReturnType())) {
                Object object = proxy.invokeSuper(obj, args);
                registeredBeanDictionary.registerBean(object);
                return object;
            }

            return registeredBeanDictionary.get(method.getReturnType()).orElse(null);
        } else if (method.isAnnotationPresent(Sync.class)) {
            return handleSyncAnnotation(obj, method, args, proxy);
        } else if (method.isAnnotationPresent(Async.class)) {
            return handleAsyncAnnotation(obj, method, args, proxy);
        } else {
            return proxy.invokeSuper(obj, args);
        }
    }

    private Object handleSyncAnnotation(Object obj, Method method, Object[] args, MethodProxy proxy) {

    }

    private Object handleAsyncAnnotation(Object obj, Method method, Object[] args, MethodProxy proxy) {

    }
}
