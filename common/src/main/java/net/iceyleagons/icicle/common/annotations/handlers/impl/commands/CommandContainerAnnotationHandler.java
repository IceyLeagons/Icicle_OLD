package net.iceyleagons.icicle.common.annotations.handlers.impl.commands;

import lombok.Getter;
import net.iceyleagons.icicle.common.annotations.handlers.HandlerClass;
import net.iceyleagons.icicle.common.commands.annotations.CommandContainer;
import net.iceyleagons.icicle.common.annotations.handlers.AbstractAnnotationHandler;
import net.iceyleagons.icicle.common.annotations.handlers.AnnotationHandler;
import org.reflections.Reflections;

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
    public void scanAndHandleClasses(List<HandlerClass> classes)  {
        classes.forEach(container -> {
            try {
                if (container.isNormalClass()) {
                    Optional<Constructor<?>> constructor = container.getEmptyConstructor();
                    if (constructor.isPresent()) {
                        Object commandContainerObject = constructor.get().newInstance();
                        commandContainers.put(container.getClazz(), commandContainerObject);
                    } else {
                        Optional<Constructor<?>> autowiredConstructor = container.getAutowiredConstructor();
                        if (autowiredConstructor.isPresent()) {
                            commandContainers.put(container.getClazz(), super.createObjectAndAutowireFromConstructor(autowiredConstructor.get()));
                        } else {
                            getLogger().warning(String.format("Class named %s does not have an empty constructor!", container.getName()));
                        }
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
