package net.iceyleagons.icicle.common.wrapped.utils;

import lombok.NonNull;
import net.iceyleagons.icicle.common.reflect.Reflections;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;

public class WrappedMethod<R> {

    private final Method method;
    private final Class<?> expectedReturn;

    @NonNull
    protected WrappedMethod(@NonNull Method method, @Nullable Class<R> expectedReturn) {
        this.method = method;
        this.expectedReturn = expectedReturn != null ? expectedReturn : Object.class;
    }

    public void invokeNoReturn(Object main, Object... parameters) {
        Reflections.invoke(method, expectedReturn, main, parameters);
    }

    public R invoke(Object main, Object... parameters) {
        return (R) Reflections.invoke(method, expectedReturn, main, parameters);
    }

}
