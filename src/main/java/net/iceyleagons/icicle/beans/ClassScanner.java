package net.iceyleagons.icicle.beans;

import lombok.RequiredArgsConstructor;
import net.iceyleagons.icicle.annotations.AutoCreation;
import net.iceyleagons.icicle.annotations.handlers.annotations.AutoCreationHandler;
import net.iceyleagons.icicle.annotations.handlers.AutoCreationHandlerListener;
import net.iceyleagons.icicle.annotations.handlers.annotations.CustomAnnotationHandler;
import net.iceyleagons.icicle.annotations.handlers.CustomAnnotationHandlerListener;
import net.iceyleagons.icicle.utils.Asserts;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Contains all the {@link Reflections} related scanning.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class ClassScanner {

    private final Reflections reflections;
    private final RegisteredBeanDictionary registeredBeanDictionary;
    private Map<Class<?>, AutoCreationHandlerListener> autoCreationHandlers = null;
    private Map<Class<?>, CustomAnnotationHandlerListener> customAnnotationHandlers = null;

    /**
     * Utility method (not related to {@link Reflections}) to get all Methods that annotates a specified annotation in the class.
     *
     * @param clazz the class to scan
     * @param annotation the required annotation
     * @return the {@link List} of methods
     */
    public List<Method> getMethodsAnnotatedWithInsideClazz(Class<?> clazz, Class<? extends Annotation> annotation) {
        return Arrays.stream(clazz.getDeclaredMethods()).filter(m -> m.isAnnotationPresent(annotation)).collect(Collectors.toList());
    }

    /**
     * @return all the {@link AutoCreationHandlerListener} classes which are annotated with {@link AutoCreationHandler}
     */
    public Map<Class<?>, AutoCreationHandlerListener> getAutoCreationHandlers() {
        if (autoCreationHandlers == null) {
            autoCreationHandlers = new HashMap<>();
            reflections.getTypesAnnotatedWith(AutoCreationHandler.class).stream()
                    .filter(c -> !c.isInterface() && !c.isAnnotation()).forEach(c -> {

                Asserts.isAssignable(AutoCreationHandlerListener.class, c, "Class (" + c.getName() + ") must implement AutoCreationHandlerListener!");
                try {
                    Object obj = c.getDeclaredConstructor().newInstance();
                    AutoCreationHandlerListener listener = (AutoCreationHandlerListener) obj;

                    //System.out.println("Registering auto creation handler " + c.getName());
                    registeredBeanDictionary.registerBean(obj);
                    autoCreationHandlers.put(c.getAnnotation(AutoCreationHandler.class).value(), listener);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new IllegalStateException("Could not create new instance of AutoCreationHandlerListener", e);
                }
            });
        }

        return autoCreationHandlers;
    }

    /**
     * @return all the {@link CustomAnnotationHandlerListener} classes which are annotated with {@link CustomAnnotationHandler}
     */
    public Map<Class<?>, CustomAnnotationHandlerListener> getCustomAnnotationHandlers() {
        if (customAnnotationHandlers == null) {
            this.customAnnotationHandlers = new HashMap<>();

            reflections.getTypesAnnotatedWith(CustomAnnotationHandler.class).stream()
                    .filter(c -> !c.isInterface() && !c.isAnnotation()).forEach(c -> {

                Asserts.isAssignable(CustomAnnotationHandlerListener.class, c, "Class (" + c.getName() + ") must implement CustomAnnotationHandlerListener!");
                try {
                    Object obj = c.getDeclaredConstructor().newInstance();
                    CustomAnnotationHandlerListener listener = (CustomAnnotationHandlerListener) obj;

                    registeredBeanDictionary.registerBean(obj);
                    customAnnotationHandlers.put(c.getAnnotation(CustomAnnotationHandler.class).value(), listener);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new IllegalStateException("Could not create new instance of AutoCreationHandlerListener", e);
                }
            });
        }

        return customAnnotationHandlers;
    }

    /**
     * @return all classes that have an annotation that annotates {@link AutoCreation}.
     */
    public List<Class<?>> getAutoCreationClasses() {
        final List<Class<? extends Annotation>> autoCreationAnnotations = reflections.getTypesAnnotatedWith(AutoCreation.class).stream()
                .filter(Class::isAnnotation).map(c -> (Class<? extends Annotation>) c).collect(Collectors.toList());

        final List<Class<?>> classes = new ArrayList<>();
        for (final Class<? extends Annotation> autoCreationAnnotation : autoCreationAnnotations) {
            classes.addAll(reflections.getTypesAnnotatedWith(autoCreationAnnotation).stream()
                    .filter(c -> !c.isInterface() && !c.isAnnotation())
                    .collect(Collectors.toList()));
        }

        return classes;
    }

}
