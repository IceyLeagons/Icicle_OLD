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
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class BeanCreator {

    private final RegisteredIciclePlugin registeredIciclePlugin;

    public void scanAndCreateAutoCreationClasses() {
        final ClassScanner classScanner = registeredIciclePlugin.getClassScanner();
        final RegisteredBeanDictionary registeredBeanDictionary = registeredIciclePlugin.getRegisteredBeanDictionary();

        final Map<Class<?>, AutoCreationHandlerListener> handlers = classScanner.getAutoCreationHandlers();

        classScanner.getAutoCreationClasses().forEach(c -> {
            Asserts.isSize(1, c.getDeclaredConstructors(), "Class must have only 1 public constructor!");

            final Constructor<?> constructor = c.getDeclaredConstructors()[0];
            final Object object = scanAndCreateBeans(createObject(constructor, registeredBeanDictionary), registeredBeanDictionary);


            if (object != null) {
                for (Annotation annotation : object.getClass().getAnnotations()) {
                    if (handlers.containsKey(annotation.annotationType())) {
                        handlers.get(object.getClass()).onCreated(annotation.annotationType(), registeredIciclePlugin);
                    }
                }

                callCustomAnnotationHandlers(object);
                registeredBeanDictionary.registerBean(object);
            }
        });
    }

    private Object createObject(Constructor<?> constructor, RegisteredBeanDictionary registeredBeanDictionary) {
        Asserts.notNull(registeredBeanDictionary, "RegisteredBeanDictionary must not be null!");

        if (constructor.isAnnotationPresent(Autowired.class)) {
            return AutowiringUtils.autowireAndCreateInstance(constructor, registeredBeanDictionary); //TODO constructor may want to autowire beans that have not been initialized yet, therefore make an ordering system here
        } else {
            Asserts.isTrue(constructor.getParameterTypes().length == 0, "Constructor is not annotated with @Autowired, so should not contain parameters!");
            try {
                final Object object = constructor.newInstance();
                AutowiringUtils.autowireObject(object, registeredBeanDictionary);
                callCustomAnnotationHandlers(object);

                return object;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new IllegalStateException("Could not create and autowire object from constructor.", e);
            }
        }
    }

    private void callCustomAnnotationHandlers(Object object) {
        Map<Class<?>, CustomAnnotationHandlerListener> handlers = registeredIciclePlugin.getClassScanner().getCustomAnnotationHandlers();
        for (Method declaredMethod : object.getClass().getDeclaredMethods()) {
            for (Annotation annotation : declaredMethod.getAnnotations()) {
                if (handlers.containsKey(annotation.annotationType())) {
                    handlers.get(annotation.annotationType()).postRegistered(object, this.registeredIciclePlugin);
                }
            }
        }
    }

    private Object scanAndCreateBeans(@Nullable Object object, RegisteredBeanDictionary registeredBeanDictionary) {
        if (object == null) return null;
        Asserts.notNull(registeredBeanDictionary, "RegisteredBeanDictionary must not be null!");

        final List<Method> methods = registeredIciclePlugin.getClassScanner().getMethodsAnnotatedWithInsideClazz(object.getClass(), Bean.class);
        methods.forEach(method -> {
            //if (method.getParameterTypes().length != 0) {
                //TODO warning with logger
            //}
            method.setAccessible(true);
            try {
                final Object obj = method.invoke(object);

                registeredBeanDictionary.registerBean(obj);
                callCustomAnnotationHandlers(object);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IllegalStateException("Could not access or invoke method marked with @Bean", e);
            }
        });

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
