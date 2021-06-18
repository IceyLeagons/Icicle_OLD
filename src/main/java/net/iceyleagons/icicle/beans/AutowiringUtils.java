package net.iceyleagons.icicle.beans;

import net.iceyleagons.icicle.utils.Asserts;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class AutowiringUtils {

    /**
     * These are just utility methods, since there are way more registering going behind the scenes do not call these methods directly from here, but
     * from {@link net.iceyleagons.icicle.RegisteredIciclePlugin}
     *
     * @param clazz
     * @param registeredBeanDictionary
     * @return
     */
    public static Object autowireAndCreateInstance(Class<?> clazz, RegisteredBeanDictionary registeredBeanDictionary) {

        Asserts.notNull(clazz, "Supplied class must not be null!");
        Asserts.notNull(registeredBeanDictionary, "Supplied RegisteredBeanDictionary must not be null!");

        final Constructor<?>[] constructors = clazz.getConstructors();
        Asserts.isSize(1, constructors, "Autowireable object must only contain 1 constructor!");

        return autowireAndCreateInstance(constructors[0], registeredBeanDictionary);
    }


    /**
     * These are just utility methods, since there are way more registering going behind the scenes do not call these methods directly from here, but
     * from {@link net.iceyleagons.icicle.RegisteredIciclePlugin}
     * @param toAutowire
     * @param registeredBeanDictionary
     */
    public static void autowireObject(Object toAutowire, RegisteredBeanDictionary registeredBeanDictionary) {
        Asserts.notNull(registeredBeanDictionary, "Supplied RegisteredBeanDictionary must not be null!");

        for (final Field declaredField : toAutowire.getClass().getDeclaredFields()) {
            autowireField(declaredField, toAutowire, registeredBeanDictionary);
        }
    }


    /**
     * These are just utility methods, since there are way more registering going behind the scenes do not call these methods directly from here, but
     * from {@link net.iceyleagons.icicle.RegisteredIciclePlugin}
     *
     * @param constructor
     * @param registeredBeanDictionary
     * @return
     */
    public static Object autowireAndCreateInstance(Constructor<?> constructor, RegisteredBeanDictionary registeredBeanDictionary) {
        Asserts.notNull(registeredBeanDictionary, "Supplied RegisteredBeanDictionary must not be null!");
        Asserts.notNull(constructor, "Supplied constructor must not be null!");

        final Class<?>[] paramTypes = constructor.getParameterTypes();
        Object[] params = new Object[paramTypes.length];

        for (int i = 0; i < paramTypes.length; i++) {
            final Class<?> paramType = paramTypes[i];

            final Optional<Object> bean = registeredBeanDictionary.get(paramType);
            if (bean.isEmpty()) throw new IllegalArgumentException("Constructor required a non-registered bean!");

            final Object beanObject = bean.get();
            Asserts.isInstanceOf(paramType, beanObject, "RegisteredBeanDictionary returned an invalid object! (Does not match required type)");

            params[i] = paramType.cast(bean.get());
        }

        try {
            return constructor.newInstance(params);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException("Cannot create autowired constructor!", e);
        }
    }


    /**
     * These are just utility methods, since there are way more registering going behind the scenes do not call these methods directly from here, but
     * from {@link net.iceyleagons.icicle.RegisteredIciclePlugin}
     *
     * @param field
     * @param parent
     * @param registeredBeanDictionary
     */
    public static void autowireField(Field field, Object parent, RegisteredBeanDictionary registeredBeanDictionary) {
        Asserts.notNull(registeredBeanDictionary, "Supplied RegisteredBeanDictionary must not be null!");
        Asserts.notNull(field, "Supplied field must not be null!");
        Asserts.notNull(parent, "Supplied parent object must not be null!");

        final Class<?> paramType = field.getType();

        final Optional<Object> bean = registeredBeanDictionary.get(paramType);
        if (bean.isEmpty()) throw new IllegalArgumentException("Field required a non-registered bean!");

        final Object beanObject = bean.get();
        Asserts.isInstanceOf(paramType, beanObject, "RegisteredBeanDictionary returned an invalid object! (Does not match required type)");

        try {
            injectField(field, parent, beanObject);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Could not inject field.", e);
        }
    }

    private static void injectField(Field field, Object parent, Object toInject) throws IllegalAccessException{
        field.setAccessible(true);
        field.set(parent, toInject);
    }
}
