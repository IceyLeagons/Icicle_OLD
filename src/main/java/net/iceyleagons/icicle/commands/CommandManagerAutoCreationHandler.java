package net.iceyleagons.icicle.commands;

import lombok.Getter;
import net.iceyleagons.icicle.RegisteredIciclePlugin;
import net.iceyleagons.icicle.annotations.commands.CommandManager;
import net.iceyleagons.icicle.annotations.handlers.AutoCreationHandlerListener;
import net.iceyleagons.icicle.annotations.handlers.annotations.AutoCreationHandler;

import java.util.HashSet;
import java.util.Set;

@Getter
@AutoCreationHandler(CommandManager.class)
public class CommandManagerAutoCreationHandler implements AutoCreationHandlerListener {

    private final Set<RegisteredCommandManager> managers = new HashSet<>();

    @Override
    public void onCreated(Object object, Class<?> type, RegisteredIciclePlugin registeredIciclePlugin) {
        try {
            RegisteredCommandManager registeredCommandManager = new RegisteredCommandManager(registeredIciclePlugin, type.getAnnotation(CommandManager.class), object, type);
            managers.add(registeredCommandManager);
        } catch (Exception e) {
            System.err.println("Cannot create CommandManager named " + type.getName());
            e.printStackTrace();
            //throw new IllegalStateException("Cannot create CommandManager named " + type.getName(), e);
        }
    }
}
