package net.iceyleagons.icicle.beans.interceptor;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public interface MethodAnnotationHandler {

    Object handleAnnotation(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable;
    Class<? extends Annotation> getAnnotation();

}
