/*
 * MIT License
 *
 * Copyright (c) 2020 IceyLeagons (Tamás Tóth and Márton Kissik) and Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.iceyleagons.icicle.reflect;

import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Anything that can help you with plugin specific reflections.
 * (it can do normal reflections as well)
 *
 * @author Gábe
 * @version 1.0.0
 * @since 1.0.0-SNAPSHOT
 */
public class Reflections {
    private static final String craftBukkitString;
    private static final String netMinecraftServerString;

    static {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        craftBukkitString = "org.bukkit.craftbukkit." + version + ".";
        netMinecraftServerString = "net.minecraft.server." + version + ".";
    }

    /**
     * @param name the name of the class
     * @return used to check whether a class exists or not
     */
    public static boolean classExists(String name) {
        try {
            Class.forName(name);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * Returns a craftbukkit class with that name
     *
     * @param name the class name
     * @return the craftBukkit class
     */
    public static Class<?> getNormalCBClass(String name) {
        return getNormalClass(craftBukkitString + name);
    }

    /**
     * Returns an NMS class with that name
     *
     * @param name the class name
     * @return the craftBukkit class
     */
    public static Class<?> getNormalNMSClass(String name) {
        return getNormalClass(netMinecraftServerString + name);
    }

    /**
     * Returns a class with that name
     *
     * @param name the class name
     * @return the craftBukkit class
     */
    public static Class<?> getNormalClass(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns a specified method from a class, and if required sets it to be accessible
     *
     * @param clazz           the class
     * @param methodName      the name of the method
     * @param forceAccessible whether to force it accessible or not
     * @param parameterTypes  the parameter types for that method
     * @return the method
     */
    public static Method getMethod(final Class<?> clazz, String methodName, boolean forceAccessible, final Class<?>... parameterTypes) {
        try {
            Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
            if (forceAccessible)
                method.setAccessible(true);
            return method;
        } catch (NoSuchMethodException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    /**
     * Invokes the method without you having to deal with exceptions.
     *
     * @param method     the method
     * @param wantedType is the type you want the result to be in
     * @param o          {@link Method#invoke(Object, Object...)}
     * @param args       {@link Method#invoke(Object, Object...)}
     * @return will try to cast the invoke result if successful if not null
     */
    public static <T> T invoke(Method method, Class<T> wantedType, Object o, Object... args) {
        try {
            Object result = method.invoke(o, args);

            return wantedType.isInstance(result) ? wantedType.cast(result) : null;
        } catch (IllegalAccessException | InvocationTargetException | ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Invokes the method without you having to deal with exceptions.
     *
     * @param field      the field
     * @param wantedType is the type you want the result to be in
     * @param o          {@link Method#invoke(Object, Object...)}
     * @return will try to cast the invoke result if successful if not null
     */
    public static <T> T get(Field field, Class<T> wantedType, Object o) {
        try {
            Object result = field.get(o);
            return wantedType.isInstance(result) ? wantedType.cast(result) : null;
        } catch (IllegalAccessException | ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void set(Field field, Object object, Object value) {
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns all methods in a class, and sets them to be accessible if required
     *
     * @param clazz           the class
     * @param forceAccessible whether to force it accessible or not
     * @return the array of methods
     */
    public static Method[] getMethods(final Class<?> clazz, boolean forceAccessible) {
        Method[] methods = clazz.getDeclaredMethods();
        if (forceAccessible)
            for (Method m : methods)
                m.setAccessible(true);

        return methods;
    }

    /**
     * Returns all fields in a class, and sets them to be accessible
     *
     * @param clazz the class
     * @return the array of fields
     */
    public static Field[] getFields(final Class<?> clazz) {
        final Field[] fields = clazz.getDeclaredFields();
        for (final Field f : fields)
            f.setAccessible(true);

        return fields;
    }

    /**
     * Returns a field with a specified name ands sets it to be accessible if required
     *
     * @param clazz           the class
     * @param name            the name of the field
     * @param forceAccessible whether to force it accessible or not
     * @return the field
     */
    public static Field getField(final Class<?> clazz, String name, boolean forceAccessible) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(name);
            field.setAccessible(forceAccessible);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return field;
    }

    /**
     * Returns a field from the class with a specified data type on the specified index.
     * Since this may be a bit hard to understand here's an example:
     * Ex.:
     * int a = 0;
     * int b = 2;
     * int c = 3;
     * <p>
     * c = (int) getField(class,Integer.class,2).get(obj);
     *
     * @param clazz    the class
     * @param dataType the datatype
     * @param index    the index
     * @return the field
     */
    public static Field getField(final Class<?> clazz, final Class<?> dataType, final int index) {
        int i = 0;
        Field finalField = null;
        List<Field> fieldList = Arrays.stream(getFields(clazz)).parallel()
                .filter(f -> f.getType().equals(dataType))
                .collect(Collectors.toList());
        for (Field field : fieldList)
            if (i++ == index) {
                finalField = field;
                break;
            }
        return finalField;
    }
}
