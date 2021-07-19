package net.iceyleagons.icicle.beans.interceptor;

import lombok.RequiredArgsConstructor;
import net.iceyleagons.icicle.RegisteredIciclePlugin;
import net.iceyleagons.icicle.annotations.Bean;
import net.iceyleagons.icicle.beans.RegisteredBeanDictionary;
import net.iceyleagons.icicle.beans.interceptor.impl.AsyncAnnotationMethodInterceptor;
import net.iceyleagons.icicle.beans.interceptor.impl.SyncAnnotationMethodInterceptor;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class IcicleMethodInterceptor implements MethodInterceptor {

    private final RegisteredBeanDictionary registeredBeanDictionary;
    private final RegisteredIciclePlugin registeredIciclePlugin;
    private final Map<Class<? extends Annotation>, MethodInterceptorAnnotationHandler> annotationHandlerMap = getHandlers();

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (method.isAnnotationPresent(Bean.class)) {
            if (!registeredBeanDictionary.contains(method.getReturnType())) {
                Object object = proxy.invokeSuper(obj, args);
                registeredBeanDictionary.registerBean(object);
                return object;
            }

            return registeredBeanDictionary.get(method.getReturnType()).orElse(null);
        }

        for (Class<? extends Annotation> aClass : annotationHandlerMap.keySet()) {
            if (!method.isAnnotationPresent(aClass)) continue;

            return annotationHandlerMap.get(aClass).handleAnnotation(obj, method, args, proxy, registeredBeanDictionary, registeredIciclePlugin);
        }

        return proxy.invokeSuper(obj, args);
    }

    public static Map<Class<? extends Annotation>, MethodInterceptorAnnotationHandler> getHandlers() {
        return Stream.of(new SyncAnnotationMethodInterceptor(), new AsyncAnnotationMethodInterceptor())
                .collect(Collectors.toMap(MethodInterceptorAnnotationHandler::getAnnotation, m -> m));
    }
}
