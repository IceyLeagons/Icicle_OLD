package net.iceyleagons.icicle.common.annotations.handlers;

import lombok.Getter;
import net.iceyleagons.icicle.common.annotations.Autowired;
import net.iceyleagons.icicle.common.reflect.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HandlerClass {

    @Getter
    private final Class<?> clazz;

    public HandlerClass(Class<?> clazz) {
        this.clazz = clazz;
    }

    public List<Method> getMethodsWithAnnotation(Class<? extends Annotation> annotation) {
        return Arrays.stream(clazz.getMethods())
                .filter(method -> method.isAnnotationPresent(annotation))
                .peek(method -> method.setAccessible(true))
                .collect(Collectors.toList());
    }

    public List<Field> getFieldsWithAnnotation(Class<? extends Annotation> annotation) {
        return Arrays.stream(clazz.getFields())
                .filter(field -> field.isAnnotationPresent(annotation))
                .peek(method -> method.setAccessible(true))
                .collect(Collectors.toList());
    }

    public Optional<Constructor<?>> getAutowiredConstructor() {
        return Arrays.stream(clazz.getConstructors()).filter(constructor -> constructor.isAnnotationPresent(Autowired.class)).findFirst();
    }

    public String getName() {
        return clazz.getName();
    }

    public Optional<Constructor<?>> getEmptyConstructor() {
        return Optional.ofNullable(Reflections.getConstructor(clazz, true));
    }

    public boolean isSuperClassAssignable(Class<?> clazz) {
        return this.clazz.getSuperclass().isAssignableFrom(clazz);
    }

    public boolean isNormalClass() {
        return !clazz.isAnnotation() && !clazz.isInterface();
    }
}
