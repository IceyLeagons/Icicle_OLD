package net.iceyleagons.icicle.common.wrapped.utils;

import lombok.Getter;
import lombok.NonNull;
import net.iceyleagons.icicle.common.reflect.Reflections;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;

// nézz be WrappedBiomeRegistry-be egy példáért.

public class WrappedClass {

    @Getter
    @NonNull
    private final Class<?> clazz;

    private static final HashMap<String, WrappedClass> classMap = new HashMap<>();
    private final HashMap<String, WrappedMethod<?>> methodMap = new HashMap<>();
    private final HashMap<String, WrappedField<?>> fieldMap = new HashMap<>();

    private WrappedClass(Class<?> clazz) {
        this.clazz = clazz;
    }

    public static WrappedClass getNMSClass(@NonNull String name) {
        return classMap.computeIfAbsent(name.toLowerCase(), ignored -> new WrappedClass(Reflections.getNormalNMSClass(name)));
    }

    public static WrappedClass getCBClass(@NonNull String name) {
        return classMap.computeIfAbsent(name.toLowerCase(), ignored -> new WrappedClass(Reflections.getNormalCBClass(name)));
    }

    public static WrappedClass getClass(@NonNull String name) {
        return classMap.computeIfAbsent(name.toLowerCase(), ignored -> new WrappedClass(Reflections.getNormalClass(name)));
    }

    public boolean isInstance(@NonNull Object what) {
        return clazz.isInstance(what);
    }

    @NonNull
    public Object cast(@NonNull Object toCast) {
        return clazz.cast(toCast);
    }

    @Nullable
    public Object newInstance(@Nullable Object... args) {
        try {
            return (args != null ? clazz.getDeclaredConstructor(Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new)) : clazz.getDeclaredConstructor()).newInstance(args);
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    @NonNull
    public <R> WrappedClass lookupMethod(@Nullable Class<R> expectedReturn, @NonNull String name, @Nullable String saveName, @Nullable Class<?>... paramTypes) {
        methodMap.putIfAbsent(saveName != null ? saveName : name, new WrappedMethod<>(Reflections.getMethod(clazz, name, true, paramTypes), expectedReturn));
        return this;
    }

    @NonNull
    public <T> WrappedClass lookupField(@Nullable Class<T> type, @NonNull String name) {
        fieldMap.putIfAbsent(name, new WrappedField<>(Reflections.getField(clazz, name, true), type));
        return this;
    }

    @NonNull
    public WrappedClass lookupMethod(@NonNull String name, @Nullable String saveName, @Nullable Class<?>... paramTypes) {
        lookupMethod(Object.class, name, saveName, paramTypes);
        return this;
    }

    @NonNull
    public <R> WrappedClass lookupMethod(@Nullable Class<R> expectedReturn, @NonNull String name, @Nullable String saveName, @Nullable WrappedClass... paramTypes) {
        lookupMethod(expectedReturn, name, saveName, paramTypes != null ? Arrays.stream(paramTypes).map(WrappedClass::getClazz).toArray(Class<?>[]::new) : null);
        return this;
    }

    @NonNull
    public WrappedClass lookupMethod(@Nullable WrappedClass expectedReturn, @NonNull String name, @Nullable String saveName, @Nullable WrappedClass... paramTypes) {
        if (expectedReturn != null)
            lookupMethod(expectedReturn.getClazz(), name, saveName, paramTypes);
        else lookupMethod(Object.class, name, saveName, paramTypes);
        return this;
    }

    @NonNull
    public WrappedClass lookupMethod(@Nullable WrappedClass expectedReturn, @NonNull String name, @Nullable String saveName, @Nullable Class<?>... paramTypes) {
        if (expectedReturn != null)
            lookupMethod(expectedReturn.getClazz(), name, saveName, paramTypes);
        else lookupMethod(Object.class, name, saveName, paramTypes);
        return this;
    }

    @NonNull
    public WrappedClass lookupField(@NonNull String name) {
        lookupField(Object.class, name);
        return this;
    }

    @NonNull
    public WrappedClass lookupField(@Nullable WrappedClass type, @NonNull String name) {
        if (type != null)
            lookupField(type.getClazz(), name);
        else lookupField(Object.class, name);
        return this;
    }

    public WrappedMethod<?> getMethod(@NonNull String name) {
        return methodMap.get(name);
    }

    public WrappedField<?> getField(@NonNull String name) {
        return fieldMap.get(name);
    }

}
