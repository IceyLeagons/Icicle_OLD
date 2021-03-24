<<<<<<< HEAD:core/src/main/java/net/iceyleagons/icicle/annotations/handlers/storage/EntityAnnotationHandler.java
package net.iceyleagons.icicle.annotations.handlers.storage;

import lombok.Getter;
import net.iceyleagons.icicle.api.annotations.handlers.AbstractAnnotationHandler;
import net.iceyleagons.icicle.api.annotations.handlers.AnnotationHandler;
import net.iceyleagons.icicle.api.annotations.storage.Entity;
=======
package net.iceyleagons.icicle.annotations.handlers.impl.storage;

import lombok.Getter;
import net.iceyleagons.icicle.annotations.handlers.AbstractAnnotationHandler;
import net.iceyleagons.icicle.annotations.handlers.AnnotationHandler;
import net.iceyleagons.icicle.annotations.storage.Entity;
>>>>>>> parent of e77d82b (:package: multi module):src/main/java/net/iceyleagons/icicle/annotations/handlers/impl/storage/EntityAnnotationHandler.java
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
