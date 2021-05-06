package net.iceyleagons.icicle.common.wrapped.utils;

import lombok.NonNull;
import net.iceyleagons.icicle.common.reflect.Reflections;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

public class WrappedField<T> {

    private final Field field;
    private final Class<?> type;

    @NonNull
    protected WrappedField(@NonNull Field field, @Nullable Class<T> type) {
        this.field = field;
        this.type = type != null ? type : Object.class;
    }

    public T set(Object root, T value) {
        Reflections.set(field, root, value);
        return value;
    }

    public T get(Object root) {
        return (T) Reflections.get(field, type, root);
    }

}
