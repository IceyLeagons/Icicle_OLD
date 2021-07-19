package net.iceyleagons.icicle.beans.interceptor;

import net.iceyleagons.icicle.RegisteredIciclePlugin;
import net.iceyleagons.icicle.beans.RegisteredBeanDictionary;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public interface MethodInterceptorAnnotationHandler {

    Object handleAnnotation(Object obj, Method method, Object[] args, MethodProxy proxy, RegisteredBeanDictionary registeredBeanDictionary, RegisteredIciclePlugin registeredIciclePlugin) throws Throwable;
    Class<? extends Annotation> getAnnotation();

}
