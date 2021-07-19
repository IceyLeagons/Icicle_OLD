package net.iceyleagons.icicle.beans;

import lombok.AllArgsConstructor;
import net.iceyleagons.icicle.Icicle;
import net.iceyleagons.icicle.RegisteredIciclePlugin;
import net.iceyleagons.icicle.annotations.Autowired;
import net.iceyleagons.icicle.annotations.handlers.AutoCreationHandlerListener;
import net.iceyleagons.icicle.annotations.handlers.CustomAnnotationHandlerListener;
import net.iceyleagons.icicle.beans.interceptor.IcicleMethodInterceptor;
import net.iceyleagons.icicle.utils.Asserts;
import net.sf.cglib.proxy.Enhancer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;

@AllArgsConstructor
public class BeanCreator {

    private final RegisteredIciclePlugin registeredIciclePlugin;

    public void scanAndCreateAutoCreationClasses() {
        final ClassScanner classScanner = registeredIciclePlugin.getClassScanner();
        final RegisteredBeanDictionary registeredBeanDictionary = registeredIciclePlugin.getRegisteredBeanDictionary();

        final Map<Class<?>, AutoCreationHandlerListener> handlers = classScanner.getAutoCreationHandlers();
        final Map<Class<?>, Constructor<?>> constructors = new HashMap<>(); //with this we're first creating the autocreation classes with the least parameter types, so less chance of requiring something that has not yet been registered

        classScanner.getAutoCreationClasses().forEach(c -> {
            Icicle.debug("Auto creation class detected: " + c.getName());
            Asserts.isSize(1, c.getDeclaredConstructors(), "Class must have only 1 public constructor!");

            final Constructor<?> constructor = c.getDeclaredConstructors()[0];
            constructors.put(constructor.getDeclaringClass(), constructor);
        });

        handleAutoCreationConstructors(constructors, registeredBeanDictionary, handlers);
    }

    private void handleAutoCreationConstructors(final Map<Class<?>, Constructor<?>> constructorMap, RegisteredBeanDictionary registeredBeanDictionary, final Map<Class<?>, AutoCreationHandlerListener> handlers) {
        for (Constructor<?> constructor : constructorMap.values()) {
            if (registeredBeanDictionary.contains(constructor.getDeclaringClass())) continue;

            for (Class<?> parameterType : constructor.getParameterTypes()) {
                if (!registeredBeanDictionary.contains(parameterType)) {
                    Icicle.debug("Creating bean " + parameterType.getName() + " because " + constructor.getDeclaringClass().getName() + " required it.");
                    createFromConstructor(constructorMap.get(parameterType), registeredBeanDictionary, handlers);
                }
            }

            Icicle.debug("Creating bean: " + constructor.getDeclaringClass().getName());
            createFromConstructor(constructor, registeredBeanDictionary, handlers);
        }
    }

    private void createFromConstructor(Constructor<?> constructor, RegisteredBeanDictionary registeredBeanDictionary, final Map<Class<?>, AutoCreationHandlerListener> handlers) {
        final Object object = createObject(constructor, registeredBeanDictionary);

        Icicle.debug("Creating from bean constructor returned: " + object);
        if (object != null) {
            for (Annotation annotation : constructor.getDeclaringClass().getAnnotations()) {
                if (handlers.containsKey(annotation.annotationType())) {
                    handlers.get(annotation.annotationType()).onCreated(object, constructor.getDeclaringClass(), registeredIciclePlugin);
                }
            }

            callCustomAnnotationHandlers(object, constructor.getDeclaringClass());
            registeredBeanDictionary.registerBean(object, constructor.getDeclaringClass());
        }
    }

    private Object createObject(Constructor<?> constructor, RegisteredBeanDictionary registeredBeanDictionary) {
        Asserts.notNull(registeredBeanDictionary, "RegisteredBeanDictionary must not be null!");


        if (constructor.isAnnotationPresent(Autowired.class)) {
            Object[] params = AutowiringUtils.getParameters(constructor.getParameterTypes(), registeredBeanDictionary, constructor.getDeclaringClass());
            return createEnhancedClass(constructor, params, registeredBeanDictionary);
        } else {
            Asserts.isTrue(constructor.getParameterTypes().length == 0, "Constructor is not annotated with @Autowired, so should not contain parameters!");
            Object object = createEnhancedClass(constructor, new Object[0], registeredBeanDictionary);
            AutowiringUtils.autowireObject(object, registeredBeanDictionary);
            return object;
        }
    }

    private Object createEnhancedClass(Constructor<?> constructor, Object[] params, RegisteredBeanDictionary registeredBeanDictionary) {
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(constructor.getDeclaringClass());

        enhancer.setCallback(new IcicleMethodInterceptor(registeredBeanDictionary, registeredIciclePlugin));

        return enhancer.create(constructor.getParameterTypes(), params);
    }

    private void callCustomAnnotationHandlers(Object object, Class<?> type) {
        Map<Class<?>, CustomAnnotationHandlerListener> handlers = registeredIciclePlugin.getClassScanner().getCustomAnnotationHandlers();
        for (Method declaredMethod : type.getDeclaredMethods()) {
            for (Annotation annotation : declaredMethod.getAnnotations()) {
                if (handlers.containsKey(annotation.annotationType())) {
                    handlers.get(annotation.annotationType()).postRegistered(object, type, this.registeredIciclePlugin);
                }
            }
        }
    }
}
