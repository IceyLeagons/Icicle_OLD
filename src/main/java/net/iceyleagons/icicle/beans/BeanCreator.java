package net.iceyleagons.icicle.beans;

import lombok.AllArgsConstructor;
import net.iceyleagons.icicle.RegisteredIciclePlugin;
import net.iceyleagons.icicle.annotations.Autowired;
import net.iceyleagons.icicle.annotations.Bean;
import net.iceyleagons.icicle.annotations.handlers.AutoCreationHandlerListener;
import net.iceyleagons.icicle.annotations.handlers.CustomAnnotationHandlerListener;
import net.iceyleagons.icicle.utils.Asserts;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@AllArgsConstructor
public class BeanCreator {

    private final RegisteredIciclePlugin registeredIciclePlugin;

    //Constructor.getDeclaringClass() for Class<?> type parameters are used because CGLib object.getClass() is not equals to the original class

    public void scanAndCreateAutoCreationClasses() {
        final ClassScanner classScanner = registeredIciclePlugin.getClassScanner();
        final RegisteredBeanDictionary registeredBeanDictionary = registeredIciclePlugin.getRegisteredBeanDictionary();

        final Map<Class<?>, AutoCreationHandlerListener> handlers = classScanner.getAutoCreationHandlers();
        //System.out.println(handlers.size());
        //handlers.keySet().forEach(System.out::println);
        final Map<Class<?>, Constructor<?>> constructors = new HashMap<>(); //with this we're first creating the autocreation classes with the least parameter types, so less chance of requiring something that has not yet been registered

        classScanner.getAutoCreationClasses().forEach(c -> {
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
                    //System.out.println("Creating " + parameterType.getName() + " because " + constructor.getDeclaringClass().getName() + " required it.");
                    createFromConstructor(constructorMap.get(parameterType), registeredBeanDictionary, handlers);
                }
            }

            //System.out.println("Creating: " + constructor.getDeclaringClass().getName());
            createFromConstructor(constructor, registeredBeanDictionary, handlers);
        }
    }

    private void createFromConstructor(Constructor<?> constructor, RegisteredBeanDictionary registeredBeanDictionary, final Map<Class<?>, AutoCreationHandlerListener> handlers) {
        final Object object = scanAndCreateBeans(createObject(constructor, registeredBeanDictionary), constructor.getDeclaringClass(), registeredBeanDictionary);

        //System.out.println("Creating from constructor returned: " + object );
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
            return AutowiringUtils.autowireAndCreateInstance(constructor, registeredBeanDictionary);
        } else {
            Asserts.isTrue(constructor.getParameterTypes().length == 0, "Constructor is not annotated with @Autowired, so should not contain parameters!");
            try {
                final Object object = constructor.newInstance();
                AutowiringUtils.autowireObject(object, registeredBeanDictionary);
                callCustomAnnotationHandlers(object, constructor.getDeclaringClass());

                return object;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new IllegalStateException("Could not create and autowire object from constructor.", e);
            }
        }
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

    private Object scanAndCreateBeans(@Nullable Object object, Class<?> type, RegisteredBeanDictionary registeredBeanDictionary) {
        if (object == null) return null;
        Asserts.notNull(registeredBeanDictionary, "RegisteredBeanDictionary must not be null!");

        final List<Method> methods = registeredIciclePlugin.getClassScanner().getMethodsAnnotatedWithInsideClazz(type, Bean.class);
        methods.forEach(method -> {
            //if (method.getParameterTypes().length != 0) {
                //TODO warning with logger
            //}
            method.setAccessible(true);
            try {
                final Object obj = method.invoke(object);

                registeredBeanDictionary.registerBean(obj);
                callCustomAnnotationHandlers(object, type);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IllegalStateException("Could not access or invoke method marked with @Bean", e);
            }
        });

        return createMethodOverrides(object, registeredBeanDictionary);
    }

    private Object createMethodOverrides(Object object, RegisteredBeanDictionary registeredBeanDictionary) {
        //TODO figure out a fix for problem: Superclass has no null constructors but no arguments were given
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(object.getClass());
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            if (method.isAnnotationPresent(Bean.class)) {
                return registeredBeanDictionary.get(method.getReturnType()).orElse(null);
            } else {
                return proxy.invokeSuper(obj, args);
            }
        });

        return enhancer.create();
    }
}
