package net.iceyleagons.icicle.api.annotations.handlers.impl.commands;

import lombok.Getter;
import net.iceyleagons.icicle.api.annotations.commands.CommandContainer;
import net.iceyleagons.icicle.api.annotations.AbstractAnnotationHandler;
import net.iceyleagons.icicle.api.annotations.handlers.AnnotationHandler;
import net.iceyleagons.icicle.reflect.Reflections;

import java.lang.reflect.Constructor;
import java.util.*;

@Getter
@AnnotationHandler(CommandContainer.class)
public class CommandContainerAnnotationHandler extends AbstractAnnotationHandler {

    private final Map<Class<?>, Object> commandContainers = new HashMap<>();

    @Override
    public List<Object> getObjects() {
        return new ArrayList<>(commandContainers.values());
    }


    @Override
    public void scanAndHandleClasses(Reflections reflections) {
        Set<Class<?>> containers = reflections.getTypesAnnotatedWith(CommandContainer.class);
        containers.forEach(container -> {
            try {
                if (!container.isAnnotation() && !container.isInterface()) {
                    Constructor<?> constructor = Reflections.getConstructor(container, true);
                    if (constructor != null) {
                        Object commandContainerObject = constructor.newInstance();
                        commandContainers.put(container, commandContainerObject);
                    } else {
                        getLogger().warning(String.format("Class named %s does not have an empty constructor!", container.getName()));
                    }
                } else {
                    getLogger().warning(String.format("Class named %s is not supported for annotation: CommandContainer!", container.getName()));
                }
            } catch (Exception e) {
                getLogger().warning(String.format("An exception happened while tried to load in CommandContainer named %s.", container.getName()));
                e.printStackTrace();
            }
        });

        commandContainers.values().forEach(container ->
                super.getRegisteredPlugin().getPluginCommandManager().registerCommandContainer(container));
    }
}
