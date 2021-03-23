package net.iceyleagons.icicle.wrapped.system;

import lombok.NonNull;
import net.iceyleagons.icicle.reflect.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class WrappedClass {

    private final Class<?> clazz;

    private final Map<String, Method> methods = new HashMap<>();
    private final Map<String, Field> fields = new HashMap<>();

    public WrappedClass(String className, WrappedClassType wrappedClassType) {
        this.clazz = wrappedClassType.getClassSupplier().get(className);
    }

    public <T> T invoke(@NonNull String name, @NonNull Class<T> wantedType, @NonNull Object parent, Object... parameters) {
        return Reflections.invokeFromObjectArray(methods.get(name), wantedType, parent, parameters);
    }

    public <T> T get(@NonNull String name, @NonNull Class<T> wantedType, Object parent) {
        return Reflections.get(fields.get(name), wantedType, parent);
    }

    public void set(@NonNull String name, @NonNull Object parent, Object value) {
        Reflections.set(fields.get(name), parent, value);
    }

    public void discoverField(String name) {
        Field field = Reflections.getField(clazz, name, true);
        if (field == null) return;

        fields.put(name, field);
    }

    public void discoverMethod(String name, Class<?>... parameterTypes) {
        Method method = Reflections.getMethod(clazz, name, true, parameterTypes);
        if (method == null) return;

        methods.put(name, method);
    }
}
