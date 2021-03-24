<<<<<<< HEAD:core/src/main/java/net/iceyleagons/icicle/annotations/handlers/commands/CommandParameterAnnotationHandler.java
package net.iceyleagons.icicle.annotations.handlers.commands;

import net.iceyleagons.icicle.api.annotations.commands.CommandParameterHandler;
import net.iceyleagons.icicle.api.annotations.handlers.AbstractAnnotationHandler;
import net.iceyleagons.icicle.api.annotations.handlers.AnnotationHandler;
import net.iceyleagons.icicle.api.commands.CommandParameterHandlerTemplate;
=======
package net.iceyleagons.icicle.annotations.handlers.impl.commands;

import net.iceyleagons.icicle.annotations.commands.CommandParameterHandler;
import net.iceyleagons.icicle.annotations.config.Configuration;
import net.iceyleagons.icicle.annotations.handlers.AbstractAnnotationHandler;
import net.iceyleagons.icicle.annotations.handlers.AnnotationHandler;
import net.iceyleagons.icicle.commands.system.CommandParameterHandlerTemplate;
import net.iceyleagons.icicle.config.AbstractConfiguration;
>>>>>>> parent of e77d82b (:package: multi module):src/main/java/net/iceyleagons/icicle/annotations/handlers/impl/commands/CommandParameterAnnotationHandler.java
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@AnnotationHandler(CommandParameterHandler.class)
public class CommandParameterAnnotationHandler extends AbstractAnnotationHandler {

    @Override
    public void scanAndHandleClasses(Reflections reflections) {
        Set<Class<?>> configurations = reflections.getTypesAnnotatedWith(CommandParameterHandler.class);
        configurations.forEach(handler -> {
            try {
                if (!handler.isAnnotation() && !handler.isInterface()) {
                    if (Arrays.stream(handler.getInterfaces()).collect(Collectors.toList()).contains(CommandParameterHandlerTemplate.class)) {
                        Constructor<?> constructor = net.iceyleagons.icicle.reflect.Reflections.getConstructor(handler, true);
                        if (constructor != null) {
                            CommandParameterHandler commandParameterHandler = handler.getAnnotation(CommandParameterHandler.class);

                            CommandParameterHandlerTemplate object = (CommandParameterHandlerTemplate) constructor.newInstance();

                            Arrays.stream(commandParameterHandler.value()).forEach(type ->
                                    super.getRegisteredPlugin().getPluginCommandManager().getParameterHandlers().put(type, object));
                        } else {
                            getLogger().warning(String.format("Class named %s does not have an empty constructor!", handler.getName()));
                        }
                    } else {
                        getLogger().warning(String.format("Class named %s is annotated with @CommandParameterHandler but it does not implement CommandParameterHandlerTemplate!", handler.getName()));
                    }
                } else {
                    getLogger().warning(String.format("Class named %s is not supported for annotation: CommandParameterHandler!", handler.getName()));
                }
            } catch (Exception e) {
                getLogger().warning(String.format("An exception happened while tried to load in CommandParameterHandler named %s.", handler.getName()));
                e.printStackTrace();
            }
        });
    }
}
