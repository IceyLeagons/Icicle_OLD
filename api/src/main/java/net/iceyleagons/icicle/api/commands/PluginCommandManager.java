package net.iceyleagons.icicle.api.plugin;

import java.util.Map;

public interface PluginCommandManager {

    void registerCommandContainer(Object o);
    Map<String, RegisteredCommand> getCommands();
    Map<Class<?>, CommandParameterHandlerTemplate> getParameterHandlers();

}
