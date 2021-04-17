package net.iceyleagons.icicle.storage.annotations;

import lombok.Getter;
import net.iceyleagons.icicle.annotations.handlers.AbstractAnnotationHandler;
import net.iceyleagons.icicle.annotations.handlers.AnnotationHandler;
import net.iceyleagons.icicle.storage.annotations.Entity;
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
