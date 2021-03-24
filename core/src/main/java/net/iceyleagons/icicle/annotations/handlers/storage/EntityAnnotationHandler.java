package net.iceyleagons.icicle.annotations.handlers.storage;

import lombok.Getter;
import net.iceyleagons.icicle.api.annotations.handlers.AbstractAnnotationHandler;
import net.iceyleagons.icicle.api.annotations.handlers.AnnotationHandler;
import net.iceyleagons.icicle.api.annotations.storage.Entity;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AnnotationHandler(Entity.class)
public class EntityAnnotationHandler extends AbstractAnnotationHandler {


    @Getter
    private final List<Class<?>> entityClasses = new ArrayList<>();

    @Override
    public void scanAndHandleClasses(Reflections reflections) {
        Set<Class<?>> entities = reflections.getTypesAnnotatedWith(Entity.class);
        entityClasses.addAll(entities);
    }
}
