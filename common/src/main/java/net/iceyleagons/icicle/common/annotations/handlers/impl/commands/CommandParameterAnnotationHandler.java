package net.iceyleagons.icicle.common.annotations.handlers.impl.commands;

import net.iceyleagons.icicle.common.annotations.handlers.HandlerClass;
import net.iceyleagons.icicle.common.commands.annotations.CommandParameterHandler;
import net.iceyleagons.icicle.common.annotations.handlers.AbstractAnnotationHandler;
import net.iceyleagons.icicle.common.annotations.handlers.AnnotationHandler;
import net.iceyleagons.icicle.common.commands.system.CommandParameterHandlerTemplate;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AnnotationHandler(CommandParameterHandler.class)
public class CommandParameterAnnotationHandler extends AbstractAnnotationHandler {

    @Override
    public void scanAndHandleClasses(List<HandlerClass> classes) {
        classes.forEach(handler -> {
            try {
                if (handler.isNormalClass()) {
                    if (Arrays.stream(handler.getClazz().getInterfaces()).collect(Collectors.toList()).contains(CommandParameterHandlerTemplate.class)) {
                        Optional<Constructor<?>> constructor = handler.getEmptyConstructor();
                        if (constructor.isPresent()) {
                            CommandParameterHandler commandParameterHandler = handler.getClazz().getAnnotation(CommandParameterHandler.class);

                            CommandParameterHandlerTemplate object = (CommandParameterHandlerTemplate) constructor.get().newInstance();

                            Arrays.stream(commandParameterHandler.value()).forEach(type ->
                                    super.getRegisteredPlugin().getPluginCommandManager().getParameterHandlers().put(type, object));
                        } else {
                            Optional<Constructor<?>> autowiredConstructor = handler.getAutowiredConstructor();
                            if (autowiredConstructor.isPresent()) {
                                CommandParameterHandler commandParameterHandler = handler.getClazz().getAnnotation(CommandParameterHandler.class);

                                CommandParameterHandlerTemplate object = (CommandParameterHandlerTemplate) super.createObjectAndAutowireFromConstructor(autowiredConstructor.get());

                                Arrays.stream(commandParameterHandler.value()).forEach(type ->
                                        super.getRegisteredPlugin().getPluginCommandManager().getParameterHandlers().put(type, object));
                            } else {
                                getLogger().warning(String.format("Class named %s does not have an empty constructor!", handler.getName()));
                            }
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
